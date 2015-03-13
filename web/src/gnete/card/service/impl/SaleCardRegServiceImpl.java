package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.RebateRule;
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
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.service.SaleCardRegService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
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

@Service("saleCardRegService")
public class SaleCardRegServiceImpl implements SaleCardRegService {
	
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;
	@Autowired
	private SaleCardBatRegDAO saleCardBatRegDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	
	private String message;
	
	static Logger logger = Logger.getLogger(SaleCardRegServiceImpl.class);
	
	public void addSaleCardReg(SaleCardReg saleCardReg, CardExtraInfo cardExtraInfo, 
			UserInfo userInfo, String serialNo) throws BizException {
		Assert.notNull(saleCardReg, "添加的售卡登记对象不能为空");
		Assert.notNull(cardExtraInfo, "添加的持卡人对象不能为空");
		Assert.notEmpty(saleCardReg.getCardId(), "卡号不能为空");
		Assert.notNull(userInfo, "登录用户信息不能为空");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, userInfo), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		// 2. 检查卡的唯一性
		CardInfo cardInfo = this.checkCardId(saleCardReg, userInfo);
		
		// == 此规则已经去掉 3. 判断售出的卡的发卡机构是否配置了审核
		// 3. 如果是发卡机构登录时，判断发卡机构是否需要审核
		boolean isNeedCheck = this.isCheckForSell(userInfo);
		
		// 4. 为售卡登记对象赋值，如果需要录入持卡人信息，则录入持卡人信息，同时在数据库中插入记录
		saleCardReg = this.setSaleCardValue(saleCardReg, cardInfo, cardExtraInfo, isNeedCheck, userInfo);
		
		// 更新 卡信息 与 卡库存 状态；
		if (saleCardReg.isPreSellReg()) {// 预售卡记录
			this.cardStockInfoDAO.updateStatus(saleCardReg.getCardId(), CardStockState.PRE_SOLD.getValue());
			this.cardInfoDAO.updateCardStatus(saleCardReg.getCardId(), CardState.PRESELLED.getValue());
		} else { // 实时售卡记录
			this.cardStockInfoDAO.updateStatus(saleCardReg.getCardId(), CardStockState.SOLD_ING.getValue());
		}
		
		if (isNeedCheck) { // 如果需要审核，则启动审核流程
			try {
				this.workflowService.startFlow(saleCardReg, WorkflowConstants.SALE_CARD_BATCH_ADAPTER, 
								Long.toString(saleCardReg.getSaleBatchId()), userInfo);
			} catch (Exception e) {
				throw new BizException("启动（预）售卡审核流程失败！", e.getMessage());
			}
		} else {
			if (!saleCardReg.isPreSellReg()) {
				this.sendMsgAndDealCardRisk(saleCardReg, cardInfo, userInfo);
			}
		}
		
		this.setMessage("添加售卡登记号[" + saleCardReg.getSaleBatchId() + "]，卡号[" + saleCardReg.getCardId() + "]成功！");
	}
	
	/**
	 * 检查卡的唯一性
	 * @param saleCardReg
	 * @param userInfo
	 * @return
	 * @throws BizException
	 */
	private CardInfo checkCardId(SaleCardReg saleCardReg, UserInfo userInfo) throws BizException {
		String roleType = userInfo.getRole().getRoleType();
		// 检查卡唯一性
		CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardReg.getCardId());
		//   查卡信息表
		Assert.notNull(cardInfo, "卡号[" + saleCardReg.getCardId() + "]卡号不存在");
		Assert.equals(CardState.FORSALE.getValue(), cardInfo.getCardStatus(), "卡号[" + saleCardReg.getCardId() + "]不是待售状态");
		
		// 售卡的卡类型必须和卡信息表的卡类型一致
		Assert.equals(saleCardReg.getCardClass(), cardInfo.getCardClass(), 
				"卡号[" + saleCardReg.getCardId() + "]不是[" + CardType.valueOf(saleCardReg.getCardClass()).getName() + "]");
