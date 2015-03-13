package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.RebateCardRegDAO;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.RebateCardReg;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.SaleCancelFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PresellType;
import gnete.card.entity.type.RebateFromType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.service.SaleCardBatRegService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("saleCardBatRegService")
public class SaleCardBatRegServiceImpl implements SaleCardBatRegService {
	
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;
	@Autowired
	private SaleCardBatRegDAO saleCardBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO; 
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private RebateCardRegDAO rebateCardRegDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private UserService userService;
	
	private String message;
	
	private static final Logger logger = Logger.getLogger(SaleCardBatRegServiceImpl.class);
	
	public long addSaleCardBatReg(SaleCardReg saleCardReg, SaleCardBatReg saleCardBatReg, 
			List<RebateCardReg> rebateCardRegList, UserInfo user, String serialNo) throws BizException {
		Assert.notNull(saleCardReg, "添加的批量售卡对象不能为空");
		Assert.notNull(saleCardBatReg, "添加的批量售卡登记对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		logger.debug("1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致");
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		//手工处理时,要重新计算返利
		if(saleCardReg.isManual()){
			//明细的返利金额 = 充值金额*（总的返利金额/充值总金额）
			BigDecimal rebateAmt = AmountUtil.multiply(saleCardBatReg.getAmt(), AmountUtil.divide(saleCardReg.getRebateAmt(), saleCardReg.getAmt()));
			saleCardBatReg.setRebateAmt(rebateAmt);
		}

		// 2. 检查卡的唯一性； 
		logger.debug("2. 检查卡的唯一性");
		Object[] objects = this.checkCardIdRev(saleCardReg, saleCardBatReg, rebateCardRegList, user);
		CardInfo cardInfo = (CardInfo) objects[0];
		List<SaleCardBatReg> batRegList = (List<SaleCardBatReg>) objects[1];
		
		//已经不按这种条件了 3. 判断售出的卡的发卡机构是否配置了审核。同时如果是手工指定返利且非发卡机构登记售卡则需要发卡机构审核
		// 3. 判断当前登录的发卡机构是否配置了审核权限。同时如果是手工指定返利且非发卡机构登记售卡则需要发卡机构审核
		logger.debug("3.判断是否需要审核");
		boolean isNeedCheck = this.isCheckForSell(saleCardReg, user);
		
		// 4. 为售卡登记对象赋值，如果需要录入持卡人信息，则录入持卡人信息，同时在数据库中插入记录
		logger.debug("4. 为售卡登记对象赋值，如果需要录入持卡人信息，则录入持卡人信息，同时在数据库中插入记录");
		saleCardReg = this.setSaleCardValue(saleCardReg, cardInfo, batRegList, rebateCardRegList, isNeedCheck, user);
		
		// 更新 卡信息 与 卡库存 状态；
		updateCardStatus(batRegList, saleCardReg.isPreSellReg());
		
		// 5. 如果需要审核，则启动审核流程。不需要审核则发送报文同时扣减风险保证金
		logger.debug("5. 如果需要审核，则启动审核流程。不需要审核则发送报文同时扣减风险保证金");
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(saleCardReg, WorkflowConstants.SALE_CARD_BATCH_ADAPTER, Long
						.toString(saleCardReg.getSaleBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			if (!saleCardReg.isPreSellReg()) {
				// 实时售卡发报文，并扣减风险金；预售卡在激活时才发报文
				this.sendMsgAndDealCardRisk(saleCardReg, batRegList, cardInfo, user);
			}
		}
		return saleCardReg.getSaleBatchId();
	}

	/** 
	 * 更新 卡信息 与 卡库存（防止其他机构将此卡领出） 状态；
	 */
	private void updateCardStatus(List<SaleCardBatReg> batRegList, boolean isPreSellReg)
			throws BizException {
		List<String> cardIdList = new ArrayList<String>();
		for (SaleCardBatReg batReg : batRegList) {
			cardIdList.add(batReg.getCardId());
		}
		if (isPreSellReg) { // 预售卡记录
			this.cardInfoDAO.updateCardStatus(cardIdList, CardState.PRESELLED.getValue());
			this.cardStockInfoDAO.updateStatus(cardIdList, CardStockState.PRE_SOLD.getValue());
		} else {
			this.cardStockInfoDAO.updateStatus(cardIdList, CardStockState.SOLD_ING.getValue());
		}
	}
	
	/**
	 * 检查卡的唯一性
	 * @param saleCardReg
	 * @param userInfo
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unused")
	private Object[] checkCardId(SaleCardReg saleCardReg, List<SaleCardBatReg> saleCardBatRegList,
			List<RebateCardReg> rebateCardRegList, UserInfo userInfo) throws BizException {
		String roleType = userInfo.getRole().getRoleType();
		List<SaleCardBatReg> list = new ArrayList<SaleCardBatReg>();
		
		// 取第一张卡
		CardInfo saleCardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardBatRegList.get(0).getCardId());
		Assert.notNull(saleCardInfo, "卡号[" + saleCardBatRegList.get(0).getCardId() + "]不存在");
		
		// 如果是返利指定卡状态，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(saleCardReg.getRebateType())) {
			Assert.notEmpty(saleCardReg.getRebateCard(), "返利指定卡式返利卡号不能为空");
			
			CardInfo rebateCardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardReg.getRebateCard());
			Assert.notNull(rebateCardInfo, "指定的返利卡卡号[" + saleCardReg.getRebateCard() + "]不存在");
			Assert.equals(saleCardInfo.getCardIssuer(), rebateCardInfo.getCardIssuer(), "所售卡与返利指定卡不属于同一发卡机构");
			
			// 如果返利卡不在本批卖出的卡中，则需验证状态是否已售出
//			for (SaleCardBatReg batReg : saleCardBatRegList) {
//				if (!StringUtils.equals(saleCardReg.getRebateCard(), batReg.getCardId())) {
//					Assert.equals(CardState.ACTIVE.getValue(), rebateCardInfo.getCardStatus(), "返利卡[" + saleCardReg.getRebateCard() + "]未激活或不在本次售卡批次中");
//				}
//			}
			
			if (!checkRebateCardId(saleCardReg.getRebateCard(), saleCardBatRegList)) {
				Assert.equals(CardState.ACTIVE.getValue(), rebateCardInfo.getCardStatus(), "返利卡[" + saleCardReg.getRebateCard() + "]未激活或不在本次售卡批次中");
			}
			
		}
		// 如果是返利多张卡
		else if (RebateType.CARDS.getValue().equals(saleCardReg.getRebateType())) {
			Assert.notEmpty(rebateCardRegList, "多张卡返利登记对象不能为空");
			BigDecimal rebateSum = new BigDecimal("0");
			
			// 检查每张返利卡
			for (RebateCardReg rebateCardReg : rebateCardRegList) {
				Assert.notEmpty(rebateCardReg.getCardId(), 
						"第[" + rebateCardRegList.indexOf(rebateCardReg) + "]张返利卡号[" + rebateCardReg.getCardId() + "]不能为空");
				
				CardInfo rebateInfo = (CardInfo) this.cardInfoDAO.findByPk(rebateCardReg.getCardId());
				Assert.notNull(rebateInfo, "第[" + rebateCardRegList.indexOf(rebateCardReg) + "]张返利卡号[" + rebateCardReg.getCardId() + "]不存在");
				Assert.equals(saleCardInfo.getCardIssuer(), rebateInfo.getCardIssuer(), "所售卡与返利卡[" + rebateCardReg.getCardId() + "]不属于同一发卡机构");
				
				// 如果返利卡不在本批卖出的卡中，则需验证状态是否已售出
//				for (SaleCardBatReg batReg : saleCardBatRegList) {
//					if (!StringUtils.equals(rebateCardReg.getCardId(), batReg.getCardId())) {
//						Assert.equals(CardState.ACTIVE.getValue(), rebateInfo.getCardStatus(), "返利卡[" + rebateCardReg.getCardId() + "]未激活或不在本次售卡批次中");
//					}
//				}
				if (!checkRebateCardId(rebateCardReg.getCardId(), saleCardBatRegList)) {
					Assert.equals(CardState.ACTIVE.getValue(), rebateInfo.getCardStatus(), "返利卡[" + rebateCardReg.getCardId() + "]未激活或不在本次售卡批次中");
				}
				
				rebateSum = AmountUtil.add(rebateSum, rebateCardReg.getRebateAmt());
			}
			
			BigDecimal rebateAmt = AmountUtil.format(saleCardReg.getRebateAmt());
			
			Assert.isTrue(AmountUtil.et(rebateSum, rebateAmt), 
					"返利卡的金额总和[" + rebateSum + "]与售卡登记簿中的返利金额[" + rebateAmt + "]不相等");
		}
		
		// 检查批量售卡明细
		for (int i = 0; i < saleCardBatRegList.size(); i++) {
			SaleCardBatReg saleCardBatReg = saleCardBatRegList.get(i);
			
			String cardId = saleCardBatReg.getCardId();
			Assert.notEmpty(cardId, "第[" + (i + 1) + "]张卡，卡号不能为空！");
			
			// 查卡信息表
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);

			// 如果是旧卡
			if (saleCardBatReg.getCardId().length() == 18) {
				// 按旧校验位生成的第一种方法生成的
				if (cardInfo == null) {
					String cardPrex = cardId.substring(0, cardId.length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				}
				
				saleCardBatReg.setCardId(cardId);
			}
			Assert.notNull(cardInfo, "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]不存在！");
			Assert.equals(CardState.FORSALE.getValue(), cardInfo.getCardStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]不是待售卡！");
			
			// 售卡的卡类型必须和卡信息表的卡类型一致
			Assert.equals(saleCardReg.getCardClass(), cardInfo.getCardClass(), 
					"卡号[" + saleCardReg.getCardId() + "]不是[" + CardType.valueOf(saleCardReg.getCardClass()).getName() + "]");
//			if (StringUtils.equals(saleCardReg.getCardClass(), cardInfo.getCardClass())) {
//				throw new BizException("第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]不是[" + CardType.valueOf(saleCardReg.getCardClass()).getName() + "]");
//			}

//			// 只能售自己领的卡 
//			if (RoleType.CARD_DEPT.getValue().equals(roleType)){
//				Assert.equals(cardInfo.getAppOrgId(), userInfo.getDeptId(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在部门领的卡，不能售出！");
//			} else {
//				Assert.equals(cardInfo.getAppOrgId(), userInfo.getBranchNo(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在机构领的卡，不能售出！");
//			}
			
			// 发卡机构部门的话只能售
			if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				Assert.equals(cardInfo.getAppOrgId(), userInfo.getDeptId(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在部门领的卡，不能售出！");
			}
			// 发卡机构可售自己发的卡和自己领的卡
			else if (RoleType.CARD.getValue().equals(roleType)) {
				// 如果卡的发卡机构不是自己，则为下级机构售卡，只能售自己从上级领的卡
				if (!StringUtils.equals(cardInfo.getCardIssuer(), userInfo.getBranchNo())) {
					Assert.equals(cardInfo.getAppOrgId(), userInfo.getBranchNo(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在的发卡机构领的卡，不能售出！");
				}
			} else {
				Assert.equals(cardInfo.getAppOrgId(), userInfo.getBranchNo(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在机构领的卡，不能售出！");
			}

			// 预售还要更改卡表状态为预售出
			if (PresellType.PRESELL.getValue().equals(saleCardReg.getPresell())) {
				cardInfo.setCardStatus(CardState.PRESELLED.getValue());
				this.cardInfoDAO.update(cardInfo);
			}
			
			// 查售卡登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardId", saleCardBatReg.getCardId());
			params.put("saleCancel", SaleCancelFlag.NORMAL.getValue());
			List<SaleCardReg> regList = this.saleCardRegDAO.findSaleCardReg(params);
			
			for (SaleCardReg reg : regList) {
				Assert.notEquals(RegisterState.NORMAL.getValue(), reg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出！");
				Assert.notEquals(RegisterState.WAITEDEAL.getValue(), reg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出，状态为“待处理”！");
				Assert.notEquals(RegisterState.INACTIVE.getValue(), reg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出，状态为“未激活”！");
				Assert.notEquals(RegisterState.WAITED.getValue(), reg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出，状态为“待审核”！");
			}

			// 查售卡明细登记簿
			List<SaleCardBatReg> batRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			for (SaleCardBatReg batReg : batRegList) {
				Assert.notEquals(RegisterState.NORMAL.getValue(), batReg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出！");
				Assert.notEquals(RegisterState.WAITEDEAL.getValue(), batReg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出，状态为“待处理”！");
				Assert.notEquals(RegisterState.INACTIVE.getValue(), batReg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出，状态为“未激活”！");
				Assert.notEquals(RegisterState.WAITED.getValue(), batReg.getStatus(), "第[" + (i + 1) + "]张卡，卡号[" + saleCardBatReg.getCardId() + "]已经售出，状态为“待审核”！");
			}
			
			list.add(saleCardBatReg);
		}
		
		return new Object[] {saleCardInfo, list};
	}
	
	/**
	 * 检查卡的唯一性
	 * @param saleCardReg
	 * @param userInfo
	 * @return
	 * @throws BizException
	 */
	private Object[] checkCardIdRev(SaleCardReg saleCardReg, SaleCardBatReg saleCardBatReg,
			List<RebateCardReg> rebateCardRegList, UserInfo userInfo) throws BizException {
		String roleType = userInfo.getRole().getRoleType();
		List<SaleCardBatReg> list = new ArrayList<SaleCardBatReg>();
		
		// 取第一张卡
		CardInfo firstCard = (CardInfo)this.cardInfoDAO.findByPk(saleCardReg.getStrCardId());
		Assert.notNull(firstCard, "卡号[" + saleCardReg.getStrCardId() + "]不存在");
		
		BigDecimal sumAmt = BigDecimal.ZERO;
		
		String cardIdInLoop = "";
		SaleCardBatReg saleBatReg = null;

		List<CardInfo> cardInfoList = this.cardInfoDAO.getCardList(saleCardReg.getStrCardId(), saleCardReg.getEndCardId());
		for (int i = 0; i < cardInfoList.size(); i++) {
			CardInfo cardInfo = cardInfoList.get(i);
			cardIdInLoop = cardInfo.getCardId();
			
			Assert.equals(CardState.FORSALE.getValue(), cardInfo.getCardStatus(), "第[" + (i + 1) + "]张卡，卡号[" + cardIdInLoop + "]不是待售卡！");
			
			// 售卡的卡类型必须和卡信息表的卡类型一致
			Assert.equals(saleCardReg.getCardClass(), cardInfo.getCardClass(), 
					"卡号[" + saleCardReg.getCardId() + "]不是[" + CardType.valueOf(saleCardReg.getCardClass()).getName() + "]");
			
			// 发卡机构部门的话只能售
			if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				Assert.equals(cardInfo.getAppOrgId(), userInfo.getDeptId(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在部门领的卡，不能售出！");
			}
			// 发卡机构可售自己发的卡和自己领的卡
			else if (RoleType.CARD.getValue().equals(roleType)) {
				// 如果卡的发卡机构不是自己，则为下级机构售卡，只能售自己从上级领的卡
				if (!StringUtils.equals(cardInfo.getCardIssuer(), userInfo.getBranchNo())) {
					Assert.equals(cardInfo.getAppOrgId(), userInfo.getBranchNo(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在的发卡机构领的卡，不能售出！");
				}
			} else {
				Assert.equals(cardInfo.getAppOrgId(), userInfo.getBranchNo(), "第[" + (i + 1) + "]张卡，卡号[" + cardInfo.getCardId() + "]不是您所在机构领的卡，不能售出！");
			}
			
			sumAmt = AmountUtil.add(sumAmt, saleCardBatReg.getAmt());
			
			saleBatReg = new SaleCardBatReg();
			saleBatReg.setCardId(cardIdInLoop);
			saleBatReg.setCardBranch(cardInfo.getCardIssuer());
			saleBatReg.setExpenses(saleCardBatReg.getExpenses());
			saleBatReg.setRealAmt(saleCardBatReg.getRealAmt());
			saleBatReg.setAmt(saleCardBatReg.getAmt());
			saleBatReg.setPresell(saleCardReg.getPresell());
			
			if (RebateType.CARD.getValue().equals(saleCardReg.getRebateType())
					|| RebateType.CARDS.getValue().equals(saleCardReg.getRebateType())) {
				saleBatReg.setRebateAmt(BigDecimal.ZERO);
			} else {
				saleBatReg.setRebateAmt(saleCardBatReg.getRebateAmt());
			}
			list.add(saleBatReg);
		}
		sumAmt = AmountUtil.format(sumAmt);
		Assert.isTrue(AmountUtil.et(sumAmt, saleCardReg.getAmt()), "售卡金额总和[" + sumAmt + "]与售卡登记簿中的售卡总金额[" + saleCardReg.getAmt() + "]不相等");
		
		// 如果是返利指定卡状态，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(saleCardReg.getRebateType())) {
			Assert.notEmpty(saleCardReg.getRebateCard(), "返利指定卡式返利卡号不能为空");
			
			CardInfo rebateCardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardReg.getRebateCard());
			Assert.notNull(rebateCardInfo, "指定的返利卡卡号[" + saleCardReg.getRebateCard() + "]不存在");
			Assert.equals(firstCard.getCardIssuer(), rebateCardInfo.getCardIssuer(), "所售卡与返利指定卡不属于同一发卡机构");
			
			if (!checkRebateCardId(saleCardReg.getRebateCard(), list)) {
				Assert.equals(CardState.ACTIVE.getValue(), rebateCardInfo.getCardStatus(), "返利卡[" + saleCardReg.getRebateCard() + "]未激活或不在本次售卡批次中");
			}
		}
		// 如果是返利多张卡
		else if (RebateType.CARDS.getValue().equals(saleCardReg.getRebateType())) {
			Assert.notEmpty(rebateCardRegList, "多张卡返利登记对象不能为空");
			BigDecimal rebateSum = new BigDecimal("0");
			
			// 检查每张返利卡
			for (RebateCardReg rebateCardReg : rebateCardRegList) {
				Assert.notEmpty(rebateCardReg.getCardId(), 
						"第[" + rebateCardRegList.indexOf(rebateCardReg) + "]张返利卡号[" + rebateCardReg.getCardId() + "]不能为空");
				
				CardInfo rebateInfo = (CardInfo) this.cardInfoDAO.findByPk(rebateCardReg.getCardId());
				Assert.notNull(rebateInfo, "第[" + rebateCardRegList.indexOf(rebateCardReg) + "]张返利卡号[" + rebateCardReg.getCardId() + "]不存在");
				Assert.equals(firstCard.getCardIssuer(), rebateInfo.getCardIssuer(), "所售卡与返利卡[" + rebateCardReg.getCardId() + "]不属于同一发卡机构");
				
				if (!checkRebateCardId(rebateCardReg.getCardId(), list)) {
					Assert.equals(CardState.ACTIVE.getValue(), rebateInfo.getCardStatus(), "返利卡[" + rebateCardReg.getCardId() + "]未激活或不在本次售卡批次中");
				}
				
				rebateSum = AmountUtil.add(rebateSum, rebateCardReg.getRebateAmt());
			}
			
			BigDecimal rebateAmt = AmountUtil.format(saleCardReg.getRebateAmt());
			
			Assert.isTrue(AmountUtil.et(rebateSum, rebateAmt), "返利卡的金额总和[" + rebateSum + "]与售卡登记簿中的返利金额[" + rebateAmt + "]不相等");
		}
		
		return new Object[] {firstCard, list};
	}
	
	/**
	 *  检查返利卡号是否在批量售卡明细中
	 *  
	 * @param rebateCardId
	 * @param batList
	 * @return
	 */
	private boolean checkRebateCardId(String rebateCardId, List<SaleCardBatReg> batList) {
		boolean flag = false;
		for (SaleCardBatReg batReg : batList) {
			if (StringUtils.equals(rebateCardId, batReg.getCardId())) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	/**
	 * 判断发卡机构发的卡售卡是否需要审核.同时如果是手工指定返利且非发卡机构登记售卡则需要发卡机构审核
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForSell(SaleCardReg saleCardReg, UserInfo user) throws BizException {
		boolean isCheckForSell = false;
		
		String roleType = user.getRole().getRoleType();

		// 发卡机构的话需要检查是否配置了审核权限
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置售卡审核权限");
			
			isCheckForSell = StringUtils.equals(Symbol.YES, config.getSellCheck());
		} else {
			isCheckForSell = saleCardReg.isManual();
		}
		
		
		return isCheckForSell;
	}
	
	/**
	 * 为售卡登记薄及售卡登记明细对象赋值，并在数据库中插入记录
	 * @param saleCardReg
	 * @param cardInfo
	 * @param batRegList
	 * @param isNeedCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private SaleCardReg setSaleCardValue(SaleCardReg saleCardReg, CardInfo cardInfo, 
			List<SaleCardBatReg> batRegList, List<RebateCardReg> rebateCardRegList, 
			boolean isNeedCheck, UserInfo user) throws BizException {
		// 数据预处理
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(user.getUserId());
		saleCardReg.setEntryUserid(user.getUserId());
		// 只有返利指定卡时，返利卡号才允许有数据
		if (!RebateType.CARD.getValue().equals(saleCardReg.getRebateType())) {
			saleCardReg.setRebateCard("");
		}
		
		// 取得系统工作日，作为售卡时间
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		saleCardReg.setSaleDate(workDate);
		saleCardReg.setSaleCancel(SaleCancelFlag.NORMAL.getValue()); // 是正常售卡
		saleCardReg.setCancelFlag(Symbol.NO); // 不是售卡撤销
		
		String status = "";
		// 如果需要审核，则状态为待审核
		if (isNeedCheck) {
			status = RegisterState.WAITED.getValue();
		} else {
			// 如果是预售的话，状态为未激活，否则为待处理
			if (PresellType.PRESELL.getValue().equals(saleCardReg.getPresell())) {
				status = RegisterState.INACTIVE.getValue();
			} else {
				status = RegisterState.WAITEDEAL.getValue();
			}
		}
		saleCardReg.setStatus(status);
		
		this.saleCardRegDAO.insert(saleCardReg);
		
		// 在批量售卡明细表中插入，记录
		for (SaleCardBatReg batReg : batRegList) {
			batReg.setSaleBatchId(saleCardReg.getSaleBatchId());
			batReg.setUpdateTime(new Date());
			batReg.setUpdateUser(user.getUserId());
			batReg.setStatus(status);
			
			saleCardBatRegDAO.insert(batReg);
		}
		
		// 在售卡返利明细表中插入数据
		for (RebateCardReg rebateCardReg : rebateCardRegList) {
			rebateCardReg.setRebateFrom(RebateFromType.SALE_CARD.getValue());
			rebateCardReg.setBatchId(saleCardReg.getSaleBatchId());
			
			this.rebateCardRegDAO.insert(rebateCardReg);
		}
		
		return saleCardReg;
	}
	
	/**
	 * 发送 实时售卡 报文<br/>
	 * 同时扣减操作员的配额和发卡机构风险准备金
	 * @throws BizException
	 */
	private void sendMsgAndDealCardRisk(SaleCardReg saleCardReg, 
			List<SaleCardBatReg> list, CardInfo cardInfo, UserInfo userInfo) throws BizException {

		// 实时售卡则发送售卡报文，同时扣减操作员配额和发卡机构的风险准备金
		MsgSender.sendMsg(MsgType.SALE_CARD, saleCardReg.getSaleBatchId(), userInfo.getUserId());

		// 计算风险保证金的扣减金额
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());

		// 次卡需要读取清算金额
		if (DepositType.TIMES.getValue().equals(saleCardReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();

			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO
					.findByPk(cardSubClass);
			String freqClass = cardSubClassDef.getFrequencyClass();
			Assert.notEmpty(freqClass, "无该次数卡子类型定义");

			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			Assert.notNull(accuClassDef, "次卡子类[" + freqClass + "]不存在");
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = new BigDecimal(0.0);
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), saleCardReg.getAmt());
			}
		}

		String roleType = userInfo.getRole().getRoleType();
		// 处理风险准备金和售卡配额，预售在激活的时候扣
		// 售卡配额发卡机构自己不处理配额的情况
		if (!StringUtils.equals(StringUtils.trim(cardInfo.getCardIssuer()), saleCardReg.getBranchCode())) {
			BranchSellReg branchSellReg = new BranchSellReg();
			branchSellReg.setId(saleCardReg.getSaleBatchId()); // 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
			branchSellReg.setAmt(riskAmt); // 扣的是清算金额
			branchSellReg.setCardBranch(StringUtils.trim(cardInfo.getCardIssuer())); // 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
			branchSellReg.setSellBranch(saleCardReg.getBranchCode()); // 售卡机构
			if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减操作员额度。扣清算金额
		this.cardRiskService.deductUserAmt(userInfo.getUserId(), saleCardReg.getBranchCode(), riskAmt);

		// 扣减风险保证金
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(saleCardReg.getSaleBatchId()); // 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(StringUtils.trim(cardInfo.getCardIssuer())); // 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	/** 
	 * 激活批量预售卡
	 */
	public boolean activate(SaleCardReg saleCardReg, UserInfo user)throws BizException {
		String createUserId = user.getUserId();
		
		Assert.notNull(saleCardReg, "被激活的售卡登记对象不能为空");
		Assert.isTrue(saleCardReg.getPresell().equals(PresellType.PRESELL.getValue()), "被激活的卡对象必须是预售卡");
		Assert.isTrue(saleCardReg.getStatus().equals(RegisterState.INACTIVE.getValue()), "只有'未激活'状态的卡才能被激活");
		
		// 更改售卡表预售卡“未激活”状态为“待处理”状态
		saleCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(createUserId);
		this.saleCardRegDAO.update(saleCardReg); 
		
		// 更改批量售卡表预售卡“未激活”状态为“待处理”状态
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("saleBatchId", saleCardReg.getSaleBatchId());  
		List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
		SaleCardBatReg saleCardBatReg;
		long waitsinfoId;
		int count = saleCardBatRegList.size();
		CardInfo cardInfo = null;
		for(int i = 0; i < count; i++){
			saleCardBatReg = saleCardBatRegList.get(i);
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardBatReg.getCardId());
			
			saleCardBatReg.setStatus(RegisterState.WAITEDEAL.getValue());
			saleCardBatReg.setUpdateTime(new Date());
			saleCardBatReg.setUpdateUser(createUserId);
			this.saleCardBatRegDAO.update(saleCardBatReg);
		}
		
		// 发 预售卡激活 报文
		//waitsinfoId = send(saleCardReg.getSaleCardBatRegId(), createUserId);
		waitsinfoId = MsgSender.sendMsg(MsgType.PRE_SELL_ACTIVATE, saleCardReg.getSaleBatchId(), createUserId);
		Assert.isTrue(waitsinfoId > 0, "发送报文失败！");
		
		this.setMessage("激活批量预售卡登记号[" + saleCardReg.getSaleBatchId() + "]成功！");
		
		// 次卡需要读取清算金额
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());
		if (DepositType.TIMES.getValue().equals(saleCardReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
			String freqClass = cardSubClassDef.getFrequencyClass();
			Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
			
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = new BigDecimal(0.0);
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), saleCardReg.getAmt());
			}
		}
		
		// 发卡机构自己激活不处理配额的情况
		if (!StringUtils.equals(StringUtils.trim(cardInfo.getCardIssuer()), saleCardReg.getBranchCode())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
			branchSellReg.setAmt(saleCardReg.getRealAmt());
			branchSellReg.setCardBranch(StringUtils.trim(cardInfo.getCardIssuer()));		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(saleCardReg.getBranchCode());	// 售卡机构
			if (saleCardReg.getBranchCode().startsWith("D")) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(createUserId, saleCardReg.getBranchCode(), saleCardReg.getRealAmt());
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
		return true;
	}
	
//	// 发报文
//	private long send(long saleBatchId, String createUserId)throws BizException {
//		return MsgSender.sendMsg(MsgType.SALE_CARD, saleBatchId, createUserId);
//	}
	
	
	public boolean modifySaleCardBatReg(SaleCardBatReg saleCardBatReg, String modifyUserId) throws BizException {
		Assert.notNull(saleCardBatReg, "更新的批量售卡登记对象不能为空");		
		
		saleCardBatReg.setUpdateTime(new Date());
		saleCardBatReg.setUpdateUser(modifyUserId);
		// saleCardBatReg.setStatus(RegisterState.NORMAL.getValue());
		
		int count = this.saleCardBatRegDAO.update(saleCardBatReg);		
		
		return count > 0;
	}

	public boolean deleteSaleCardBatReg(long saleBatchId) throws BizException{
		Assert.notNull(saleBatchId, "删除的批量售卡登记ID不能为空");
		
		int count = this.saleCardBatRegDAO.delete(saleBatchId);
		
		return count > 0;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
