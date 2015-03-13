package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.IOUtil;
import flink.util.Paginater;
import flink.util.SpringContext;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.dao.RebateCardRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.RebateCardReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.DepositCancelFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PreDepositType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RebateFromType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.entity.type.SubacctType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.BaseDataService;
import gnete.card.service.CardRiskService;
import gnete.card.service.DepositService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.BranchBizConfigCache;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("depositService")
public class DepositServiceImpl implements DepositService {
	
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;
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
	private BaseDataService baseDataService;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private RebateCardRegDAO rebateCardRegDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	
	/**
	 * 默认分隔符
	 */
	private static final String DEFAULT_SEQ = ",";
	
	static final Logger logger = Logger.getLogger(DepositServiceImpl.class);
	
	public long addDepositReg(DepositReg depositReg, UserInfo user, String serialNo) throws BizException {
		
		Assert.notNull(depositReg, "添加的充值登记对象不能为空");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notEmpty(depositReg.getCardId(), "要充值的卡号不能为空");
		Assert.isTrue(AmountUtil.gt(depositReg.getAmt(), BigDecimal.ZERO), "充值金额必须大于0");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		// 2. 检查卡信息
		CardInfo cardInfo = this.checkCardNo(depositReg, user);
		
		// 3. 判断该卡是否需要审核
		boolean isNeedCheck = this.isCheckForDeposit(user);
		
		// 3. 在登记簿中插入数据
		depositReg = this.insertDepositRegData(depositReg, isNeedCheck, user);
		
		// 4.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositReg, WorkflowConstants.DEPOSIT_BATCH_ADAPTER, 
								Long.toString(depositReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送充值报文，同时扣减风险准备金和售卡配额（单张卡充值没有预充值）
			this.sendMsgAndDealCardRisk(depositReg, cardInfo, user);
		}
		
		return depositReg.getDepositBatchId();
	}
	
	private CardInfo checkCardNo(DepositReg depositReg, UserInfo user) throws BizException {
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositReg.getCardId());
		
		Assert.notNull(cardInfo, "卡号[" + depositReg.getCardId() + "]不存在");
		Assert.equals(CardState.ACTIVE.getValue(), cardInfo.getCardStatus(), "卡号[" + depositReg.getCardId() + "]不是正常状态，不能充值！");
		
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
		Assert.notNull(acctInfo, "卡号[" + depositReg.getCardId() + "]的账户不存在");
		
		CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
		Assert.notNull(cardSubClass, "卡号[" + depositReg.getCardId() + "]所属卡类型不存在");

