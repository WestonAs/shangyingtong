package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.SpringContext;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.DepositCouponBatRegDAO;
import gnete.card.dao.DepositCouponRegDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DepositCouponBatReg;
import gnete.card.entity.DepositCouponReg;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.entity.type.SingleBatchType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.BaseDataService;
import gnete.card.service.CardRiskService;
import gnete.card.service.CouponBusService;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("couponBusService")
public class CouponBusServiceImpl implements CouponBusService {
	
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private DepositCouponRegDAO depositCouponRegDAO;
	@Autowired
	private DepositCouponBatRegDAO depositCouponBatRegDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	@Autowired
	protected WorkflowService workflowService;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseDataService baseDataService;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private UserCertificateRevService userCertificateRevService;
	
	/** 默认编码格式 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/** 默认分隔符 */
	private static final String DEFAULT_SEQ = ",";
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public void addDepositCouponReg(DepositCouponReg depositCouponReg,
			UserInfo user, String serialNo) throws BizException {
		Assert.notNull(depositCouponReg, "赠券赠送登记对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(depositCouponReg.getCardId(), "要做赠券赠送的卡号不能为空");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			
			// 待验签的数据
			String info = depositCouponReg.getCardId() + depositCouponReg.getRefAmt().toString() + depositCouponReg.getRandomSessionid();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, depositCouponReg.getSignature(), info), "验签失败");
		}
		
		// 2. 检查卡信息
		CardInfo cardInfo = this.checkCardNo(depositCouponReg, user);
		
		// 3. 判断该发卡机构是否需要审核
		boolean isNeedCheck ;
		 if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){//商户都要审核
			 isNeedCheck = true;
		 }else{
			 isNeedCheck = this.isCheckForDeposit(user);
		 }
		
		// 3. 在登记簿中插入数据
		depositCouponReg = this.setDepositCouponRegData(depositCouponReg, cardInfo, isNeedCheck, user);
		
		// 4.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositCouponReg, WorkflowConstants.DEPOSIT_COUPON_ADAPTER, 
								Long.toString(depositCouponReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送赠券赠送报文，同时扣减风险准备金和售卡配额（单张卡赠券赠送没有预赠券赠送）
			this.sendMsgAndDealCardRisk(depositCouponReg, user);
		}
	}
	
	/**
	 * 检查要做赠券赠送的卡
	 * @param depositCouponReg
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private CardInfo checkCardNo(DepositCouponReg depositCouponReg, UserInfo user) throws BizException {
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositCouponReg.getCardId());
		
		Assert.notNull(cardInfo, "卡号[" + depositCouponReg.getCardId() + "]不存在");
		Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + depositCouponReg.getCardId() + "]的账户不存在");
		Assert.equals(CardState.ACTIVE.getValue(), cardInfo.getCardStatus(), 
				"卡号[" + depositCouponReg.getCardId() + "]不是正常状态，不能赠券赠送！");
		
		// 如果没有权限赠券赠送则报错
		if (!baseDataService.hasRightsToDepositCoupon(user, cardInfo)){
			throw new BizException("没有给该卡[" + depositCouponReg.getCardId() + "]赠券赠送的权限");
		}
		
		return cardInfo;
	}
	
	/**
	 * 判断登录的机构赠券赠送是否需要审核。
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForDeposit(UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		boolean isNeedCheck = false;
		
		if (RoleType.CARD.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置赠券赠送审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getDepositCheck());
		}
		return isNeedCheck;
	}
	
	/**
	 * 为赠券赠送登记对象赋值，同时在数据库中插入记录
	 * 
	 * @param depositCouponReg
	 * @param isCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private DepositCouponReg setDepositCouponRegData(DepositCouponReg depositCouponReg, 
			CardInfo cardInfo, boolean isCheck, UserInfo user) throws BizException {

		// 如果需要审核，则状态为待审核
		if (isCheck) {
			depositCouponReg.setStatus(RegisterState.WAITED.getValue());
		} else {
			depositCouponReg.setStatus(RegisterState.WAITEDEAL.getValue());
		}
		
		String roleType = user.getRole().getRoleType();
		
		// 商户做赠券赠送时
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			depositCouponReg.setDepositBranch(user.getMerchantNo());
		} 
		// 部门赠券赠送时
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			depositCouponReg.setDepositBranch(user.getDeptId());
		} else {
			depositCouponReg.setDepositBranch(user.getBranchNo());
		}
		
		// 数据预处理
		depositCouponReg.setIsSigle(SingleBatchType.SINGLE_SHORT.getValue());//非批量
		depositCouponReg.setCardBranch(cardInfo.getCardIssuer());
		depositCouponReg.setUpdateTime(new Date());
		depositCouponReg.setUpdateUser(user.getUserId());
		depositCouponReg.setEntryUserid(user.getUserId());
		depositCouponReg.setFileDeposit(Symbol.NO); // 不是以文件方式赠券赠送
		// 取得系统工作日，作为赠券赠送日期
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositCouponReg.setDepositDate(workDate);

		// 存储赠券赠送登记簿
		this.depositCouponRegDAO.insert(depositCouponReg);
		
		return depositCouponReg;
	}
	
	/**
	 * 发送赠券赠送报文，同时扣减操作员的配额和发卡机构风险准备金。（单张卡赠券赠送没有预赠券赠送）
	 * @throws BizException
	 */
	private void sendMsgAndDealCardRisk(DepositCouponReg depositCouponReg, UserInfo user) throws BizException {
		// 发送赠券赠送报文
		MsgSender.sendMsg(MsgType.DEPOSIT_COUPON, depositCouponReg.getDepositBatchId(), user.getUserId());
		
		String roleType = user.getRole().getRoleType();
		// 商户不用扣减操作
		if (!RoleType.MERCHANT.getValue().equals(roleType)) {
			// 扣减操作员配额和发卡机构的风险准备金
			this.deductUserAmtAndCardRisk(depositCouponReg, user);
		}
	}
	
	/**
	 * 扣减风险保证金和操作员额度
	 * @param depositCouponReg
	 * @param user
	 * @throws BizException
	 */
	private void deductUserAmtAndCardRisk(DepositCouponReg depositCouponReg, UserInfo user) throws BizException {
		BigDecimal totalRisk = depositCouponReg.getRefAmt(); // 赠券赠送扣的是积分折算金额。
		
		// 扣减风险准备金和赠券赠送售卡额度
		if (!RoleType.CARD.getValue().equals(user.getRole().getRoleType())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositCouponReg.getDepositBatchId());	// 赠券赠送登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
			branchSellReg.setAmt(totalRisk);						// 清算金额
			branchSellReg.setCardBranch(depositCouponReg.getCardBranch());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositCouponReg.getDepositBranch());	// 赠券赠送机构
			if (RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(user.getUserId(), depositCouponReg.getDepositBranch(), totalRisk);
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositCouponReg.getDepositBatchId());	// 赠券赠送登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(totalRisk);						// 积分折算金额
		cardRiskReg.setBranchCode(depositCouponReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	public CardInfo checkCardId(String cardId, String cardCount, UserInfo user) throws BizException {
		Assert.notEmpty(cardId, "卡号不能为空");
		
		CardInfo cardInfo = null;
		int count = 1; 
		if (StringUtils.isNotEmpty(cardCount)) {
			Assert.isTrue(NumberUtils.isDigits(cardCount), "卡数量必须为正整数");
			count = Integer.valueOf(cardCount);
		}
		if (cardId.length() == 19) {//新卡
			String[] cardArray = CardUtil.getCard(cardId, count);
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				Assert.notNull(cardInfo, "卡号[" + cardNo + "]不存在");
				
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
			}
		}
		// 18位的是旧卡
		else if (cardId.length() == 18) {
			String[] cardArray = CardUtil.getOldCard(cardId, count);
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				// 按另一种规则试试
				if (cardInfo == null) {
					String cardPrex = StringUtils.substring(cardId, 0, cardId.length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
					
					Assert.notNull(cardInfo, "旧卡结束卡号[" + cardPrex + "*]不存在");
				}
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
			}
		} else {
			Assert.isTrue(count == 1, "外部卡不能使用批量赠券赠送");
			
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在");
			
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardId + "]不是“正常(已激活)”状态的卡");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在");
		}
		