//		if (StringUtils.equals(saleCardReg.getCardClass(), cardInfo.getCardClass())) {
//			throw new BizException("卡号[" + saleCardReg.getCardId() + "]不是[" + CardType.valueOf(saleCardReg.getCardClass()).getName() + "]");
//		}
		
		// 发卡机构部门的话只能售
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			Assert.equals(userInfo.getDeptId(), cardInfo.getAppOrgId(), "只能售自己领的卡");
		}
		// 发卡机构可售自己发的卡和自己领的卡
		else if (RoleType.CARD.getValue().equals(roleType)) {
			// 如果卡的发卡机构不是自己，则为下级机构售卡，只能售自己从上级领的卡
			if (!StringUtils.equals(cardInfo.getCardIssuer(), userInfo.getBranchNo())) {
				Assert.equals(userInfo.getBranchNo(), cardInfo.getAppOrgId(), "只能售自己领的卡");
			}
		} else {
			Assert.equals(userInfo.getBranchNo(), cardInfo.getAppOrgId(), "只能售自己领的卡");
		}
		
		// 如果是返利指定卡状态，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(saleCardReg.getRebateType())) {
			String rebateCard = saleCardReg.getRebateCard();
			Assert.notEmpty(rebateCard, "返利卡号不能为空");
			
			CardInfo rebateCardInfo = (CardInfo)this.cardInfoDAO.findByPk(rebateCard);
			Assert.notNull(rebateCardInfo, "返利卡卡号[" + rebateCard + "]不存在");
			
			// 如果返利卡不在本批卖出的卡中，则需验证状态是否已售出
			if (!StringUtils.equals(cardInfo.getCardId(), rebateCard)) {
				Assert.isTrue(CardState.ACTIVE.getValue().equals(rebateCardInfo.getCardStatus()), "返利卡[" + rebateCard + "]未激活");
			}
			// 任意读取一张卡
			Assert.isTrue(cardInfo.getCardIssuer().equals(rebateCardInfo.getCardIssuer()), "所售卡与返利指定卡不属于同一发卡机构");
		}
		
		//   查售卡登记簿
		Map<String, Object> params = new HashMap<String, Object>();
		params = new HashMap<String, Object>();
		params.put("cardId", saleCardReg.getCardId());
		params.put("saleCancel", SaleCancelFlag.NORMAL.getValue());
		
		List<SaleCardReg> saleCardRegList = this.saleCardRegDAO.findSaleCardReg(params);
		for(SaleCardReg reg : saleCardRegList){
			Assert.notEquals(reg.getStatus(), RegisterState.NORMAL.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“成功”。");
			Assert.notEquals(reg.getStatus(), RegisterState.WAITEDEAL.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“待处理”。");
			Assert.notEquals(reg.getStatus(), RegisterState.INACTIVE.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“未激活”。");
			Assert.notEquals(reg.getStatus(), RegisterState.WAITED.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“待审核”。");
		}
		//   查售卡明细登记簿
		List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
		for(SaleCardBatReg temp : saleCardBatRegList){
			Assert.notEquals(temp.getStatus(), RegisterState.NORMAL.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“成功”。");
			Assert.notEquals(temp.getStatus(), RegisterState.WAITEDEAL.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“待处理”。");
			Assert.notEquals(temp.getStatus(), RegisterState.INACTIVE.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“未激活”。");
			Assert.notEquals(temp.getStatus(), RegisterState.WAITED.getValue(), "添加售卡登记失败！卡号[" + saleCardReg.getCardId() + "]已售出，状态为“待审核”。");
		}
		
		return cardInfo;
	}
	
	/**
	 * 判断发卡机构发的卡售卡是否需要审核
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForSell(UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		
		boolean isNeedCheck = false;

		// 发卡机构或发卡机构部门的话需要检查是否配置了审核权限
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置售卡审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getSellCheck());
		}
		
		return isNeedCheck;
	}
	
	/**
	 *  为售卡登记对象赋值，如果需要录入持卡人信息，则录入持卡人信息，同时在数据库中插入记录
	 * @param saleCardReg
	 * @param cardInfo
	 * @param cardExtraInfo
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private SaleCardReg setSaleCardValue(SaleCardReg saleCardReg, CardInfo cardInfo,
			CardExtraInfo cardExtraInfo, boolean isCheck, UserInfo user) throws BizException {
		Assert.isTrue(AmountUtil.ge(saleCardReg.getRealAmt(), BigDecimal.ZERO), "实收金额不合法，可能为负数");
		Assert.isTrue(AmountUtil.ge(saleCardReg.getRebateAmt(), BigDecimal.ZERO), "返利金额不合法，可能为负数");
		// 数据预处理
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(user.getUserId());
		saleCardReg.setEntryUserid(user.getUserId());
		
		// 如果需要审核，则状态为待审核
		if (isCheck) {
			saleCardReg.setStatus(RegisterState.WAITED.getValue());
		} else {
			// 如果是预售的话，状态为未激活，否则为待处理
			if (PresellType.PRESELL.getValue().equals(saleCardReg.getPresell())) {
				saleCardReg.setStatus(RegisterState.INACTIVE.getValue());
			} else {
				saleCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			}
		}
		
		// 取得系统工作日，作为售卡时间
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		saleCardReg.setSaleDate(workDate);
		saleCardReg.setSaleCancel(SaleCancelFlag.NORMAL.getValue()); // 是正常售卡
		saleCardReg.setCancelFlag(Symbol.NO); // 不是售卡撤销
		
		// 持卡人信息
		if(StringUtils.isNotBlank(cardExtraInfo.getCustName())){
			Assert.notTrue(this.cardExtraInfoDAO.isExist(saleCardReg.getCardId()), "卡号[" + saleCardReg.getCardId() + "]的信息已经存在");
			
			cardExtraInfo.setUpdateTime(saleCardReg.getUpdateTime());
			cardExtraInfo.setUpdateBy(user.getUserId());
			cardExtraInfo.setCardId(saleCardReg.getCardId());
			cardExtraInfo.setPassword(Constants.DEFUALT_SEARCH_PW);
			
			// 表中新增字段
			cardExtraInfo.setCardCustomerId(saleCardReg.getCardCustomerId());
			cardExtraInfo.setSaleOrgId(user.getBranchNo());
			cardExtraInfo.setCardBranch(saleCardReg.getCardBranch());
			
			this.cardExtraInfoDAO.insert(cardExtraInfo);
		}
		saleCardReg.setCustName(cardExtraInfo.getCustName());
		
		// 售卡登记簿
		this.saleCardRegDAO.insert(saleCardReg);
		
		return saleCardReg;
	}
	
	/**
	 * 实时售卡发报文，预售卡在激活时才发报文（预售时要改卡库存状态为预售）
	 *  同时扣减操作员的配额和发卡机构风险准备金
	 * @throws BizException
	 */
	private void sendMsgAndDealCardRisk(SaleCardReg saleCardReg, CardInfo cardInfo, UserInfo userInfo) throws BizException {
		// 实时售卡则发送售卡报文，同时扣减操作员配额和发卡机构的风险准备金(卡的状态为售卡中)

		MsgSender.sendMsg(MsgType.SALE_CARD, saleCardReg.getSaleBatchId(), userInfo.getUserId());
		
		// 计算风险保证金的扣减金额
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());
		
		// 次卡需要读取清算金额
		if (DepositType.TIMES.getValue().equals(saleCardReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
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
		// 发卡机构自己不处理配额的情况
		if (!StringUtils.equals(cardInfo.getCardIssuer(), saleCardReg.getBranchCode())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
			branchSellReg.setAmt(riskAmt); //扣的是清算金额
			branchSellReg.setCardBranch(StringUtils.trim(cardInfo.getCardIssuer()));		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
			branchSellReg.setSellBranch(saleCardReg.getBranchCode());	// 售卡机构
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
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(StringUtils.trim(cardInfo.getCardIssuer()));	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		this.cardRiskService.activateCardRisk(cardRiskReg);
			
	}
	//========================== 单张卡售卡处理 end ====================================================================
	
	public boolean activate(SaleCardReg saleCardReg, UserInfo user) throws BizException {
		String createUserId = user.getUserId();
		
		Assert.notNull(saleCardReg, "被激活的售卡登记对象不能为空");
		Assert.isTrue(saleCardReg.getPresell().equals(PresellType.PRESELL.getValue()), "被激活的卡对象必须是预售卡");
		Assert.isTrue(saleCardReg.getStatus().equals(RegisterState.INACTIVE.getValue()), "只有'未激活'状态的卡才能被激活");
		
		// 更改预售卡“未激活”状态为“待处理”状态
		saleCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(createUserId);
		this.saleCardRegDAO.update(saleCardReg);
		
		// 预售卡发报文
		long waitsinfoId = sendPreSell(saleCardReg.getSaleBatchId(), createUserId);
		Assert.isTrue(waitsinfoId > 0, "发送报文失败！");
		
		// 更新卡库存状态
		CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(saleCardReg.getCardId());
		Assert.notNull(cardStockInfo, "库存中没有卡号[" + saleCardReg.getCardId() + "]的记录");
		
		cardStockInfo.setCardStatus(CardStockState.SOLD_ING.getValue());
		this.cardStockInfoDAO.update(cardStockInfo);
		
		this.setMessage("激活预售卡登记号[" + saleCardReg.getSaleBatchId() + "]，卡号[" + saleCardReg.getCardId() + "]成功！");
		
		CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardReg.getCardId());
		
		// 计算风险保证金的扣减金额
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());
		// 次卡需要读取清算金额
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
		if (!StringUtils.equals(cardInfo.getCardIssuer(), saleCardReg.getBranchCode())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
//			branchSellReg.setAmt(saleCardReg.getRealAmt());
			branchSellReg.setAmt(riskAmt);
			branchSellReg.setCardBranch(cardInfo.getCardIssuer().trim());		// 发卡机构
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
		this.cardRiskService.deductUserAmt(createUserId, saleCardReg.getBranchCode(), riskAmt);
//		this.cardRiskService.deductUserAmt(createUserId, saleCardReg.getBranchCode(), saleCardReg.getRealAmt());
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
		return true;
	}
	
	/**
	// 发报文
	private long send(long saleBatchId, String createUserId)throws BizException {
		return MsgSender.sendMsg(MsgType.SALE_CARD, saleBatchId, createUserId);
	}
	 */
	
	// 发报文
	private long sendPreSell(long saleBatchId, String createUserId)throws BizException {
		return MsgSender.sendMsg(MsgType.PRE_SELL_ACTIVATE, saleBatchId, createUserId);
	}
	
	public boolean modifySaleCardReg(SaleCardReg saleCardReg, String modifyUserId) throws BizException {
		Assert.notNull(saleCardReg, "更新的售卡登记对象不能为空");		
		
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(modifyUserId);
		// saleCardReg.setStatus(RegisterState.NORMAL.getValue());
		
		int count = this.saleCardRegDAO.update(saleCardReg);		
		
		return count > 0;
	}

	public boolean deleteSaleCardReg(long saleCardRegId) throws BizException{
		Assert.notNull(saleCardRegId, "删除的售卡登记ID不能为空");
		
		int count = this.saleCardRegDAO.delete(saleCardRegId);
		
		return count > 0;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Paginater findSaleCardCancelPage(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		return this.saleCardRegDAO.findSaleCardCancel(params, pageNumber, pageSize);
	}
	
	public SaleCardReg findSaleCardCancelDetail(Long saleBatchId)
			throws BizException {
		Assert.notNull(saleBatchId, "售卡撤销批次号不能为空");
		SaleCardReg saleCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(saleBatchId);
		
		Assert.notNull(saleCardReg.getCardCustomerId(), "购卡客户ID不能为空");
		CardCustomer cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(saleCardReg.getCardCustomerId());
		
		saleCardReg.setCardCustomerName(cardCustomer.getCardCustomerName());
		return saleCardReg;
	}
	
	public RebateRule findRebateRule(Long rebateId) throws BizException {
		Assert.notNull(rebateId, "售卡返利ID不能为空");
		return (RebateRule) this.rebateRuleDAO.findByPk(rebateId);
	}
	
	public void addSaleCardCancel(SaleCardReg saleCardReg, UserInfo user, 
			String serialNo) throws BizException {
		Assert.notNull(saleCardReg, "要撤销的售卡对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notNull(saleCardReg.getSaleBatchId(), "要撤销的售卡批次号不能为空");
		Assert.notNull(saleCardReg.getRealAmt(), "实收金额不能为空");
		
		SaleCardReg oldSaleReg = (SaleCardReg) this.saleCardRegDAO.findByPk(saleCardReg.getSaleBatchId());
		Assert.notNull(oldSaleReg, "要撤销的售卡批次为[" + saleCardReg.getSaleBatchId() + "]的售卡记录已经不存在");
		List<RegisterState> list = RegisterState.getForSaleCancelDeal();
		
		RegisterState state = RegisterState.valueOf(oldSaleReg.getStatus());
		Assert.notNull(state, "要撤销的售卡登记薄中的状态不正确");
		
		Assert.isTrue(list.contains(state), "只有“成功” 或 “未激活”状态的才可以做售卡撤销。");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		// 1.为售卡撤销对象设值
		setForSaleCardCancel(saleCardReg, oldSaleReg, user);
		
		//2. 根据原售卡记录的状态做不同的操作
		if (RegisterState.NORMAL.getValue().equals(oldSaleReg.getStatus())) {
			dealSaleCancelForNormal(oldSaleReg, saleCardReg, user);
			
		} else if (RegisterState.INACTIVE.getValue().equals(oldSaleReg.getStatus())) {
			dealSaleCancelForInactive(oldSaleReg, saleCardReg, user);
			
		} else {
			throw new BizException("只有“成功” 或 “未激活”状态的才可以做售卡撤销。");
		}
		this.saleCardRegDAO.update(oldSaleReg);
	}
	
	/**
	 * 预售卡撤销
	 */
	public void addSaleCardPreCancel(SaleCardBatReg saleCardReg, UserInfo user) throws BizException {
		Assert.notNull(saleCardReg, "要撤销的售卡对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notNull(saleCardReg.getSaleBatchId(), "要撤销的售卡批次号不能为空");
		
		SaleCardReg oldSaleReg = (SaleCardReg) this.saleCardRegDAO.findByPk(saleCardReg.getSaleBatchId());
		Assert.notNull(oldSaleReg, "要撤销的售卡批次为[" + saleCardReg.getSaleBatchId() + "]的售卡记录已经不存在");
		
		// 根据原售卡记录的状态做不同的操作
		if (RegisterState.INACTIVE.getValue().equals(oldSaleReg.getStatus())) {
			dealSalePreCancelForInactive(oldSaleReg, user);
		} else {
			throw new BizException("只有“未激活”状态的才可以做预售卡撤销。");
		}
		// 为售卡撤销对象设值
		oldSaleReg.setEntryUserid(user.getUserId());
		oldSaleReg.setUpdateTime(new Date());
		oldSaleReg.setUpdateUser(user.getUserId());
		oldSaleReg.setStatus(RegisterState.CANCELED.getValue());
		oldSaleReg.setCancelFlag(Symbol.YES);
//		oldSaleReg.setSaleCancel(SaleCancelFlag.CANCEL.getValue());
		this.saleCardRegDAO.update(oldSaleReg);
	}
	
	/**
	 * 预售卡撤销
	 * @Date 2013-3-29上午11:35:11
	 * @return void
	 */
	private void dealSalePreCancelForInactive(SaleCardReg oldSaleReg, UserInfo user) throws BizException {
		// 批量要改明细表的状态
		if (StringUtils.isEmpty(oldSaleReg.getCardId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", oldSaleReg.getSaleBatchId());
			List<SaleCardBatReg> batList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			
			Assert.notEmpty(batList, "找不到原预售卡登记[" + oldSaleReg.getSaleBatchId() + "]的明细信息。");
			for (SaleCardBatReg reg : batList) {
				CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(reg.getCardId());
				Assert.notNull(cardInfo, "卡号[" + reg.getCardId() + "]不存在");

				// 更改卡表状态为已领卡待售
				if(cardInfo.isPreselledStatus()){
					this.cardInfoDAO.updateCardStatus(cardInfo.getCardId(), CardState.FORSALE.getValue());
				}else{
					throw new BizException("卡号[" + reg.getCardId() + "]状态不是 预售出 状态");
				}
				
				reg.setStatus(RegisterState.CANCELED.getValue());
				reg.setUpdateTime(new Date());
				reg.setUpdateUser(user.getUserId());
			}
			this.saleCardBatRegDAO.updateBatch(batList);
		} else {
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(oldSaleReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + oldSaleReg.getCardId() + "]不存在");

			// 更改卡表状态为已领卡待售
			if(cardInfo.isPreselledStatus()){
				this.cardInfoDAO.updateCardStatus(cardInfo.getCardId(), CardState.FORSALE.getValue());
			}else{
				throw new BizException("卡号[" + cardInfo.getCardId() + "]状态不是 预售出 状态");
			}
		}
		updateExtraInfoForCardSalePre(oldSaleReg);
	}
	
	/**
	 * 为售卡撤销对象设值
	 * @param saleCardReg
	 * @param oldSaleReg
	 * @param user
	 * @throws BizException
	 */
	private void setForSaleCardCancel(SaleCardReg saleCardReg, SaleCardReg oldSaleReg, 
			UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		saleCardReg.setCardId(oldSaleReg.getCardId());
		saleCardReg.setCardClass(oldSaleReg.getCardClass());
		saleCardReg.setCardCustomerId(oldSaleReg.getCardCustomerId());
		saleCardReg.setRebateId(oldSaleReg.getRebateId());
		saleCardReg.setExpenses(oldSaleReg.getExpenses());
		saleCardReg.setAmt(oldSaleReg.getAmt());
		saleCardReg.setRebateAmt(oldSaleReg.getRebateAmt());
		saleCardReg.setRebateCard(oldSaleReg.getRebateCard());
		saleCardReg.setRebateType(oldSaleReg.getRebateType());
		saleCardReg.setDepositType(oldSaleReg.getDepositType());
		saleCardReg.setPresell(oldSaleReg.getPresell());
		saleCardReg.setSaleDate(oldSaleReg.getSaleDate());
		saleCardReg.setStrCardId(oldSaleReg.getStrCardId());
		saleCardReg.setEndCardId(oldSaleReg.getEndCardId());
		saleCardReg.setPayAccNo(oldSaleReg.getPayAccNo());
		saleCardReg.setPayAccName(oldSaleReg.getPayAccName());
		saleCardReg.setCardBranch(oldSaleReg.getCardBranch());
		saleCardReg.setFeeRate(oldSaleReg.getFeeRate());
		saleCardReg.setFeeAmt(oldSaleReg.getFeeAmt());
		
		// 发卡机构部门时
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			saleCardReg.setBranchCode(user.getDeptId());
		} else {
			saleCardReg.setBranchCode(user.getBranchNo()); //撤销机构
		}
		saleCardReg.setCancelFlag(Symbol.YES);
		saleCardReg.setSaleCancel(SaleCancelFlag.CANCEL.getValue());
		saleCardReg.setOldSaleBatch(oldSaleReg.getSaleBatchId());

		saleCardReg.setEntryUserid(user.getUserId());
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(user.getUserId());
	}
	
	/**
	 * 2.3 原售卡记录的状态为“未激活”时。原售卡记录状态改为“已撤销”，售卡撤销记录的状态为“成功”.卡信息表中的卡号改为“以领卡待售”
	 * @param oldSaleReg 原售卡记录
	 * @param saleCardReg 售卡撤销记录
	 * @param user
	 * @throws BizException
	 */
	private void dealSaleCancelForInactive(SaleCardReg oldSaleReg, SaleCardReg saleCardReg, 
			UserInfo user) throws BizException {
		oldSaleReg.setStatus(RegisterState.CANCELED.getValue());
		oldSaleReg.setCancelFlag(SaleCancelFlag.CANCEL.getValue());
		saleCardReg.setStatus(RegisterState.NORMAL.getValue());
		
		this.saleCardRegDAO.insert(saleCardReg);
		
		// 批量要改明细表的状态
		if (StringUtils.isEmpty(oldSaleReg.getCardId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", oldSaleReg.getSaleBatchId());
			List<SaleCardBatReg> batList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			
			Assert.notEmpty(batList, "找不到原售卡登记[" + oldSaleReg.getSaleBatchId() + "]的明细信息。");
			List<SaleCardBatReg> list2 = new ArrayList<SaleCardBatReg>();
			
			for (SaleCardBatReg reg : batList) {
				CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(reg.getCardId());
				Assert.notNull(cardInfo, "卡号[" + reg.getCardId() + "]不存在");
				// 更改卡表状态为已领卡待售
				if(cardInfo.isPreselledStatus()){
					this.cardInfoDAO.updateCardStatus(cardInfo.getCardId(), CardState.FORSALE.getValue());
				}
				
				reg.setStatus(RegisterState.CANCELED.getValue());
				reg.setUpdateTime(new Date());
				reg.setUpdateUser(user.getUserId());
				
				list2.add(reg);
			}
			this.saleCardBatRegDAO.updateBatch(list2);
		} else {
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(oldSaleReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + oldSaleReg.getCardId() + "]不存在");
			// 更改卡表状态为已领卡待售
			if(cardInfo.isPreselledStatus()){
				this.cardInfoDAO.updateCardStatus(cardInfo.getCardId(), CardState.FORSALE.getValue());
			}
		}
		
		updateExtraInfoForSuccess(saleCardReg);
	}
	
	/**
	 * 2.2 原售卡记录的状态为“待审核”时.原售卡记录状态改为“已撤销”，售卡撤销记录的状态为“成功”
	 * @param oldSaleReg 原售卡记录
	 * @param saleCardReg
	 * @param user
	 * @throws BizException
	 */
	private void dealSaleCancelForWaited(SaleCardReg oldSaleReg, SaleCardReg saleCardReg, 
			UserInfo user) throws BizException {
		oldSaleReg.setStatus(RegisterState.CANCELED.getValue());
		saleCardReg.setStatus(RegisterState.NORMAL.getValue());
		
		try {
			this.workflowService.deleteFlow(WorkflowConstants.SALE_CARD_BATCH_ADAPTER, 
					Long.toString(oldSaleReg.getSaleBatchId()));
		} catch (Exception e) {
			throw new BizException("删除批量售卡流程时发生异常，原因："+ e.getMessage());
		}
		this.saleCardRegDAO.insert(saleCardReg);
		
		// 批量要改明细表的状态
		if (StringUtils.isEmpty(oldSaleReg.getCardId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", oldSaleReg.getSaleBatchId());
			List<SaleCardBatReg> batList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			
			Assert.notEmpty(batList, "找不到原售卡登记[" + oldSaleReg.getSaleBatchId() + "]的明细信息。");
			List<SaleCardBatReg> list2 = new ArrayList<SaleCardBatReg>();
			
			for (SaleCardBatReg reg : batList) {
				reg.setStatus(RegisterState.CANCELED.getValue());
				reg.setUpdateTime(new Date());
				reg.setUpdateUser(user.getUserId());
				
				list2.add(reg);
			}
			this.saleCardBatRegDAO.updateBatch(list2);
		}
		
		updateExtraInfoForSuccess(saleCardReg);
	}
	
	/**
	 * //2.1 原售卡记录的状态为“成功”时，需启动售卡撤销流程
	 * @param oldSaleReg 原售卡记录
	 * @param saleCardReg
	 * @param user
	 * @throws BizException
	 */
	private void dealSaleCancelForNormal(SaleCardReg oldSaleReg, SaleCardReg saleCardReg, 
			UserInfo user) throws BizException {
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		Assert.equals(oldSaleReg.getSaleDate(), workDate, "原售卡记录已经清算，不能撤销。");
		
		saleCardReg.setStatus(RegisterState.WAITED.getValue());
		oldSaleReg.setStatus(RegisterState.WAITEDEAL.getValue());
		
		this.saleCardRegDAO.insert(saleCardReg);
		
		try {
			this.workflowService.startFlow(saleCardReg, WorkflowConstants.SALE_CARD_CANCEL_ADAPTER, 
					Long.toString(saleCardReg.getSaleBatchId()), user);
		} catch (Exception e) {
			throw new BizException("启动售卡撤销流程出错，原因：" + e.getMessage());
		}
		
		// 批量要改明细表的状态
		if (StringUtils.isEmpty(oldSaleReg.getCardId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", oldSaleReg.getSaleBatchId());
			List<SaleCardBatReg> batList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			
			Assert.notEmpty(batList, "找不到原售卡登记[" + oldSaleReg.getSaleBatchId() + "]的明细信息。");
			List<SaleCardBatReg> list2 = new ArrayList<SaleCardBatReg>();
			
			for (SaleCardBatReg reg : batList) {
				reg.setStatus(RegisterState.WAITEDEAL.getValue());
				reg.setUpdateTime(new Date());
				reg.setUpdateUser(user.getUserId());
				
				list2.add(reg);
			}
			this.saleCardBatRegDAO.updateBatch(list2);
		}
	}
	
	public void updateExtraInfoForSuccess(SaleCardReg saleCardReg) throws BizException {
		Assert.notNull(saleCardReg, "售卡撤销对象不能为空");
		Assert.notNull(saleCardReg.getOldSaleBatch(), "原售卡批次不能为空");
		
		// 1. 取得原售卡登记对象
		SaleCardReg oldCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(saleCardReg.getOldSaleBatch());
		Assert.notNull(oldCardReg, "原售卡记录已经不存在");
		
		// 2.根据卡号，判断是单张卡售卡，还是批量售卡
		if (StringUtils.isNotBlank(oldCardReg.getCardId())) {
			logger.debug("删除卡号[" + oldCardReg.getCardId() + "]在卡附加信息表中的数据");
			this.cardExtraInfoDAO.delete(oldCardReg.getCardId());
			
			// 取得卡库存信息表中该卡的数据，更新状态为"已领卡"
			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(oldCardReg.getCardId());
			Assert.notNull(cardStockInfo, "在卡库存信息表中找不到卡号[" + oldCardReg.getCardId() + "]的记录");
			
			cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
			this.cardStockInfoDAO.update(cardStockInfo);
		}
		// 3. 如果是批量售卡，卡号则从售卡明细登记表中取得
		else {
			// 取得售卡明细登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", oldCardReg.getSaleBatchId());
			
			List<SaleCardBatReg> list = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			Assert.notNull(list, "售卡批次为[" + oldCardReg.getSaleBatchId() + "]的售卡明细表中无对应的书局数据");
			
			for (SaleCardBatReg reg : list) {
				logger.debug("删除卡号[" + reg.getCardId() + "]在卡附加信息表中的数据");
				this.cardExtraInfoDAO.delete(reg.getCardId());

				// 取得卡库存信息表中该卡的数据，更新状态为"已领卡"
				CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(reg.getCardId());
				Assert.notNull(cardStockInfo, "在卡库存信息表中找不到卡号[" + reg.getCardId() + "]的记录");
				
				cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
				this.cardStockInfoDAO.update(cardStockInfo);
			}
		}
	}
	
	public void updateExtraInfoForCardSalePre(SaleCardReg oldCardReg) throws BizException {
		// 2.根据卡号，判断是单张卡售卡，还是批量售卡
		if (StringUtils.isNotBlank(oldCardReg.getCardId())) {
			logger.debug("删除卡号[" + oldCardReg.getCardId() + "]在卡附加信息表中的数据");
			this.cardExtraInfoDAO.delete(oldCardReg.getCardId());
			
			// 取得卡库存信息表中该卡的数据，更新状态为"已领卡"
			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(oldCardReg.getCardId());
			Assert.notNull(cardStockInfo, "在卡库存信息表中找不到卡号[" + oldCardReg.getCardId() + "]的记录");
			
			cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
			this.cardStockInfoDAO.update(cardStockInfo);
		}
		// 3. 如果是批量售卡，卡号则从售卡明细登记表中取得
		else {
			// 取得售卡明细登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", oldCardReg.getSaleBatchId());
			
			List<SaleCardBatReg> list = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			Assert.notNull(list, "售卡批次为[" + oldCardReg.getSaleBatchId() + "]的售卡明细表中无对应的数据");
			
			for (SaleCardBatReg reg : list) {
				logger.debug("删除卡号[" + reg.getCardId() + "]在卡附加信息表中的数据");
				this.cardExtraInfoDAO.delete(reg.getCardId());

				// 取得卡库存信息表中该卡的数据，更新状态为"已领卡"
				CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(reg.getCardId());
				Assert.notNull(cardStockInfo, "在卡库存信息表中找不到卡号[" + reg.getCardId() + "]的记录");
				
				cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
				this.cardStockInfoDAO.update(cardStockInfo);
			}
		}
	}
}