		// 该卡类型的卡能否充值
		Assert.equals(cardSubClass.getDepositFlag(), Symbol.YES, "卡号[" + depositReg.getCardId() + "]所属的卡类型不能做充值");
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
			throw new BizException("没有给该卡["+ depositReg.getCardId() +"]充值的权限");
		}
		
		/*
		 * 单张卡售卡不能指定返利卡
		 * // 如果是返利指定卡状态，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(depositReg.getRebateType())) {
			String rebateCard = depositReg.getRebateCard();
			Assert.notEmpty(rebateCard, "返利指定卡式返利卡号不能为空");
			
			CardInfo rebateCardInfo = (CardInfo)this.cardInfoDAO.findByPk(rebateCard);
			Assert.notNull(rebateCardInfo, "指定的返利卡卡号[" + rebateCard + "]不存在");
			Assert.notEmpty(rebateCardInfo.getAcctId(), "指定的返利卡卡号[" + rebateCard + "]的账户不存在");
			
			// 如果返利卡不在本批卖出的卡中，则需验证状态是否已售出
			if (!StringUtils.equals(cardInfo.getCardId(), rebateCard)) {
				Assert.equals(CardState.ACTIVE.getValue(), rebateCardInfo.getCardStatus(), "返利卡[" + rebateCard + "]未激活");
			}
			// 任意读取一张卡
			Assert.equals(cardInfo.getCardIssuer(), rebateCardInfo.getCardIssuer(), "所充值卡与返利指定卡不属于同一发卡机构");
		}*/
		
		return cardInfo;
	}
	
	/**
	 * 判断登录的机构充值是否需要审核。
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForDeposit(UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		boolean isNeedCheck = false;
		
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置充值审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getDepositCheck());
		}
		return isNeedCheck;
	}
	
	/**
	 * 判断发卡机构发的卡充值是否需要审核。同时如果是手工指定返利且非发卡机构登记充值则需要发卡机构审核
	 * @param depositReg
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForDepositBat(DepositReg depositReg, UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		boolean isCheckForDeposit = false;
		
		// 发卡机构的话需要检查是否配置了审核权限
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置充值审核权限");
			
			isCheckForDeposit = StringUtils.equals(Symbol.YES, config.getDepositCheck());
		} else {
			isCheckForDeposit = depositReg.isManual();
		}
		
//		SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
//		Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置充值审核权限");
		
		
		return isCheckForDeposit;
	}
	
	/**
	 * 为充值登记对象赋值，插入单张卡充值记录.(单张卡充值没有预充值)
	 * @param depositReg
	 * @param isCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private DepositReg insertDepositRegData(DepositReg depositReg, 
			boolean isCheck, UserInfo user) throws BizException {

		// 如果需要审核，则状态为待审核（单张卡充值没有预充值）
		if (isCheck) {
			depositReg.setStatus(RegisterState.WAITED.getValue());
		} else {
			depositReg.setStatus(RegisterState.WAITEDEAL.getValue());
		}
		
		// 数据预处理
		depositReg.setUpdateTime(new Date());
		depositReg.setUpdateUser(user.getUserId());
		depositReg.setEntryUserId(user.getUserId());
		depositReg.setFileDeposit(Symbol.NO); // 不是以文件方式充值
		depositReg.setCancelFlag(Symbol.NO); // 没有取消
		depositReg.setDepositCancel(DepositCancelFlag.NORMAL.getValue()); // 正常充值
		// 取得系统工作日，作为充值日期
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositReg.setDepositDate(workDate);

		// 存储充值登记簿
		this.depositRegDAO.insert(depositReg);
		
		return depositReg;
	}
	
	/**
	 * 发送充值报文，同时扣减操作员的配额和发卡机构风险准备金。（单张卡充值没有预充值）
	 * @throws BizException
	 */
	private void sendMsgAndDealCardRisk(DepositReg depositReg, CardInfo cardInfo, UserInfo user) throws BizException {
		// 实时充值则发送售卡报文
		MsgSender.sendMsg(MsgType.DEPOSIT, depositReg.getDepositBatchId(), user.getUserId());
		
		// 扣减操作员配额和发卡机构的风险准备金
		this.deductUserAmtAndCardRisk(depositReg, user);
		
		/*String roleType = user.getRole().getRoleType();
		// 计算风险保证金的扣减金额
		BigDecimal riskAmt = AmountUtil.add(depositReg.getAmt(), depositReg.getRebateAmt());
		// 次卡需要读取清算金额
		if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
			String freqClass = cardSubClassDef.getFrequencyClass();
			Assert.notEmpty(freqClass, "该卡所属卡子类型没有定义次数卡子类型");
			
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = new BigDecimal(0.0);
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), depositReg.getAmt());
			}
		}
		
		// 扣减风险准备金和充值售卡额度
		if (!RoleType.CARD.getValue().equals(roleType)) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositReg.getDepositBatchId());	// 充值登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
			branchSellReg.setAmt(riskAmt); //扣的是清算金额
			branchSellReg.setCardBranch(StringUtils.trim(cardInfo.getCardIssuer()));		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositReg.getDepositBranch());	// 充值机构
			if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(user.getUserId(), depositReg.getDepositBranch(), riskAmt);
		
		// 扣减风险保证金
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(StringUtils.trim(cardInfo.getCardIssuer()));	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);*/
	}
	public Paginater findDepositRegCancelPage(Map<String, Object> params,int pageNumber, int pageSize) throws BizException {
		return this.depositRegDAO.findDepositRegCancel(params, pageNumber, pageSize);
	}
	
	/**
	 * 预充值撤销
	 */
	public void addDepositPreCancel(DepositBatReg depositReg, UserInfo user) throws BizException {
		Assert.notNull(depositReg, "要撤销的充值对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notNull(depositReg.getDepositBatchId(), "要撤销的充值批次号不能为空");
		
		DepositReg oldDepositReg = (DepositReg) this.depositRegDAO.findByPk(depositReg.getDepositBatchId());
		Assert.notNull(oldDepositReg, "要撤销的充值批次为[" + depositReg.getDepositBatchId() + "]的充值记录已经不存在");
		// 根据原充值记录的状态做不同的操作
		if (RegisterState.INACTIVE.getValue().equals(oldDepositReg.getStatus())) {
			dealDepositPreCancelForInactive(oldDepositReg, user);
		} else {
			throw new BizException("只有“未激活”状态的才可以做预充值撤销。");
		}
		
		// 为充值撤销对象设值
		oldDepositReg.setEntryUserId(user.getUserId());
		oldDepositReg.setUpdateTime(new Date());
		oldDepositReg.setUpdateUser(user.getUserId());
		oldDepositReg.setStatus(RegisterState.CANCELED.getValue());
		oldDepositReg.setCancelFlag(Symbol.YES);
//		oldDepositReg.setDepositCancel(DepositCancelFlag.CANCEL.getValue());
		this.depositRegDAO.update(oldDepositReg);
	}
	
	/**
	 * 预充值撤销
	 * @Date 2013-3-29上午11:35:11
	 * @return void
	 */
	private void dealDepositPreCancelForInactive(DepositReg oldDepositReg, UserInfo user) throws BizException {
		// 批量要改明细表的状态
		if (StringUtils.isEmpty(oldDepositReg.getCardId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", oldDepositReg.getDepositBatchId());
			List<DepositBatReg> batList = this.depositBatRegDAO.findDepositBatReg(params);
			
			Assert.notEmpty(batList, "找不到原预充值登记[" + oldDepositReg.getDepositBatchId() + "]的明细信息。");
			for (DepositBatReg reg : batList) {
				CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(reg.getCardId());
				Assert.notNull(cardInfo, "卡号[" + reg.getCardId() + "]不存在");
				
				reg.setStatus(RegisterState.CANCELED.getValue());
				reg.setUpdateTime(new Date());
				reg.setUpdateUser(user.getUserId());
			}
			this.depositBatRegDAO.updateBatch(batList);
			
			// 在充值返利明细表删除记录
			List<RebateCardReg> rebateList = this.rebateCardRegDAO.findRebateCardRegList(params);
			for (RebateCardReg rebateCardReg : rebateList) {
				logger.debug("删除充值[" + rebateCardReg.getRegId() + "]在充值返利明细中的数据");
				this.rebateCardRegDAO.delete(rebateCardReg.getRegId());
			}
		} 
	}
	
	public long addDepositBatReg(DepositReg depositReg, DepositBatReg depositBatReg, 
			List<RebateCardReg> rebateCardList, UserInfo user, String serialNo) throws BizException {
		/*Assert.notNull(depositReg, "添加的批量充值对象不能为空");
		Assert.notNull(depositBatRegList, "添加的批量充值明细对象不能为空");
		String createUserId = user.getUserId();
		String roleType = user.getRole().getRoleType();
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(ParaMgr.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		int count;
		long batchId = 0;
		DepositBatReg depositBatReg;
		
		// 如果是返利指定卡状态，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(depositReg.getRebateType())) {
			String rebateCard = depositReg.getRebateCard();
			Assert.notEmpty(rebateCard, "返利指定卡式返利卡号不能为空");
			
			CardInfo rebateCardInfo = (CardInfo)this.cardInfoDAO.findByPk(rebateCard);
			Assert.notNull(rebateCardInfo, "指定的返利卡卡号[" + rebateCard + "]不存在");
			Assert.notEmpty(rebateCardInfo.getAcctId(), "指定的返利卡卡号[" + rebateCard + "]的账户不存在");
			
			Assert.isTrue(CardState.ACTIVE.getValue().equals(rebateCardInfo.getCardStatus()), "返利卡[" + rebateCard + "]未激活");

			// 任意读取一张卡
			CardInfo saleCard = (CardInfo)this.cardInfoDAO.findByPk(depositBatRegList.get(0).getCardId());
			Assert.notNull(saleCard, "卡号[" + rebateCard + "]不存在");
			Assert.notEmpty(saleCard.getAcctId(), "卡号[" + rebateCard + "]的账户不存在");
			
			Assert.isTrue(saleCard.getCardIssuer().equals(rebateCardInfo.getCardIssuer()), "所充值卡与返利指定卡不属于同一发卡机构");
		}
		
		// 预充值
		boolean preDeposit = false;
		if (PreDepositType.PRE_DEPOSIT.getValue().equals(depositReg.getPreDeposit())){
			preDeposit = true;
		}
		
		// 存储充值登记簿
		depositReg.setUpdateTime(new Date());
		depositReg.setUpdateUser(createUserId);
		depositReg.setEntryUserId(createUserId);
		depositReg.setFileDeposit(Symbol.NO);
		depositReg.setCancelFlag(Symbol.NO);// 是否已经撤销
		depositReg.setDepositCancel(DepositCancelFlag.NORMAL.getValue());// 正常充值
		
		String status = null;
		boolean toCheck = false;
		if(preDeposit){
			// 如果是手工指定返利且非发卡机构登记则需要发卡机构审核
			if (depositReg.isManual() && !RoleType.CARD.getValue().equals(user.getRole().getRoleType())) {
				status = RegisterState.WAITED.getValue();
				toCheck = true;
			} else {
				status = RegisterState.INACTIVE.getValue();
			}
		}
		else {
			if (depositReg.isManual()) {	// 手工指定返利则是待审核状态
				status = RegisterState.WAITED.getValue();
				toCheck = true;
			} else {
				status = RegisterState.WAITEDEAL.getValue();
			}
		}
		depositReg.setStatus(status);
		
		depositRegDAO.insert(depositReg);
		batchId = depositReg.getDepositBatchId();
		
		// 存储批量充值明细登记簿
		count = depositBatRegList.size();
		CardInfo cardInfo = null;
		BigDecimal total = new BigDecimal(0.0);
		BigDecimal totalRisk = new BigDecimal(0.0);
		for(int i = 0; i < count; i++){
			depositBatReg = depositBatRegList.get(i);
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositBatReg.getCardId());
			
			Assert.notNull(cardInfo, "卡号[" + depositBatReg.getCardId() + "]的信息不存在");
			Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + depositBatReg.getCardId() + "]的账户不存在，不能做充值");
			
			// 如果没有权限充值则报错
			if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
				throw new BizException("没有给该卡["+ depositBatReg.getCardId() +"]充值的权限");
			}
			
			// 如果是次卡充值，需验证该卡有无次卡子账户
			if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("acctId", cardInfo.getAcctId());
				params.put("subacctTypes", new String[]{SubacctType.ACCU.getValue()});
				List<SubAcctBal> subaccts = this.subAcctBalDAO.findSubAcctBal(params);
				if (CollectionUtils.isEmpty(subaccts)) {
					throw new BizException("该卡["+ depositBatReg.getCardId() +"]没有次卡子账户，不能进行次数充值");
				}
			}

			total = AmountUtil.add(total, depositBatReg.getRealAmt());
			totalRisk = AmountUtil.add(totalRisk, AmountUtil.add(depositBatReg.getAmt(), depositBatReg.getRebateAmt())); // 充值金额 + 返利金额
			depositBatReg.setDepositBatchId(batchId);
			depositBatReg.setCardClass(depositReg.getCardClass());
			depositBatReg.setUpdateTime(depositReg.getUpdateTime());
			depositBatReg.setUpdateUser(createUserId);
			depositBatReg.setStatus(depositReg.getStatus());
			depositBatReg.setEntryUserId(createUserId);
			depositBatReg.setRandomSessionId(depositReg.getRandomSessionId());
			depositBatReg.setSignature(depositReg.getSignature());
			
			depositBatRegDAO.insert(depositBatReg);
		}
		
		try {
			// 预充值在激活时激活:不是预充值，同时不需要审核时才需要立即扣减风险保证金
			 if (!preDeposit && !toCheck) {
				// 次卡需要读取清算金额
				if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
					String cardSubClass = cardInfo.getCardSubclass();
					
					CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
					String freqClass = cardSubClassDef.getFrequencyClass();
					Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
					
					AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
					if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
						totalRisk = new BigDecimal(0.0);
					} else {
						totalRisk = AmountUtil.multiply(accuClassDef.getSettAmt(), depositReg.getAmt());
					}
				}
				
				// 扣减风险准备金和充值售卡额度
				if (!RoleType.CARD.getValue().equals(user.getRole().getRoleType())) {
					BranchSellReg branchSellReg = new BranchSellReg(); 
					branchSellReg.setId(depositReg.getDepositBatchId());	// 充值登记薄的ID
					branchSellReg.setAdjType(AdjType.MANAGE.getValue());
					branchSellReg.setAmt(total);							// 实收金额
					branchSellReg.setCardBranch(cardInfo.getCardIssuer());		// 发卡机构
					branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
					branchSellReg.setSellBranch(depositReg.getDepositBranch());	// 充值机构
					if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
						branchSellReg.setSellType(SellType.DEPT.getValue());
					} else {
						branchSellReg.setSellType(SellType.BRANCH.getValue());
					}
					this.cardRiskService.activateSell(branchSellReg);
				}
				// 扣减操作员额度
				this.cardRiskService.deductUserAmt(user.getUserId(), depositReg.getDepositBranch(), total);
				
				CardRiskReg cardRiskReg = new CardRiskReg();
				cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
				cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
				cardRiskReg.setAmt(totalRisk);						// 充值金额 + 返利金额
				cardRiskReg.setBranchCode(cardInfo.getCardIssuer());	// 发卡机构
				cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
				this.cardRiskService.activateCardRisk(cardRiskReg);
				// 批量应统一发报文
				MsgSender.sendMsg(MsgType.DEPOSIT, depositReg.getDepositBatchId(), createUserId);
			}
			 
			else if (toCheck) {
				this.workflowService.startFlow(depositReg, WorkflowConstants.DEPOSIT_BATCH_ADAPTER, 
						Long.toString(depositReg.getDepositBatchId()), user);
			}
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}*/
		
		long batchId = _addDepositBatReg(depositReg, depositBatReg, rebateCardList, user, serialNo);
		return batchId;
	}
	
	/**
	 * 批量充值处理
	 * @param depositReg
	 * @param depositBatRegList
	 * @param user
	 * @param serialNo
	 * @return
	 * @throws BizException
	 */
	private long _addDepositBatReg(DepositReg depositReg, DepositBatReg depositBatReg, 
			List<RebateCardReg> rebateCardList, UserInfo user, String serialNo) throws BizException {
		Assert.notNull(depositReg, "添加的充值登记对象不能为空");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notNull(depositBatReg, "批量充值明细对象不能为空");
		if (RebateType.CARDS.getValue().equals(depositReg.getRebateType())) {
			Assert.notEmpty(rebateCardList, "多张卡返利登记对象不能为空");
		}
		
		Assert.isTrue(AmountUtil.gt(depositReg.getAmt(), BigDecimal.ZERO), "充值总金额必须大于0");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		//手工处理时,要重新计算返利
		if(depositReg.isManual()){
			//明细的返利金额 = 充值金额*（总的返利金额/充值总金额）
			BigDecimal rebateAmt = AmountUtil.multiply(depositBatReg.getAmt(), AmountUtil.divide(depositReg.getRebateAmt(), depositReg.getAmt()));
			depositBatReg.setRebateAmt(rebateAmt);
		}

		// 2. 检查卡信息
		Object[] objects = this.checkCardNoBatRev(depositReg, depositBatReg, rebateCardList, user);
		CardInfo cardInfo = (CardInfo) objects[0];
		List<DepositBatReg> batRegList = (List<DepositBatReg>) objects[1];
		
		// 3. 判断该卡是否需要审核
		boolean isNeedCheck = this.isCheckForDepositBat(depositReg, user);
		
		// 4. 为充值登记对象，批量充值明细对象赋值及批量返利明细登记簿（多张卡返利时才有），同时在数据库中插入记录
		depositReg = this.setDepositRegValue(depositReg, cardInfo, batRegList, rebateCardList, isNeedCheck, user);
		
		// 5. 如果需要审核，则启动充值审核流程。否则则发送充值报文，同时扣减发卡机构风险保证金和操作员配额
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositReg, WorkflowConstants.DEPOSIT_BATCH_ADAPTER, 
								Long.toString(depositReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 实时充值的话，则直接发送充值报文，同时扣减风险保证金
			if (PreDepositType.REAL_DEPOSIT.getValue().equals(depositReg.getPreDeposit())){
				MsgSender.sendMsg(MsgType.DEPOSIT, depositReg.getDepositBatchId(), user.getUserId());
				
				this.deductUserAmtAndCardRisk(depositReg, user);
			}
		}
		
		return depositReg.getDepositBatchId();
	}
	
	@SuppressWarnings("unused")
	private Object[] checkCardNoBat(DepositReg depositReg, List<DepositBatReg> depositBatRegList,
			List<RebateCardReg> rebateCardList, UserInfo user) throws BizException {
		List<DepositBatReg> list = new ArrayList<DepositBatReg>();
		
		// 批量充值时的卡号字段应该为空。故取第一张卡号
		CardInfo depositCardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositBatRegList.get(0).getCardId());
		
		Assert.notNull(depositCardInfo, "卡号[" + depositBatRegList.get(0).getCardId() + "]不存在");
		Assert.equals(CardState.ACTIVE.getValue(), depositCardInfo.getCardStatus(), 
				"卡号[" + depositBatRegList.get(0).getCardId() + "]不是正常状态，不能充值！");
		
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(depositCardInfo.getAcctId());
		Assert.notNull(acctInfo, "卡号[" + depositBatRegList.get(0).getCardId() + "]的账户不存在");
		
		CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(depositCardInfo.getCardSubclass());
		Assert.notNull(cardSubClass, "卡号[" + depositBatRegList.get(0).getCardId() + "]所属卡类型不存在");

		// 该卡类型的卡能否充值
		Assert.equals(cardSubClass.getDepositFlag(), Symbol.YES, "卡号[" + depositBatRegList.get(0).getCardId() + "]所属的卡类型不能做充值");
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDeposit(user, depositCardInfo)){
			throw new BizException("没有给该卡[" + depositBatRegList.get(0).getCardId() + "]充值的权限");
		}
		
		// 如果是返利指定卡的话，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(depositReg.getRebateType())) {
			// 检查返利卡号
			CardInfo rebateCardInfo = this.checkRebateCard(depositReg.getRebateCard());
			
			// 任意读取一张卡
			Assert.equals(depositCardInfo.getCardIssuer(), rebateCardInfo.getCardIssuer(), "返利指定的卡与所充值卡不属于同一发卡机构");
		}
		// 2012-6-28 新加， 如果是返利多张卡的话，要检查返利的每一张卡
		else if (RebateType.CARDS.getValue().equals(depositReg.getRebateType())) {
			BigDecimal rebateSum = new BigDecimal("0");
			for (RebateCardReg rebate : rebateCardList) {
				CardInfo rebateCard = this.checkRebateCard(rebate.getCardId());
				
				Assert.equals(depositCardInfo.getCardIssuer(), rebateCard.getCardIssuer(), 
						"返利卡[" + rebate.getCardId() + "]与所充值卡不属于同一发卡机构");
				
				rebateSum = AmountUtil.add(rebateSum, rebate.getRebateAmt());
			}
			rebateSum = AmountUtil.format(rebateSum);
			BigDecimal rebateAmt = AmountUtil.format(depositReg.getRebateAmt());
			
			Assert.isTrue(AmountUtil.et(rebateSum, rebateAmt), 
					"返利卡的金额总和[" + rebateSum + "]与充值登记簿中的返利金额[" + rebateAmt + "]不相等");
		}
		
		// 检查批量充值明细
		for (int i = 0; i < depositBatRegList.size(); i++ ) {
			DepositBatReg batReg = depositBatRegList.get(i);
			
			String cardId = batReg.getCardId();
			Assert.notEmpty(cardId, "第[" + (i + 1) + "]张卡，卡号不能为空！");
			
			// 查卡信息表
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);

			Assert.equals(cardInfo.getAppOrgId(), depositCardInfo.getAppOrgId(), String.format(
					"第%s张卡[%s]的领卡机构[%s]与第1张卡的领卡机构[%s]不同，不能在同一批次批量充值！", i + 1, cardId, 
						cardInfo.getAppOrgId(), depositCardInfo.getAppOrgId()));

			// 如果是旧卡
			if (batReg.getCardId().length() == 18) {
				// 按旧校验位生成的第一种方法生成的
				if (cardInfo == null) {
					String cardPrex = cardId.substring(0, cardId.length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				}
				
				batReg.setCardId(cardId);
			}
			Assert.notNull(cardInfo, "第[" + (i + 1) + "]张卡，卡号[" + batReg.getCardId() + "]不存在！");
			Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + batReg.getCardId() + "]的账户不存在，不能做充值");
			
			// 如果没有权限充值则报错
			if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
				throw new BizException("没有给该卡[" + batReg.getCardId() + "]充值的权限");
			}
			
			// 如果是次卡充值，需验证该卡有无次卡子账户
			if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("acctId", cardInfo.getAcctId());
				params.put("subacctTypes", new String[] { SubacctType.ACCU.getValue() });
			
				List<SubAcctBal> subaccts = this.subAcctBalDAO.findSubAcctBal(params);
				Assert.notEmpty(subaccts, "该卡[" + batReg.getCardId() + "]没有次卡子账户，不能进行次数充值");
			}
			
			list.add(batReg);
		}
		
		return new Object[] { depositCardInfo, list };
	}
	
	private Object[] checkCardNoBatRev(DepositReg depositReg, DepositBatReg depositBatReg,
			List<RebateCardReg> rebateCardList, UserInfo user) throws BizException {
		List<DepositBatReg> list = new ArrayList<DepositBatReg>();
		
		// 批量充值时的卡号字段应该为空。故取第一张卡号
		CardInfo firstCard = (CardInfo) this.cardInfoDAO.findByPk(depositReg.getStrCardId());
		
		Assert.notNull(firstCard, "卡号[" + depositReg.getStrCardId() + "]不存在");
		Assert.equals(CardState.ACTIVE.getValue(), firstCard.getCardStatus(), "卡号[" + depositReg.getStrCardId() + "]不是正常状态，不能充值！");
		
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(firstCard.getAcctId());
		Assert.notNull(acctInfo, "卡号[" + depositReg.getStrCardId() + "]的账户不存在");
		
		CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(firstCard.getCardSubclass());
		Assert.notNull(cardSubClass, "卡号[" + depositReg.getStrCardId() + "]所属卡类型不存在");

		// 该卡类型的卡能否充值
		Assert.equals(cardSubClass.getDepositFlag(), Symbol.YES, "卡号[" + depositReg.getStrCardId() + "]所属的卡类型不能做充值");
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDeposit(user, firstCard)){
			throw new BizException("没有给该卡[" + depositReg.getStrCardId() + "]充值的权限");
		}
		
		// 如果是返利指定卡的话，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(depositReg.getRebateType())) {
			// 检查返利卡号
			CardInfo rebateCardInfo = this.checkRebateCard(depositReg.getRebateCard());
			
			// 任意读取一张卡
			Assert.equals(firstCard.getCardIssuer(), rebateCardInfo.getCardIssuer(), "返利指定的卡与所充值卡不属于同一发卡机构");
		}
		// 2012-6-28 新加， 如果是返利多张卡的话，要检查返利的每一张卡
		else if (RebateType.CARDS.getValue().equals(depositReg.getRebateType())) {
			BigDecimal rebateSum = BigDecimal.ZERO;
			
			for (RebateCardReg rebate : rebateCardList) {
				CardInfo rebateCard = this.checkRebateCard(rebate.getCardId());
				
				Assert.equals(firstCard.getCardIssuer(), rebateCard.getCardIssuer(), "返利卡[" + rebate.getCardId() + "]与所充值卡不属于同一发卡机构");
				
				rebateSum = AmountUtil.add(rebateSum, rebate.getRebateAmt());
			}
			rebateSum = AmountUtil.format(rebateSum);
			BigDecimal rebateAmt = AmountUtil.format(depositReg.getRebateAmt());
			
			Assert.isTrue(AmountUtil.et(rebateSum, rebateAmt), "返利卡的金额总和[" + rebateSum + "]与充值登记簿中的返利金额[" + rebateAmt + "]不相等");
		}
		
		String cardId = "";
		CardInfo info = null;
		DepositBatReg batReg = null;
		BigDecimal sumAmt = BigDecimal.ZERO;
		
		List<CardInfo> cardInfoList = this.cardInfoDAO.getCardList(depositReg.getStrCardId(), depositReg.getEndCardId());
		for (int i = 0; i < cardInfoList.size(); i++) {
			info = cardInfoList.get(i);
			cardId = info.getCardId();
			
			Assert.equals(info.getCardIssuer(), firstCard.getCardIssuer(), "第[" + (i + 1) + "]张卡[" + cardId + "]与第[1]张卡不是同一发卡机构发的卡，不能在同一批次充值！");
			Assert.equals(info.getAppOrgId(), firstCard.getAppOrgId(), 
					String.format("第[%s]张卡[%s]的领卡机构[%s]与第[1]张卡的领卡机构[%s]不同，不能在同一批次充值！", i + 1, cardId, info.getAppOrgId(), firstCard.getAppOrgId()));
			Assert.notEmpty(info.getAcctId(), "卡号[" + cardId + "]的账户不存在，不能做充值");
			
			// 如果没有权限充值则报错
//			if (!baseDataService.hasRightsToDeposit(user, info)){
//				throw new BizException("没有给该卡[" + cardId + "]充值的权限");
//			}
			
			// 如果是次卡充值，需验证该卡有无次卡子账户
			if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("acctId", info.getAcctId());
				params.put("subacctTypes", new String[] { SubacctType.ACCU.getValue() });
			
				List<SubAcctBal> subaccts = this.subAcctBalDAO.findSubAcctBal(params);
				Assert.notEmpty(subaccts, "卡号[" + cardId + "]没有次卡子账户，不能进行次数充值");
			}
			
			batReg = new DepositBatReg();
			
			batReg.setCardId(cardId);
			batReg.setAmt(depositBatReg.getAmt());
			batReg.setRealAmt(depositBatReg.getRealAmt());
			batReg.setCardClass(depositReg.getCardClass());
			
			if (RebateType.CARD.getValue().equals(depositReg.getRebateType())
					|| RebateType.CARDS.getValue().equals(depositReg.getRebateType())) {
				batReg.setRebateAmt(BigDecimal.ZERO);
			} else {
				batReg.setRebateAmt(depositBatReg.getRebateAmt());
			}
			
			sumAmt = AmountUtil.add(sumAmt, batReg.getAmt());
			list.add(batReg);
		}
		Assert.isTrue(AmountUtil.et(sumAmt, depositReg.getAmt()), "充值的金额总和[" + sumAmt + "]与充值登记簿中的充值总金额[" + depositReg.getAmt() + "]不相等");
		
		return new Object[] { firstCard, list };
	}
	
	/**
	 * 4. 为充值登记对象及批量充值明细对象赋值，同时在数据库中插入记录
	 * 
	 * @param depositReg
	 * @param cardInfo
	 * @param batRegList
	 * @param isNeedCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private DepositReg setDepositRegValue(DepositReg depositReg, CardInfo cardInfo, 
			List<DepositBatReg> batRegList, List<RebateCardReg> rebateCardList, boolean isNeedCheck, UserInfo user) throws BizException {
		// 存储充值登记簿
		depositReg.setUpdateTime(new Date());
		depositReg.setUpdateUser(user.getUserId());
		depositReg.setEntryUserId(user.getUserId());
		depositReg.setFileDeposit(Symbol.NO);
		depositReg.setCancelFlag(Symbol.NO);// 是否已经撤销
		depositReg.setDepositCancel(DepositCancelFlag.NORMAL.getValue());// 正常充值
		// 取得系统工作日，作为售卡时间
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositReg.setDepositDate(workDate);
		if (!RebateType.CARD.getValue().equals(depositReg.getRebateType())) {
			depositReg.setRebateCard("");
		}
		
		String status = "";
		// 如果需要审核，则状态为待审核
		if (isNeedCheck) {
			status = RegisterState.WAITED.getValue();
		} else {
			// 如果是预充值
			if (PreDepositType.PRE_DEPOSIT.getValue().equals(depositReg.getPreDeposit())) {
				status = RegisterState.INACTIVE.getValue();
			} else {
				status = RegisterState.WAITEDEAL.getValue();
			}
		}
		depositReg.setStatus(status);
		// 存储充值登记簿
		this.depositRegDAO.insert(depositReg);
		
		// 在批量充值明细表中插入数据
		List<DepositBatReg> list = new ArrayList<DepositBatReg>();
		for (DepositBatReg batReg : batRegList) {
			batReg.setDepositBatchId(depositReg.getDepositBatchId());
			batReg.setUpdateTime(new Date());
			batReg.setUpdateUser(user.getUserId());
			batReg.setStatus(status);
			
			list.add(batReg);
		}
		this.depositBatRegDAO.insertBatch(list);
		
		// 在充值返利明细表中插入数据
		List<RebateCardReg> rebateList = new ArrayList<RebateCardReg>();
		for (RebateCardReg rebateCardReg : rebateCardList) {
			rebateCardReg.setRebateFrom(RebateFromType.DEPOSIT.getValue());
			rebateCardReg.setBatchId(depositReg.getDepositBatchId());
			
			rebateList.add(rebateCardReg);
		}
		this.rebateCardRegDAO.insertBatch(rebateList);
		
		return depositReg;
	}
	
	public boolean modifyDepositBatReg(DepositBatReg depositBatReg, String modifyUserId) throws BizException {
		Assert.notNull(depositBatReg, "更新的批量充值登记对象不能为空");		
		
		depositBatReg.setUpdateTime(new Date());
		depositBatReg.setUpdateUser(modifyUserId);
		// depositBatReg.setStatus(RegisterState.NORMAL.getValue());
		
		int count = this.depositBatRegDAO.update(depositBatReg);		
		
		return count > 0;
	}

	public boolean deleteDepositBatReg(long depositBatchId) throws BizException{
		Assert.notNull(depositBatchId, "删除的批量充值登记ID不能为空");
		
		int count = this.depositBatRegDAO.delete(depositBatchId);
		
		return count > 0;
	}
	
	// 激活批量预售卡
	public boolean activate(DepositReg depositReg, UserInfo user)throws BizException {
		String createUserId = user.getUserId();
		
		Assert.notNull(depositReg, "被激活的充值登记对象不能为空");
		Assert.isTrue(depositReg.getPreDeposit().equals(PreDepositType.PRE_DEPOSIT.getValue()), "被激活的充值对象必须是预充值");
		
		// 更改售卡表预售卡“未激活”状态为“待处理”状态
		depositReg.setStatus(RegisterState.WAITEDEAL.getValue());
		depositReg.setUpdateTime(new Date());
		depositReg.setUpdateUser(createUserId);
		this.depositRegDAO.update(depositReg); 
		
		// 更改批量售卡表预售卡“未激活”状态为“待处理”状态
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depositBatchId", depositReg.getDepositBatchId());  
		List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
		
		CardInfo cardInfo = null;
		for(int i = 0; i < depositBatRegList.size(); i++){
			DepositBatReg depositBatReg = depositBatRegList.get(i);
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(depositBatReg.getCardId());
			
			Assert.notNull(cardInfo, "卡号[" + depositBatReg.getCardId() + "]的信息不存在");
			Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + depositBatReg.getCardId() + "]的账户不存在");
			
			depositBatReg.setStatus(RegisterState.WAITEDEAL.getValue());
			depositBatReg.setUpdateTime(new Date());
			depositBatReg.setUpdateUser(createUserId);
			this.depositBatRegDAO.update(depositBatReg);
		}
		
		// 预售卡发报文
		//waitsinfoId = send(saleCardReg.getSaleCardBatRegId(), createUserId);
//		waitsinfoId = MsgSender.sendMsg(MsgType.PRE_DEPOSIT_ACTIVATE, depositReg.getDepositBatchId(), createUserId);
//		Assert.isTrue(waitsinfoId > 0, "发送报文失败！");
		MsgSender.sendMsg(MsgType.PRE_DEPOSIT_ACTIVATE, depositReg.getDepositBatchId(), createUserId);
		
		// 扣减风险保证金和操作员配额
		this.deductUserAmtAndCardRisk(depositReg, user);
		
		return true;
	}
	
	public DepositReg depositCancel(String depositBatchId, UserInfo user)
			throws BizException {
		Assert.notEmpty(depositBatchId, "要撤销的充值批次号不能为空");
		
		// 先取得原充值记录
		DepositReg oldReg = (DepositReg) depositRegDAO.findByPk(NumberUtils.toLong(depositBatchId));
		Assert.notNull(oldReg, "要撤销的充值已经记录不存在");

		if (RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())) {
			String deptId = user.getDeptId(); // 登录机构
			Assert.isTrue(deptId != null && deptId.equals(oldReg.getDepositBranch()),
					"用户角色为发卡机构网点（部门）角色，只能给充值机构 是 登录机构网点的充值记录进行撤销操作");
		} else {
			String loginedBranchCode = user.getBranchNo(); // 登录机构
			Assert.isTrue(loginedBranchCode != null
					&& (loginedBranchCode.equals(oldReg.getDepositBranch()) || loginedBranchCode
							.equals(oldReg.getCardBranch())), "用户角色为发卡机构角色，只能给充值机构或卡的发卡机构 是 登录机构的充值记录进行撤销操作");
		}
		
		// 将原纪录的状态改为待处理
		oldReg.setStatus(RegisterState.WAITEDEAL.getValue());
		depositRegDAO.update(oldReg);
		
		DepositReg newReg = new DepositReg();
		try {
			BeanUtils.copyProperties(newReg, oldReg);
		} catch (IllegalAccessException e) {
			throw new BizException("复制对象时发生IllegalAccessException异常");
		} catch (InvocationTargetException e) {
			throw new BizException("复制对象时发生InvocationTargetException异常");
		}
		
		newReg.setStatus(RegisterState.WAITED.getValue());
		newReg.setOldDepositBatch(oldReg.getDepositBatchId()); // 要撤销的充值批次
		newReg.setDepositCancel(DepositCancelFlag.CANCEL.getValue()); // 撤销标志为充值撤销
		newReg.setCancelFlag(Symbol.YES);
		newReg.setUpdateTime(new Date());
		newReg.setUpdateUser(user.getUserId());
		//newReg.setEntryUserId(user.getUserId());
		// 取得系统工作日，作为充值撤销时间
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		newReg.setDepositDate(workDate);
		
		// 在充值登记簿里插入一条充值撤销的记录
		depositRegDAO.insert(newReg);
		
		// 启动流程
		try {
			this.workflowService.startFlow(newReg, "depositCancelAdapter", Long.toString(newReg.getDepositBatchId()), user);
		} catch (Exception e) {
			throw new BizException("启动流程时发生异常");
		}
		return newReg;
	}
	
	public void depositRevoke(String depositBatchId, UserInfo user)
			throws BizException {
		Assert.notEmpty(depositBatchId , "要取消的充值批次号不能为空");
		DepositReg depositReg = (DepositReg) depositRegDAO.findByPk(NumberUtils.toLong(depositBatchId));
		Assert.isTrue(StringUtils.equals(RegisterState.INACTIVE.getValue(), depositReg.getStatus()), 
				"该批次的充值记录的状态不是“未激活”状态");

		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		
		String depositBranch = ""; // 充值机构
		// 登录角色为售卡代理或发卡机构时
		if (StringUtils.equals(roleType, RoleType.CARD.getValue())
				|| StringUtils.equals(roleType, RoleType.CARD_SELL.getValue())) {
			depositBranch = user.getBranchNo();
		}
		// 登录角色为发卡机构部门时
		else if (StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {
			depositBranch = user.getDeptId();
		}
		Assert.isTrue(StringUtils.equals(depositBranch, depositReg.getDepositBranch()), "只能给自己的充值记录进行取消操作");
		
		depositReg.setStatus(RegisterState.REVOKED.getValue()); // 已取消
		depositReg.setUpdateTime(new Date());
		depositReg.setUpdateUser(user.getUserId());
		if (StringUtils.isEmpty(depositReg.getCardId())) { //批量充值还要改明细表
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", depositBatchId);
			List<DepositBatReg> list = depositBatRegDAO.findDepositBatReg(params);
			for (DepositBatReg batReg : list) {
				batReg.setStatus(RegisterState.REVOKED.getValue());
				batReg.setUpdateTime(depositReg.getUpdateTime());
				batReg.setUpdateUser(depositReg.getUpdateUser());
				
				depositBatRegDAO.update(batReg);
			}
		}
		
		depositRegDAO.update(depositReg);
	}

	public DepositReg addDepositBatRegFile(File file,boolean isOldFileFmt, 
			DepositReg depositReg, long totalNum, UserInfo user, 
			String limitId, String serialNo) throws BizException {
		Assert.notNull(depositReg, "添加的批量充值对象不能为空");
		Assert.notNull(user, "登录用户的信息对象不能为空");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		// 解析充值文件，存入到一个list中
		List<DepositBatReg> list = resolveFile(file, isOldFileFmt);
		
		// 检测 批量充值明细，并在充值登记簿中插入记录
		depositReg = addDepositRegResult(depositReg, user, list, totalNum, file);
		
		Assert.isTrue(AmountUtil.gt(depositReg.getAmt(), BigDecimal.ZERO), "充值金额必须大于0");
		
		// 将list中的内容插入到在批量充值明细中插入记录
		addDepositDetail(list, depositReg, user, limitId);
		
		// 判断是否需要审核.如果是手工指定返利，同时充值机构不为发卡机构的时候就需要审核
//		boolean toCheck = depositReg.isManual() && !StringUtils.equals(RoleType.CARD.getValue(), user.getRole().getRoleType());
		boolean isNeedCheck = this.isCheckForDepositBat(depositReg, user);
		
		// 不需要审核，同时不是预充值时，扣减风险保证金，发送充值报文 
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositReg, WorkflowConstants.DEPOSIT_BATCH_ADAPTER, 
						Long.toString(depositReg.getDepositBatchId()), user);
			} catch (Exception e) {
				String errMsg = "流程启动失败:" + e.getMessage();
				logger.error(errMsg, e);
				throw new BizException(errMsg);
			}
		} else {
			if (StringUtils.equals(PreDepositType.REAL_DEPOSIT.getValue(), depositReg.getPreDeposit())) {
				// 批量应统一发报文
				MsgSender.sendMsg(MsgType.DEPOSIT, depositReg.getDepositBatchId(), user.getUserId());

				//扣减操作员配额及风险保证金
				deductUserAmtAndCardRisk(depositReg, user);
			}
		}
		
		return depositReg;
	}
	
	/**
	 * 在充值登记簿里插入记录 
	 * @param depositReg 充值登记簿
	 * @param user 登录用户信息
	 * @param list 充值明细
	 * @param totalNum 充值总笔数
	 * @return
	 * @throws BizException
	 */
	private DepositReg addDepositRegResult(DepositReg depositReg, UserInfo user,
			List<DepositBatReg> list, long totalNum, File file) throws BizException {
		Assert.isTrue(totalNum == list.size(), "充值文件中的充值笔数与页面录入的充值笔数不一致。");
		Assert.isTrue(checkCardId(list), "充值文件中的卡号不属于同一发卡机构或卡类型不一致");
		
		// 文件中第1张卡
		CardInfo firstCard = (CardInfo) cardInfoDAO.findByPk(list.get(0).getCardId());
		for (int i = 0; i < list.size(); i++) {
			String cardId = list.get(i).getCardId();
			CardInfo card = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(card, String.format("第%s张卡[%s]的信息不存在", i + 1, cardId));

			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(card.getAcctId());
			Assert.notNull(acctInfo, String.format("第%s张卡[%s]的账户不存在", i + 1, cardId));

			CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(card
					.getCardSubclass());
			Assert.notNull(cardSubClass, String.format("第%s张卡[%s]所属卡类型不存在", i + 1, cardId));

			// 该卡类型的卡能否充值
			Assert.equals(cardSubClass.getDepositFlag(), Symbol.YES, String.format("第%s张卡[%s]所属的卡类型不能做充值",
					i + 1, cardId));

			Assert.equals(card.getAppOrgId(), firstCard.getAppOrgId(), String.format(
					"第%s张卡[%s]的领卡机构[%s] 与 第1张卡的领卡机构[%s] 不同，不能在同一文件批量充值！", i + 1, cardId, card.getAppOrgId(),
					firstCard.getAppOrgId()));
		}
		
		if(BranchBizConfigCache.isSetDepositBranchByAppOrgId(firstCard.getCardIssuer())){
			depositReg.setDepositBranch(firstCard.getAppOrgId());
		}else{
			if(RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())){//发卡机构网点登录
				depositReg.setDepositBranch(user.getDeptId());
			}else{
				depositReg.setDepositBranch(user.getBranchNo());
			}
		}
		depositReg.setCardBranch(firstCard.getCardIssuer());
		depositReg.setUpdateTime(new Date());
		depositReg.setUpdateUser(user.getUserId());
		depositReg.setEntryUserId(user.getUserId());
		depositReg.setFileDeposit(Symbol.YES);// 以文件方式批量充值
		depositReg.setCancelFlag(Symbol.NO);// 是否已经撤销
		depositReg.setDepositCancel(DepositCancelFlag.NORMAL.getValue());// 正常充值
		// 取得系统工作日，作为售卡时间
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositReg.setDepositDate(workDate);
		
		// 如果是次卡按次充值的话，购卡客户为该卡的通用购卡客户，返利金额为0，返利规则为通用返利规则
		if (StringUtils.equals(depositReg.getCardClass(), CardType.ACCU.getValue())
				&& StringUtils.equals(depositReg.getDepositType(), DepositType.TIMES.getValue())) {
			depositReg.setRebateAmt(new BigDecimal(0.0));
			
			// 取得充值卡的所属发卡机构的通用购卡客户
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cardBranch", firstCard.getCardIssuer());
			map.put("isCommon", Symbol.YES);
			CardCustomer customer = cardCustomerDAO.findCardCustomer(map).get(0);
			depositReg.setCardCustomerId(customer.getCardCustomerId());
			
			RebateRule rebateRule = rebateRuleDAO.findRebateRule(map).get(0);
			depositReg.setRebateId(rebateRule.getRebateId());
			depositReg.setRebateType(RebateType.CASH.getValue());
		}
		
		// 预充值
		boolean preDeposit = false;
		if (PreDepositType.PRE_DEPOSIT.getValue().equals(depositReg.getPreDeposit())){
			preDeposit = true;
		}
		
		// 如果是手工指定返利，同时充值机构不为发卡机构的时候就需要审核