//		cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
//		Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在");
//		
//		Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "只有“正常(已激活)”状态的卡才可以做赠券赠送");
//		
//		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
//		Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在");
		
		String roleType = user.getRole().getRoleType();
		// 商户。判断商户是否属于该卡所属发卡机构
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			CardToMerchKey key = new CardToMerchKey();
			
			key.setBranchCode(cardInfo.getCardIssuer());
			key.setMerchId(user.getMerchantNo());
			
			Assert.isTrue(this.cardToMerchDAO.isExist(key), "商户[" + user.getMerchantNo() + "]不是卡号[" + cardId + "]所属发卡机构的特约商户。");
		}
		// 发卡机构、机构网点
		else if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			Assert.equals(user.getBranchNo(), cardInfo.getCardIssuer(), "卡号[" + cardId + "]不是登录发卡机构发的卡");
		}
		// 售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			boolean flag = isCardSellPrivilege(cardInfo, user, Constants.POINT_EXC_GIFT_PRIVILEGE_CODE);
			Assert.isTrue(flag, "售卡代理[" + cardId +  "]没有权限为卡[" + cardId + "]做赠券赠送");
		}
		
		return cardInfo;
	}
	
	/**
	 * 判断售卡代理是否有权限操作此卡
	 */
	private boolean isCardSellPrivilege(CardInfo cardInfo, UserInfo user, String limitId) throws BizException{
		
//		String saleOrgId = cardInfo.getSaleOrgId();
		String cardIssuer = cardInfo.getCardIssuer();
		String proxyId = user.getBranchNo();

//		Assert.equals(saleOrgId, proxyId, "本售卡代理不是该卡的售卡机构,无法操作该卡。");
		
		// 判断是否属于该发卡机构的售卡代理
		BranchProxyDAO branchProxyDAO = (BranchProxyDAO) SpringContext.getService("branchProxyDAOImpl");
		BranchProxyKey branchProxyKey = new BranchProxyKey(proxyId, cardIssuer, ProxyType.SELL.getValue());
		if (!branchProxyDAO.isExist(branchProxyKey)) {
			return false;
		}
		
		// 判断是否拥有该权限
		SaleProxyPrivilegeDAO saleProxyPrivilegeDAO = (SaleProxyPrivilegeDAO) SpringContext.getService("saleProxyPrivilegeDAOImpl");
		List<HashMap> privlegeList = saleProxyPrivilegeDAO.findSaleProxy(proxyId, cardIssuer, limitId);
		if (CollectionUtils.isNotEmpty(privlegeList)) {
			return true;
		}
		return false;
	}
	
	public void addDepositCouponBatReg(DepositCouponReg depositCouponReg, 
			DepositCouponBatReg depositCouponBatReg, String cardCount, 
			UserInfo user, String serialNo) throws BizException {
		Assert.notNull(depositCouponReg, "赠券赠送登记对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(depositCouponReg.getStrCardId(), "起始卡号不能为空");
		Assert.notEmpty(depositCouponReg.getEndCardId(), "结束卡号不能为空");
		Assert.notEmpty(cardCount, "批量赠券赠送的卡数量不能为空");
		Assert.isTrue(NumberUtils.isDigits(cardCount), "批量赠券赠送的卡连续张数必须为正整数");
		Long count = NumberUtils.toLong(cardCount);
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			// 待验签的数据
			String info = depositCouponReg.getRefAmt().toString() + depositCouponReg.getRandomSessionid();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, depositCouponReg.getSignature(), info), "验签失败");
		}
		// 2. 检查卡信息
		Object[] objects = this.checkCardNoBat(depositCouponReg, depositCouponBatReg, count, user);
		CardInfo cardInfo = (CardInfo) objects[0];
		List<DepositCouponBatReg> batList = (List<DepositCouponBatReg>) objects[1];
		
		// 3. 判断该发卡机构是否需要审核
		boolean isNeedCheck ;
		 if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){//商户都要审核
			 isNeedCheck = true;
		 }else{
			 isNeedCheck = this.isCheckForDeposit(user);
		 }
		
		// 3. 在登记簿中插入数据
		depositCouponReg = this.setDepositCouponBatRegData(depositCouponReg, batList, cardInfo, isNeedCheck, user);
		
		// 4.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositCouponReg, WorkflowConstants.DEPOSIT_COUPON_ADAPTER, 
								Long.toString(depositCouponReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送赠券赠送报文，同时扣减风险准备金和售卡配额（单张卡赠券赠送没有预赠券赠送）
			this.sendMsgAndDealCardRisk(depositCouponReg, user);
		}
		
	}
	
	/**
	 * 检查要做赠券赠送的卡
	 * @param depositCouponReg
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private Object[] checkCardNoBat(DepositCouponReg depositCouponReg, 
			DepositCouponBatReg depositCouponBatReg, Long cardCount, UserInfo user) throws BizException {
		List<DepositCouponBatReg> batList = new ArrayList<DepositCouponBatReg>();
		String cardId = depositCouponReg.getStrCardId();
		Assert.notEmpty(cardId, "起始卡号不能为空");
		Assert.notNull(depositCouponBatReg.getCouponAmt(), "单张卡赠送赠券不能为空");
		Assert.notNull(depositCouponBatReg.getRefAmt(), "单张卡赠送赠券折算金额不能为空");

		CardInfo cardInfo = null;
		DepositCouponBatReg batReg = null;
		if (cardId.length() == 19) {//新卡
			String[] cardArray = CardUtil.getCard(cardId, cardCount.intValue());
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				Assert.notNull(cardInfo, "卡号[" + cardNo + "]不存在");
				
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
				
				batReg = new DepositCouponBatReg();
				batReg.setCardId(cardNo);
				batReg.setCouponAmt(depositCouponBatReg.getCouponAmt());
				batReg.setRefAmt(depositCouponBatReg.getRefAmt());
				
				batList.add(batReg);
			}
		}
		// 18位的是旧卡
		else if (cardId.length() == 18) {
			String[] cardArray = CardUtil.getOldCard(cardId,  cardCount.intValue());
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				// 按另一种规则试试
				if (cardInfo == null) {
					String cardPrex = StringUtils.substring(cardId, 0, cardId.length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
					
					Assert.notNull(cardInfo, "旧卡结束卡号[" + cardPrex + "*]不存在");
				}
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
				
				batReg = new DepositCouponBatReg();
				batReg.setCardId(cardNo);
				batReg.setCouponAmt(depositCouponBatReg.getCouponAmt());
				batReg.setRefAmt(depositCouponBatReg.getRefAmt());
				
				batList.add(batReg);
			}
		}
		
		// 如果没有权限赠券赠送则报错
		if (!baseDataService.hasRightsToDepositCoupon(user, cardInfo)){
			throw new BizException("没有给该卡[" + depositCouponReg.getCardId() + "]赠券赠送的权限");
		}
		
		return new Object[]{cardInfo, batList};
	}
	
	/**
	 * 为赠券赠送登记对象赋值，同时在数据库中插入记录
	 * 
	 * @param depositCouponReg
	 * @param isCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private DepositCouponReg setDepositCouponBatRegData(DepositCouponReg depositCouponReg, 
			List<DepositCouponBatReg> batList, CardInfo cardInfo, boolean isCheck, UserInfo user) throws BizException {

		// 如果需要审核，则状态为待审核
		String status = "";
		if (isCheck) {
			status = RegisterState.WAITED.getValue();
		} else {
			status = RegisterState.WAITEDEAL.getValue();
		}
		
		String roleType = user.getRole().getRoleType();
		
		// 商户做赠券赠送时
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			depositCouponReg.setDepositBranch(user.getMerchantNo());
		} 
		// 部门赠券赠送时
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			depositCouponReg.setDepositBranch(user.getDeptId());
		} else {
			depositCouponReg.setDepositBranch(user.getBranchNo());
		}
		
		// 数据预处理
		depositCouponReg.setIsSigle(SingleBatchType.BATCH_SHORT.getValue());//批量
		depositCouponReg.setStatus(status);
		depositCouponReg.setCardBranch(cardInfo.getCardIssuer());
		depositCouponReg.setUpdateTime(new Date());
		depositCouponReg.setUpdateUser(user.getUserId());
		depositCouponReg.setEntryUserid(user.getUserId());
		depositCouponReg.setFileDeposit(Symbol.NO); // 不是以文件方式赠券赠送
		// 取得系统工作日，作为赠券赠送日期
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositCouponReg.setDepositDate(workDate);

		// 存储赠券赠送登记簿
		this.depositCouponRegDAO.insert(depositCouponReg);
		
		List<DepositCouponBatReg> batRegList = new ArrayList<DepositCouponBatReg>();
		for (DepositCouponBatReg batReg : batList) {
			batReg.setDepositBatchId(depositCouponReg.getDepositBatchId());
			batReg.setStatus(status);
			batReg.setInsertTime(new Date());
			
			batRegList.add(batReg);
		}
		this.depositCouponBatRegDAO.insertBatch(batRegList);
		
		return depositCouponReg;
	}
	
	public void addDepositCouponBatRegFile(DepositCouponReg depositCouponReg,
			File file, String cardCount, UserInfo user, String serialNo, String limitId) throws BizException {
		Assert.notNull(depositCouponReg, "要添加的赠券赠送对象不能为空");
		Assert.notNull(user, "登录用户的信息对象不能为空");
		Assert.notEmpty(cardCount, "批量赠券赠送的卡数量不能为空");
		Assert.isTrue(NumberUtils.isDigits(cardCount), "批量赠券赠送的卡连续张数必须为正整数");
		Long count = NumberUtils.toLong(cardCount);
		Assert.notNull(file, "赠券赠送文件不能为空");
		Assert.notEmpty(depositCouponReg.getCouponClass(), "赠券赠送对象中的赠券类型不能为空");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			// 待验签的数据
			String info = depositCouponReg.getRefAmt().toString() + depositCouponReg.getRandomSessionid();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, depositCouponReg.getSignature(), info), "验签失败");
		}
		
		// 2. 解析赠券赠送文件，存入到一个list中
		List<DepositCouponBatReg> batList = resolveFile(file);
		
		// 3. 判断该发卡机构是否需要审核
		boolean isNeedCheck ;
		 if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){//商户都要审核
			 isNeedCheck = true;
		 }else{
			 isNeedCheck = this.isCheckForDeposit(user);
		 }
		
		// 4.在数据中插入记录
		depositCouponReg = addDepositDetail(depositCouponReg, batList, count, isNeedCheck, user, limitId);
		
		// 5.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositCouponReg, WorkflowConstants.DEPOSIT_COUPON_ADAPTER, 
								Long.toString(depositCouponReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送赠券赠送报文，同时扣减风险准备金和售卡配额（单张卡赠券赠送没有预赠券赠送）
			this.sendMsgAndDealCardRisk(depositCouponReg, user);
		}
		
	}
	
	/**
	 * 解析赠券赠送文件，得到赠券赠送明细对象
	 * @param file 赠券赠送文件
	 * @return
	 * @throws BizException
	 */
	private List<DepositCouponBatReg> resolveFile(File file) throws BizException {
		byte[] fileData = getFileByte(file);
		
		List lines = getLines(fileData);
		
		Assert.isTrue(CollectionUtils.isNotEmpty(lines) && lines.size() > 1, "文件无内容或格式错误");
		
		return resolveDepositDetail(lines);
	}
	
	/**
	 * 读取上传文件的二进制流
	 * @param file
	 * @return
	 * @throws BizException
	 */
	private byte[] getFileByte(File file) throws BizException {
		byte[] fileData;
		try {
			fileData = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(e, e);
			throw new BizException("上传文件时发生FileNotFoundException");
		} catch (IOException e) {
			logger.error(e, e);
			throw new BizException("上传文件时发生IOException");
		}
		return fileData;
	}
	
	/**
	 * 读取类容
	 * @param fileData
	 * @return
	 * @throws BizException
	 */
	private List getLines(byte[] fileData) throws BizException {
		// 读取内容.
		List lines = null;
		
		try {
			lines = IOUtils.readLines(new ByteArrayInputStream(fileData), DEFAULT_ENCODING);
		} catch (IOException e) {
			logger.error(e, e);
			throw new BizException("解析文件失败, 编码错误");
		}
		return lines;
	}
	
	/**
	 * 解析赠券赠送明细
	 * @return
	 */
	private List<DepositCouponBatReg> resolveDepositDetail(List lines) throws BizException {
		List<DepositCouponBatReg> list = new ArrayList<DepositCouponBatReg>();
		
		// 获取表头字段数目
		int fieldNum = 0;
		if(lines.size()!=0){
			fieldNum = ((String) lines.get(0)).split(DEFAULT_SEQ).length;
		}
		
		// 记录卡号, 用作判断是否重复.
		Set<String> cardNoSet = new HashSet<String>();
		
		// 解析赠券赠送明细， 从第二行开始解析
		// 赠券赠送格式为，即第一行为：卡号,赠送赠券数
		DepositCouponBatReg batReg = null;
		String depositLine = "";
		for (int i = 1, n = lines.size(); i < n; i++) {
			depositLine = (String) lines.get(i);
			
			// 空行略过.
			if (StringUtils.isEmpty(depositLine)) {
				continue;
			}
			
			String[] arr = depositLine.split(DEFAULT_SEQ, -1);
			
			// 检查卡号
			checkCardNo(arr, i, fieldNum, cardNoSet);
			
			// 取得赠券赠送明细对象
			batReg = getBatRegFromDetail(arr);
			
			list.add(batReg);
		}
		
		return list;
	}
	
	/**
	 * 对赠券赠送明细里的卡号，进行检查
	 * @param arr 单条记录的数组
	 * @param count 记录的条数的序号
	 * @param fieldNum 模板中的明细个数
	 * @param cardNoSet 赠券赠送明细里的卡号
	 * @throws BizException
	 */
	private void checkCardNo(String[] arr, int count, int fieldNum, 
			Set<String> cardNoSet) throws BizException {
		// 赠券赠送明细元素个数要和模板配置明细个数一致.
		if (arr.length != fieldNum) {
			String msg = "明细第" + count + "行格式错误,元素不为" + fieldNum + "个";
			logger.error(msg);
			throw new BizException(msg);
		}
		
		if (ArrayUtils.isEmpty(arr)) {
			throw new BizException("解析出的数组为空");
		}
		
		//取得卡号,为空表示有错，抛出异常，跳出循环
		String cardNo = arr[0];
		if (StringUtils.isEmpty(cardNo)) {
			throw new BizException("赠券赠送明细第" + count + " 行格式错误，卡号不能为空。");
		}
		
		//卡号不能重复
		if (cardNoSet.contains(cardNo)) {
			throw new BizException("卡号[" + cardNo + "]的记录重复");
		}
		cardNoSet.add(cardNo);
	}
	
	/**
	 * 从明细中取得赠券赠送明细对象
	 * @param arr
	 * @return
	 */
	private DepositCouponBatReg getBatRegFromDetail(String[] arr) throws BizException {
		DepositCouponBatReg batReg = new DepositCouponBatReg();
		Assert.isTrue(arr.length == 2, "明细数组的长度有误");
		batReg.setCardId(arr[0]);
		Assert.isTrue(NumberUtils.isNumber(arr[1]), "赠送赠券数必须为数值");
		
		BigDecimal couponAmt = NumberUtils.createBigDecimal(arr[1]);
		Assert.isTrue(AmountUtil.gt(couponAmt, BigDecimal.ZERO), "赠送赠券数必须大于0");
		batReg.setCouponAmt(couponAmt);
		
		return batReg;
	}
	
	/**
	 * 添加赠券赠送明细到批量赠券赠送明细表
	 * @param list
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private DepositCouponReg addDepositDetail(DepositCouponReg depositCouponReg, List<DepositCouponBatReg> batList, 
			Long count, boolean isNeedCheck, UserInfo user, String limitId) throws BizException {
		Assert.isTrue(batList.size() == count.intValue(), "赠券赠送文件中的赠券赠送笔数与页面录入的赠券赠送笔数不一致");
		BigDecimal totalPointAmt = BigDecimal.ZERO;
		
		CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(depositCouponReg.getCouponClass());
		Assert.notNull(couponClassDef, "赠券类型[" + depositCouponReg.getCouponClass() + "]不存在");
		
		// 如果需要审核，则状态为待审核
		String status = "";
		if (isNeedCheck) {
			status = RegisterState.WAITED.getValue();
		} else {
			status = RegisterState.WAITEDEAL.getValue();
		}
		
		String roleType = user.getRole().getRoleType();
		// 商户做赠券赠送时
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			depositCouponReg.setDepositBranch(user.getMerchantNo());
		} 
		// 部门赠券赠送时
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			depositCouponReg.setDepositBranch(user.getDeptId());
		} else {
			depositCouponReg.setDepositBranch(user.getBranchNo());
		}
		
		depositCouponReg.setStatus(status);
		depositCouponReg.setDepositDate(SysparamCache.getInstance().getWorkDateNotFromCache());
		depositCouponReg.setUpdateTime(new Date());
		depositCouponReg.setUpdateUser(user.getUserId());
		depositCouponReg.setEntryUserid(user.getUserId());
		depositCouponReg.setFileDeposit(Symbol.YES); // 是以文件方式赠券赠送
		
		this.depositCouponRegDAO.insert(depositCouponReg);
		
		List<DepositCouponBatReg> list = new ArrayList<DepositCouponBatReg>();
		// 在明细表里插入记录
		for (DepositCouponBatReg reg : batList) {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(reg.getCardId());
			Assert.notNull(cardInfo, "要赠券赠送的卡号[" + reg.getCardId() + "]不存在");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + reg.getCardId() + "]不是“正常(已激活)”状态");
			Assert.equals(cardInfo.getCardIssuer(), depositCouponReg.getCardBranch(), 
					"卡号[" + reg.getCardId() + "]所属的发卡机构与页面选择的发卡机构[" + depositCouponReg.getCardBranch() + "]不一致");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "要赠券赠送的卡号[" + reg.getCardId() + "]的账户不存在");
			
			CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClass, "要赠券赠送的卡号[" + reg.getCardId() + "]所属卡类型[" + cardInfo.getCardSubclass() + "]不存在");
			
			// 判断是否有给该卡赠券赠送的权限
			if (RoleType.CARD_SELL.getValue().equals(user.getRole().getRoleType())) {
				Assert.isTrue(hasCardSellPrivilegeByCardId(user.getRole().getBranchNo(), reg.getCardId(), limitId), 
						"该售卡代理没有权限为该卡[" + reg.getCardId() + "]赠券赠送");
			}
			
			// 如果没有权限赠券赠送则报错
			if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
				throw new BizException("没有给该卡[" + reg.getCardId() + "]赠券赠送的权限");
			}
			
			totalPointAmt = AmountUtil.add(totalPointAmt, reg.getCouponAmt());
			
			// 积分折算金额 = 赠送赠券*积分折算率
			BigDecimal refAmt = AmountUtil.multiply(reg.getCouponAmt(), couponClassDef.getSettRate());
			
			reg.setDepositBatchId(depositCouponReg.getDepositBatchId());
			reg.setRefAmt(refAmt);
			reg.setStatus(status);
			reg.setInsertTime(new Date());
			
			list.add(reg);
		}
		
		this.depositCouponBatRegDAO.insertBatch(list);
		
		totalPointAmt = AmountUtil.format(totalPointAmt);
		BigDecimal pagePointSum = AmountUtil.format(depositCouponReg.getCouponAmt());
		
		Assert.isTrue(AmountUtil.et(totalPointAmt, pagePointSum), 
				"赠券赠送文件里的金额总和[" + totalPointAmt + "]与页面录入的总金额[" + pagePointSum + "]不一致");
		
		return depositCouponReg;
	}
	
	private boolean hasCardSellPrivilegeByCardId(String proxyId, String cardId, String limitId){
		CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		
		return hasCardSellPrivilege(proxyId, cardInfo.getCardIssuer(), limitId);
	}
	
	private boolean hasCardSellPrivilege(String proxyId, String cardBranch, String limitId) {
		// 判断是否属于该发卡机构的售卡代理
		BranchProxyDAO branchProxyDAO = (BranchProxyDAO) SpringContext.getService("branchProxyDAOImpl");
		BranchProxyKey branchProxyKey = new BranchProxyKey(proxyId, cardBranch, ProxyType.SELL.getValue());
		if (!branchProxyDAO.isExist(branchProxyKey)) {
			return false;
		}
		
		// 判断是否拥有该权限
		SaleProxyPrivilegeDAO saleProxyPrivilegeDAO = (SaleProxyPrivilegeDAO) SpringContext.getService("saleProxyPrivilegeDAOImpl");
		List<HashMap> privlegeList = saleProxyPrivilegeDAO.findSaleProxy(proxyId, cardBranch, limitId);

		return CollectionUtils.isNotEmpty(privlegeList);
	}
}