//		boolean toCheck = depositReg.isManual() && !RoleType.CARD.equals(user.getRole().getRoleType());
		boolean isNeedCheck = this.isCheckForDepositBat(depositReg, user);
		if (isNeedCheck) {
			depositReg.setStatus(RegisterState.WAITED.getValue());
		} else {
			if (preDeposit) {
				depositReg.setStatus(RegisterState.INACTIVE.getValue());
			} else {
				depositReg.setStatus(RegisterState.WAITEDEAL.getValue());
			}
		}
		
		// 如果是手工指定返利且非发卡机构登记则需要发卡机构审核
		depositRegDAO.insert(depositReg);
		
		return depositReg;
	}
	
	/**
	 * 解析充值文件，得到充值明细对象
	 * @param file 充值文件
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unchecked")
	private List<DepositBatReg> resolveFile(File file, boolean isOldFileFmt) throws BizException {
		List<String> lines = null;
		try {
			lines = IOUtil.readLines(file);
		} catch (IOException e) {
			logger.error(e, e);
			throw new BizException("解析文件失败!");
		}
		Assert.isTrue(CollectionUtils.isNotEmpty(lines) && lines.size() > 1, "文件无内容或格式错误");
		
		return resolveDepositDetail(lines, isOldFileFmt);
	}
	
	/**
	 * 解析充值明细
	 * @return
	 */
	private List<DepositBatReg> resolveDepositDetail(List<String> lines, boolean isOldFileFmt) throws BizException {
		List<DepositBatReg> list = new ArrayList<DepositBatReg>();
		
		int fieldNum = 2;//表头字段数目，用于验证明细字段数
		
		// 记录卡号, 用作判断是否重复.
		Set<String> cardNoSet = new HashSet<String>();
		// 解析充值明细， 从第二行开始解析
		for (int i = 1, n = lines.size(); i < n; i++) {
			String line = lines.get(i); 
			// 空行略过.
			if (StringUtils.isBlank(line)) {
				continue;
			}
			String depositLine = isOldFileFmt ? line.trim().substring(line.indexOf(";")+1) : line;
			String[] fields = depositLine.split(isOldFileFmt ? ";" : DEFAULT_SEQ, -1);
			// 检查卡号
			checkCardNo(fields, i, fieldNum, cardNoSet);
			// 取得充值明细对象
			DepositBatReg depositBatReg = getBatRegFromDetail(fields);
			
			list.add(depositBatReg);
		}
		return list;
	}
	
	/**
	 * 对充值明细里的卡号，进行检查
	 * @param fields 单条记录的数组
	 * @param count 记录的条数的序号
	 * @param fieldNum 模板中的明细个数
	 * @param cardNoSet 充值明细里的卡号
	 * @throws BizException
	 */
	private void checkCardNo(String[] fields, int count, int fieldNum, 
			Set<String> cardNoSet) throws BizException {
		// 充值明细元素个数要和模板配置明细个数一致.
		if (fields.length != fieldNum) {
			String msg = "明细第" + count + "行格式错误,元素不为" + fieldNum + "个";
			logger.error(msg);
			throw new BizException(msg);
		}
		
		if (ArrayUtils.isEmpty(fields)) {
			throw new BizException("解析出的数组为空");
		}
		
		//取得卡号,为空表示有错，抛出异常，跳出循环
		String cardNo = fields[0];
		if (StringUtils.isEmpty(cardNo)) {
			throw new BizException("充值明细第" + count + " 行格式错误，卡号不能为空。");
		}
		
		//卡号不能重复
		if (cardNoSet.contains(cardNo)) {
			throw new BizException("卡号[" + cardNo + "]的记录重复");
		}
		cardNoSet.add(cardNo);
	}
	
	/**
	 *  判断要充值的卡号是否属于同一发卡机构
	 * @param list
	 * @return
	 */
	private boolean checkCardId(List<DepositBatReg> list) throws BizException{
		String cardSubClass = null;
		for (DepositBatReg reg : list) {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(reg.getCardId());
			Assert.notNull(cardInfo, "要充值的卡号[" + reg.getCardId() + "]不存在");
			
			if(null == cardSubClass){
				cardSubClass = cardInfo.getCardSubclass();
			}else{
				if(!cardSubClass.equals(cardInfo.getCardSubclass())){//卡子类型不一致
					return false;
				}
			}
		}
		return true;
		
//		String prefix = StringUtils.substring(list.get(0).getCardId(), 0, 11);
//		for (int i = 1; i < list.size(); i++) {
//			CardInfo cardInfo = new CardInfo();
//			
//			cardInfo.setCardId(list.get(i).getCardId());
//			if (!StringUtils.equals(prefix, cardInfo.getCardNoPrix())) {
//				return false;
//			}
//		}
//		return true;
	}
	
	/**
	 * 添加充值明细到批量充值明细表
	 * @param list
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private void addDepositDetail(List<DepositBatReg> list, DepositReg depositReg, 
			UserInfo user, String limitId) throws BizException {
	
		CardInfo rebateCardInfo = null;
		// 如果是返利指定卡状态，则需要验证卡的状态
		if (RebateType.CARD.getValue().equals(depositReg.getRebateType())) {
			rebateCardInfo = checkRebateCard(depositReg.getRebateCard());
		}
		
		BigDecimal totalAmt = new BigDecimal(0.0);
		// 在明细表里插入记录
		for (DepositBatReg reg : list) {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(reg.getCardId());
			Assert.notNull(cardInfo, "要充值的卡号[" + reg.getCardId() + "]不存在");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "要充值的卡号[" + depositReg.getCardId() + "]的账户不存在");
			
			CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClass, "要充值的卡号[" + depositReg.getCardId() + "]所属卡类型不存在");

			// 该卡类型的卡能否充值
			Assert.equals(cardSubClass.getDepositFlag(), Symbol.YES, "卡号[" + depositReg.getCardId() + "]所属的卡类型不能做充值");
			
			// 检查充值卡状态，及返利卡与充值卡是否属于同一发卡机构
			checkRebateAndDeposit(rebateCardInfo, cardInfo);
			
			Assert.isTrue(StringUtils.equals(cardInfo.getCardClass(), depositReg.getCardClass()), "页面录入充值的卡类型与充值明细文件的卡种类不一致");
			
			// 判断是否有给该卡充值的权限
			if (RoleType.CARD_SELL.getValue().equals(user.getRole().getRoleType())) {
				Assert.isTrue(hasCardSellPrivilegeByCardId(user.getRole().getBranchNo(), reg.getCardId(), limitId), 
						"该售卡代理没有权限为该卡[" + reg.getCardId() + "]充值");
			}
			
			// 如果没有权限充值则报错
			if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
				throw new BizException("没有给该卡["+ reg.getCardId() +"]充值的权限");
			}
			
			totalAmt = AmountUtil.add(totalAmt, reg.getAmt());
			
			//明细的实收金额 = 充值金额*（总的实收金额/充值总金额）
			BigDecimal realAmt = AmountUtil.multiply(reg.getAmt(), AmountUtil.divide(depositReg.getRealAmt(), depositReg.getAmt()));
			
			//明细的返利金额 = 充值金额*（总的返利金额/充值总金额）
			BigDecimal rebateAmt = AmountUtil.multiply(reg.getAmt(), AmountUtil.divide(depositReg.getRebateAmt(), depositReg.getAmt()));

			// 如果是次卡按次充值，需验证该卡有无次卡子账户
			if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("acctId", cardInfo.getAcctId());
				params.put("subacctTypes", new String[]{SubacctType.ACCU.getValue()});
				List<SubAcctBal> subaccts = this.subAcctBalDAO.findSubAcctBal(params);
				Assert.notTrue(CollectionUtils.isEmpty(subaccts), "该卡["+ reg.getCardId() +"]没有次卡子账户，不能进行次数充值");
				
//				CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
//				Assert.notNull(subClass, "该卡所属卡子类型不存在");
//				Assert.notEmpty(cardSubClass.getFrequencyClass(), "该卡所属卡子类型没有定义次卡子类型");
				
				AccuClassDef accuClass = (AccuClassDef) this.accuClassDefDAO.findByPk(cardSubClass.getFrequencyClass());
				Assert.notNull(accuClass, "该卡所属卡子类型没有定义次卡子类型");

				// 实收金额应该等于清算金额*充值次数，不一致则抛出异常
				if (accuClass.getSettAmt() != null && accuClass.getSettAmt().doubleValue() != 0.0) {
					Assert.isTrue(AmountUtil.et(realAmt, AmountUtil.multiply(accuClass.getSettAmt(), reg.getAmt())), 
							"实收金额与次卡的清算金额不一致");
				}
			}
			
			reg.setDepositBatchId(depositReg.getDepositBatchId());
			reg.setCardClass(depositReg.getCardClass());
			reg.setUpdateTime(depositReg.getUpdateTime());
			reg.setUpdateUser(depositReg.getUpdateUser());
			reg.setStatus(depositReg.getStatus());
			reg.setEntryUserId(depositReg.getEntryUserId());
			reg.setRandomSessionId(depositReg.getRandomSessionId());
			reg.setSignature(depositReg.getSignature());
			
			//明细的实收金额 = 充值金额*（总的实收金额/充值总金额）
			reg.setRealAmt(realAmt);

			//明细的返利金额 = 充值金额*（总的返利金额/充值总金额）
			reg.setRebateAmt(rebateAmt);
			depositBatRegDAO.insert(reg);
		}
		
		String msg = "";
		// 按次充值的话
		if (StringUtils.equals(depositReg.getDepositType(), DepositType.TIMES.getValue())) {
			msg = "文件中的充值总次数与页面录入的总次数不一致";
		} else {
			msg = "文件中的充值总金额与页面录入的总金额不一致";
		}
		
		Assert.isTrue(AmountUtil.et(totalAmt, depositReg.getAmt()), msg);
	}
	
	/**
	 * 检查返利卡号
	 * @param rebateCard 返利卡号
	 * @param depositCard 充值的卡号
	 * @throws BizException
	 */
	private CardInfo checkRebateCard(String rebateCard) throws BizException {
		Assert.notEmpty(rebateCard, "返利指定卡指定的返利卡号不能为空");
		
		CardInfo rebateCardInfo = (CardInfo)this.cardInfoDAO.findByPk(rebateCard);
		Assert.notNull(rebateCardInfo, "指定的返利卡卡号[" + rebateCard + "]不存在");
		Assert.notEmpty(rebateCardInfo.getAcctId(), "指定的返利卡卡号[" + rebateCard + "]的账户不存在");
		
		Assert.isTrue(CardState.ACTIVE.getValue().equals(rebateCardInfo.getCardStatus()), "返利卡[" + rebateCard + "]未激活");
		
		return rebateCardInfo;
	}
	
	/**
	 * 检查返利卡号与充值卡号是否一致，充值卡号的状态是否有效
	 * @param rebateCardInfo
	 * @param depositCardInfo
	 * @throws BizException
	 */
	private void checkRebateAndDeposit(CardInfo rebate, CardInfo deposit) throws BizException{
		Assert.equals(CardState.ACTIVE.getValue(), deposit.getCardStatus(), "要充值的卡号[" + deposit.getCardId() + "]的状态不是正常状态");
		
		if (rebate != null) {
			Assert.equals(rebate.getCardIssuer(), deposit.getCardIssuer(), "所充值卡与返利指定卡不属于同一发卡机构");
		}
	}
	
	/**
	 * 从明细中取得充值明细对象
	 * @param arr
	 * @return
	 */
	private DepositBatReg getBatRegFromDetail(String[] arr) throws BizException {
		DepositBatReg depositBatReg = new DepositBatReg();
		Assert.isTrue(arr.length == 2, "明细数组的长度有误");
		depositBatReg.setCardId(arr[0]);
		
		BigDecimal amt = null;
		try {
			amt = NumberUtils.createBigDecimal(arr[1]);
		} catch (NumberFormatException e) {
			String msg = "充值明细文件里的金额值格式非法";
			logger.error(msg, e);
			throw new BizException(msg);
		}
		depositBatReg.setAmt(amt);
		
		return depositBatReg;
	}
	
	/**
	 * 扣减风险保证金和操作员额度
	 * @param depositReg
	 * @param user
	 * @throws BizException
	 */
	private void deductUserAmtAndCardRisk(DepositReg depositReg, UserInfo user) throws BizException {
//		BigDecimal total = depositReg.getRealAmt();// 操作员充值实收金额，扣操作员的扣实收的 // 现在扣的是清算金额
		BigDecimal totalRisk = new BigDecimal(0.0); // 充值金额 + 返利金额 // 即 清算金额
		
		// 如果是次卡按次充值的话，是没有返利的。实收金额应该与应收金额相同
		if (StringUtils.equals(depositReg.getDepositType(), DepositType.TIMES.getValue())) {
			totalRisk = depositReg.getRealAmt();
		} else {
			totalRisk = AmountUtil.add(depositReg.getAmt(), depositReg.getRebateAmt());
		}
		
		// 充值售卡额度
		if (!RoleType.CARD.getValue().equals(user.getRole().getRoleType())) { // 非发卡机构角色
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositReg.getDepositBatchId());	// 充值登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
//			branchSellReg.setAmt(total);							// 实收金额
			branchSellReg.setAmt(totalRisk);						// 清算金额
			branchSellReg.setCardBranch(depositReg.getCardBranch());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositReg.getDepositBranch());	// 充值机构
			if (RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())) {// 发卡机构网点角色登录
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(user.getUserId(), depositReg.getDepositBranch(), totalRisk);
		
		//扣减风险准备金
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(totalRisk);						// 充值金额 + 返利金额
		cardRiskReg.setBranchCode(depositReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	protected boolean hasCardSellPrivilegeByCardId(String proxyId, String cardId, String limitId){
		CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		return hasCardSellPrivilege(proxyId, cardInfo.getCardIssuer(), limitId);
	}
	
	protected boolean hasCardSellPrivilege(String proxyId, String cardBranch, String limitId) {
		// 判断是否属于该发卡机构的售卡代理
		BranchProxyDAO branchProxyDAO = (BranchProxyDAO) SpringContext.getService("branchProxyDAOImpl");
		BranchProxyKey branchProxyKey = new BranchProxyKey(proxyId, cardBranch, ProxyType.SELL.getValue());
		if (!branchProxyDAO.isExist(branchProxyKey)) {
			return false;
		}
		
		// 判断是否拥有该权限
		SaleProxyPrivilegeDAO saleProxyPrivilegeDAO = (SaleProxyPrivilegeDAO) SpringContext.getService("saleProxyPrivilegeDAOImpl");
		List<HashMap> privlegeList = saleProxyPrivilegeDAO.findSaleProxy(proxyId, cardBranch, limitId);
		if (CollectionUtils.isNotEmpty(privlegeList)) {
			return true;
		}
		return false;
	}
	
}
