package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.SaleSignCardReg;
import gnete.card.entity.SignCust;
import gnete.card.entity.SignRuleReg;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.flag.DepositCancelFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.DepositFromPageType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PreDepositType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.DepositService;
import gnete.card.service.RebateRuleService;
import gnete.card.service.mgr.BranchBizConfigCache;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.tag.NameTag;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * 充值登记簿Action
 * DepositRegAction.java.
 * 
 * @author BenYan
 * @since JDK1.5
 * @history 2010-8-24
 */
public class DepositRegAction extends BaseAction {
	
	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private DepositService depositService;
	@Autowired
	private RebateRuleService rebateRuleService;
	
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;    
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	
	// 充值登记簿对象
	private DepositReg depositReg;
	private SaleCardReg saleCardReg;
	
	// 购卡客户、返利规则
	private CardCustomer cardCustomer;    
	private String rebateType;
	private RebateRule rebateRule;
	private CustomerRebateReg customerRebateReg;
	private List<CustomerRebateReg> customerRebateRegList;
	private List<RebateRuleDetail> rebateRuleDetailList;
	private SignCust signCust;
	
	// 持卡人资料
	private CardExtraInfo cardExtraInfo;

	// 卡信息
    private CardInfo cardInfo;
	private SubAcctBal subAcctBal;
	
    // 卡类型
    private CardTypeCode cardTypeCode;
    private CardBin cardBin;
    private List<CardTypeCode> cardClassList;
    
    // 卡子类型
    private CardSubClass cardSubClass;
    //次卡子类型定义
    private List<AccuClassDef> accuClassList;
    //积分子类型定义
    private List<PointClassDef> pointClassList;
    //会员子类型定义
    private List<MembClassDef> membClassList;
    //折扣子类型定义
    private List<DiscntClassDef> discntClassList;    
    
    // 页面Action子名
    private String actionSubName;
    
    private Paginater page;
    
    private Collection statusList;	
	
    private String resultAcctId = "";
	private String resultCardClass = "";
	private String resultCardClassName = "";
	private String resultCardSubClass = "";
	private String resultCardSubClassName = "";
	private String resultCardCustomerId = "";
	private String resultCardCustomerName = "";
	private String resultRebateType = "";
	private String resultRebateTypeName = "";
	private String resultGroupCardID = "";
	private String resultRebateId = "";
	private String resultRebateName = "";
	private String resultCalType = "";
	private String resultCalTypeName = "";
	private String resultCustName = "";
	private String resultCredTypeName = "";
	private String resultCredNo = "";
	private String resultAddress = "";
	private String resultTelNo = "";
	private String resultMobileNo = "";
	private String resultEmail = "";
	private String resultAvblBal = "";	
	private String resultBranchName = "";
	private String resultMessage = "";
	private String resultCardBin = "";
	private String resultEndCardId = "";
	
	private String resultDepositTypeIsTimes = "";
	
	private SignRuleReg signRuleReg;
	private SaleSignCardReg saleSignCardReg;
	private List<SaleSignCardReg> saleSignCardRegList;
	
	private List rebateTypeList;
	
	private List<RebateRule> rebateRuleList;
	
	private String cardId;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;
	
	/** 是否显示发卡机构列表 */
    private boolean showCardBranch = false;
    /** 发卡机构列表 */
    private List<BranchInfo> cardBranchList;
    /** 发卡机构名称 */
    private String cardBranchName;
    /** 开始日期 */
    private String startDate; 
    /** 结束日期 */
    private String endDate;
	
    // 服务端检索卡相关信息，返回给客户端
    public void searchCardInfo(){
    	if (depositReg == null) {return;}
//    	// 检索CardInfo
//    	String binNo = CardBin.getBinNo(this.depositReg.getCardId());
//    	if(binNo.equals(CardBin.ERROR)){
//    		resultMessage = "卡号[" + depositReg.getCardId() + "]不存在，请重新录入！";
//    		respondReturn();
//    		return;
//    	}
    	this.cardInfo = (CardInfo)this.cardInfoDAO.findByPk(this.depositReg.getCardId());
    	if(this.cardInfo == null){
    		resultMessage = "卡号[" + depositReg.getCardId() + "]不存在，请重新录入卡号！";
    		respondReturn();
    		return;
    	}
    	
    	this.resultAcctId = this.cardInfo.getAcctId(); 
    	if (StringUtils.isEmpty(resultAcctId)) {
    		resultMessage = "卡号[" + depositReg.getCardId() + "]的账户不存在，不能做充值！";
    		respondReturn();
    		return;
		}
    	
    	// 如果是旧卡
		if (this.depositReg.getCardId().length() == 18) {
			// 如果第4位到7位不是0000的话则不能充值
			if (!"0000".equals(this.depositReg.getCardId().substring(3, 7))) {
				this.resultMessage = "旧卡[" + this.depositReg.getCardId() + "]不能充值！";
				respondReturn();
				return;
			}
		}
    	
    	this.resultCardBin = this.cardInfo.getCardBin();
    	
//    	String cardType = request.getParameter("cardType");
//    	if (StringUtils.isNotBlank(cardType)
//    			&& (!StringUtils.equals(cardType, cardInfo.getCardClass()))) {
//    		resultMessage = "卡种类不符！";
//    		respondReturn();
//    		return;
//    	}
    	if(!CardState.ACTIVE.getValue().equals(this.cardInfo.getCardStatus())){
    		resultMessage = "此卡状态不为“" + CardState.ACTIVE.getName() + "”，只有“" + 
    							CardState.ACTIVE.getName() + "”状态的卡才能充值，请重新录入！";
    		respondReturn();
    		return;
    	}
    	
    	resultBranchName = NameTag.getBranchName(StringUtils.trim(cardInfo.getCardIssuer()));
    	
    	// 检索充值子账户余额
    	this.subAcctBal = new SubAcctBal();
    	this.subAcctBal.setAcctId(resultAcctId);
    	
    	// 查找拥有多少子账户
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("acctId", resultAcctId);
    	List<SubAcctBal> subAccts = this.subAcctBalDAO.findSubAcctBal(param);
    	boolean hasAccu = false;
    	for (SubAcctBal acctBal : subAccts){
    		if (SubacctType.ACCU.getValue().equals(acctBal.getSubacctType())){
    			hasAccu = true;
    			break;
    		}
    	}
    	if(hasAccu){
    		resultDepositTypeIsTimes = "true";
    	}else{
    		resultDepositTypeIsTimes = "false";
    	}
    	
    	// 查找默认账户，次卡默认查次卡子账户，其他卡默认查储值
    	if (hasAccu) {
    		this.subAcctBal.setSubacctType(SubacctType.ACCU.getValue());
    	} else {
    		this.subAcctBal.setSubacctType(SubacctType.DEPOSIT.getValue());
    	}
    	this.subAcctBal = (SubAcctBal)this.subAcctBalDAO.findByPk(this.subAcctBal);
    	if (this.subAcctBal == null){
    		this.resultAvblBal = "0.00";
    	} else {
    		this.resultAvblBal = this.subAcctBal.getAvlbBal().toString();
    	}
    	
    	// 检索卡类型（卡种）
    	this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.cardInfo.getCardClass());
    	if(this.cardTypeCode == null){
    		resultMessage = "查询卡类型错误，请重新录入卡号！";
    		respondReturn();
    		return;
    	}
    	resultCardClass = this.cardTypeCode.getCardTypeCode();
    	resultCardClassName = this.cardTypeCode.getCardTypeName();
    	
    	// 检索卡子类型
    	this.cardSubClass = new CardSubClass();
    	if   (resultCardClass.equals(CardType.DEPOSIT.getValue())) {
			this.setActionSubName("Deposit");
			this.cardSubClass.setCardSubClassId("");
			this.cardSubClass.setCardSubClassName("");
		}else if(resultCardClass.equals(CardType.MEMB.getValue())) {
			this.setActionSubName("Memb");			
		}else if(resultCardClass.equals(CardType.DISCNT.getValue())){
			this.setActionSubName("Discnt");
		}else if(resultCardClass.equals(CardType.ACCU.getValue())){
			this.setActionSubName("Accu");
		}else if(resultCardClass.equals(CardType.POINT.getValue())){
			this.setActionSubName("Point");
		}		
    	CardSubClassDef cardSubClassDef = (CardSubClassDef)this.cardSubClassDefDAO.findByPk(this.cardInfo.getCardSubclass());
//    	if(cardSubClassDef!=null){
//    		this.cardSubClass.setCardSubClassId(cardSubClassDef.getCardSubclass());
//			this.cardSubClass.setCardSubClassName(cardSubClassDef.getCardSubclassName());	
//    		resultCardSubClass = this.cardSubClass.getCardSubClassId();
//    		resultCardSubClassName = this.cardSubClass.getCardSubClassName();
//    	}
    	if (cardSubClassDef == null) {
    		this.resultMessage = "卡号[" + this.depositReg.getCardId() + "]所属卡类型不存在，不能充值！";
    		respondReturn();
    		return;
		}
    	
    	this.cardSubClass.setCardSubClassId(cardSubClassDef.getCardSubclass());
		this.cardSubClass.setCardSubClassName(cardSubClassDef.getCardSubclassName());	
		resultCardSubClass = this.cardSubClass.getCardSubClassId();
		resultCardSubClassName = this.cardSubClass.getCardSubClassName();
    	
    	if (StringUtils.equals(Symbol.NO, cardSubClassDef.getDepositFlag())) {
    		this.resultMessage = "卡号[" + this.depositReg.getCardId() + "]所属卡类型设置的是“不能充值”";
    		respondReturn();
    		return;
		}
    	
    	// 如果卡数量不为空的话，则是批量充值
    	String cardCount = request.getParameter("cardCount");
    	if (StringUtils.isNotBlank(cardCount)) {
			if (!NumberUtils.isDigits(cardCount)){
				this.resultMessage = "卡连续张数必须为正整数";
				respondReturn();
				return;
			} else {
				resultEndCardId = CardUtil.getEndCard(depositReg.getCardId(), NumberUtils.toInt(cardCount));
			}
		}
    	
    	// 检索购卡客户
    	//this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.saleCardReg.getCardCustomerId());//暂从SaleCardReg取
    	this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.cardInfo.getCardCustomerId());
    	if(this.cardCustomer==null||this.cardCustomer.getCardCustomerId()==null) {
    		Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardBranch", cardInfo.getCardIssuer());
			params.put("isCommon", Symbol.YES);

			List<CardCustomer> commonCustomers = this.cardCustomerDAO.findCardCustomer(params);
			if (CollectionUtils.isNotEmpty(commonCustomers)) {
				this.cardCustomer = commonCustomers.get(0);
			} else {
				resultMessage = "查询购卡客户失败，请重新录入卡号！";
	    		respondReturn();
	    		return;
			}
    	}
    	
    	resultCardCustomerId = this.cardCustomer.getCardCustomerId().toString();
    	resultCardCustomerName = this.cardCustomer.getCardCustomerName();
    	resultGroupCardID = this.cardCustomer.getGroupCardId();
    	resultRebateType = this.cardCustomer.getRebateType();
    	resultRebateTypeName = this.cardCustomer.getRebateTypeName();
		    	
    	// 检索持卡人
		this.cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(this.depositReg.getCardId());
		if(this.cardExtraInfo == null) {
			respondReturn();
			return;
		}
		resultCustName = this.cardExtraInfo.getCustName();
    	resultCredTypeName = this.cardExtraInfo.getCredTypeName();
    	resultCredNo = this.cardExtraInfo.getCredNo();
    	resultAddress = this.cardExtraInfo.getAddress();
    	resultTelNo = this.cardExtraInfo.getTelNo();
    	resultMobileNo = this.cardExtraInfo.getMobileNo();
    	resultEmail = this.cardExtraInfo.getEmail();
    	
    	respondReturn();
    }
    
    public void searchForAccu(){
    	String cardId = request.getParameter("cardId");
    	String type = request.getParameter("type");
    	
    	this.cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);

    	this.resultAcctId = this.cardInfo.getAcctId(); 
    	// 检索子账户余额
    	this.subAcctBal = new SubAcctBal();
    	this.subAcctBal.setAcctId(resultAcctId);
    	
    	if ("0".equals(type)) {//次卡子账户
    		this.subAcctBal.setSubacctType(SubacctType.ACCU.getValue());
    	} else if("1".equals(type)) {//充值资金子账户
    		this.subAcctBal.setSubacctType(SubacctType.DEPOSIT.getValue());
    	} else if("2".equals(type)) {//返利资金子账户
    		this.subAcctBal.setSubacctType(SubacctType.REBATE.getValue());
    	}
    	
    	this.subAcctBal = (SubAcctBal)this.subAcctBalDAO.findByPk(this.subAcctBal);
    	if(this.subAcctBal == null) {
    		this.resultAvblBal = "0.00";
    	} else {
    		this.resultAvblBal = this.subAcctBal.getAvlbBal().toString();
    	}
    	
    	this.respond("{'resultAvblBal':'"+ resultAvblBal +"'}");
    }
    
    public String findRebateRule() throws Exception {
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.cardId);
		
		if (cardInfo == null) {
			this.setMessage("卡号["+ this.cardId +"]不存在");
			return "rebateRule";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		this.rebateRuleList = new ArrayList<RebateRule>();
		
		// 查找通用返利规则
		params.put("isCommon", Symbol.YES);
		params.put("cardBranch", cardInfo.getCardIssuer().trim());

		String[] statusArray = new String[]{RebateRuleState.NORMAL.getValue(), RebateRuleState.USED.getValue()};
		params.put("statusArray", statusArray);
		rebateRuleList.addAll(this.rebateRuleDAO.findRebateRule(params));
		
		// 查找该购卡客户的返利规则
		params.put("isCommon", Symbol.NO);
		if(!this.isFormMapFieldTrue("ablePeriod")){
			params.put("rebateType", "0"); // 一次性返利
		}
		params.put("cardBranch", cardInfo.getCardIssuer());
		params.put("statusArray", statusArray);
		params.put("cardBin", cardInfo.getCardBin());
		params.put("depositCardCustomerId", cardCustomer.getCardCustomerId());
		rebateRuleList.addAll(this.rebateRuleDAO.findRebateRule(params));
		
		if (CollectionUtils.isEmpty(rebateRuleList)) {
			this.setMessage("没有合适的返利规则！");
		}
		return "rebateRule";
	}
    
    private void respondReturn(){
    	JSONObject object = new JSONObject();
    	
    	object.put("resultAcctId", getString(resultAcctId));
    	object.put("resultCardClass", getString(resultCardClass));
    	object.put("resultCardClassName", getString(resultCardClassName));
    	object.put("resultCardSubClass", getString(resultCardSubClass));
    	object.put("resultCardSubClassName", getString(resultCardSubClassName));
    	object.put("resultCardCustomerId", getString(resultCardCustomerId));
    	object.put("resultCardCustomerName", getString(resultCardCustomerName));
    	object.put("resultRebateType", getString(resultRebateType));
    	object.put("resultRebateTypeName", getString(resultRebateTypeName));
    	object.put("resultGroupCardID", getString(resultGroupCardID));
    	object.put("resultRebateId", getString(resultRebateId));
    	object.put("resultRebateName", getString(resultRebateName));
    	object.put("resultCalType", getString(resultCalType));
    	object.put("resultCalTypeName", getString(resultCalTypeName));
    	object.put("resultCustName", getString(resultCustName));
    	object.put("resultCredTypeName", getString(resultCredTypeName));
    	object.put("resultCredNo", getString(resultCredNo));
    	object.put("resultAddress", getString(resultAddress));
    	object.put("resultTelNo", getString(resultTelNo));
    	object.put("resultMobileNo", getString(resultMobileNo));
    	object.put("resultAvblBal", getString(resultAvblBal));
    	object.put("resultEmail", getString(resultEmail));
    	object.put("resultDepositTypeIsTimes", getString(resultDepositTypeIsTimes));
    	object.put("resultMessage", getString(resultMessage));
    	object.put("resultBranchName", getString(resultBranchName));
    	object.put("resultCardBin", getString(resultCardBin));
    	object.put("resultEndCardId", getString(resultEndCardId));
    	    	
    	this.respond(object.toString());
    }
    
    /**
     * 取字符的值，避免取到null
     * 
     * @param string
     * @return
     */
    private String getString(String string) {
    	return StringUtils.isBlank(string) ? StringUtils.EMPTY : string;
    }
    
    // 服务端计算返利金额、实收金额等，返回给客户端
	public void calRealAmt(){
		String cardId = request.getParameter("cardId");
    	String type = request.getParameter("type");	// type为0时是充值次数
    	
    	JSONObject object = new JSONObject();
    	BigDecimal actual = null;
    	this.cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);

		// In param
		String amt = this.depositReg.getAmt().toString();
    	
    	if ("0".equals(type)) {
    		// 根据卡子类型得到次卡子类型
    		String subClass = this.cardInfo.getCardSubclass();
    		CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(subClass);
    		String frequencyClass = cardSubClassDef.getFrequencyClass();
    		AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(frequencyClass);
    		
    		// 实收金额 = 充值次数 * 次卡清算金额
    		actual = AmountUtil.multiply(this.depositReg.getAmt(), accuClassDef.getSettAmt());
    	} else {
    		actual = new BigDecimal(amt); 
    	}
    	
    	// 售卡手续费 = 售卡总金额*手续费比例/100
		BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, depositReg.getFeeRate()), new BigDecimal(100));
    	
		CardCustomer cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.depositReg.getCardCustomerId());
		
		// 计算卡金额等
		RebateRule  rebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.depositReg.getRebateId());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amt", actual.toString());
		
		if (cardCustomer != null && CustomerRebateType.SPECIFY_CARD.getValue().equals(cardCustomer.getRebateType())) {
			params.put("rebateType", RebateType.CARD.getValue());
		} else {
			params.put("rebateType", this.rebateType);
		}
		params.put("rebateRule", rebateRule);
		Rebate rebate = this.rebateRuleService.calculateRebate(params);  // 计算返利金额等
		
		object.put("feeAmt", feeAmt); //手续费
		object.put("realAmt", AmountUtil.add(rebate.getRealAmt(), feeAmt)); // 实收金额 + 手续费
		object.put("rebateAmt", rebate.getRebateAmt());
		object.put("isUsedPeriodRule", rebateRuleDAO.isUsedPeriodRule(cardId,
				this.depositReg.getRebateId()));
		this.respond(object.toString());
		// Out param
//		String realAmt = rebate.getRealAmt().toString();
//		String rebateAmt = rebate.getRebateAmt().toString();		
//		this.respond("{'realAmt':"+ realAmt + ", 'rebateAmt':" + rebateAmt + "}");
	}

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		
		this.statusList = RegisterState.ALL.values();
		this.cardClassList = this.cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		this.rebateTypeList = RebateType.getForSellSingle();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositReg != null) {
			params.put("depositBatchId", depositReg.getDepositBatchId());
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(depositReg.getCardId()));			
			params.put("cardClass", depositReg.getCardClass());
			params.put("status", depositReg.getStatus());
			params.put("cardBranch", depositReg.getCardBranch()); //发卡机构
			params.put("cardCustomer", MatchMode.ANYWHERE.toMatchString(depositReg.getCardCustomerName()));// 购卡客户
			params.put("rebateType", depositReg.getRebateType()); // 返利方式
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("depositBranchName", MatchMode.ANYWHERE.toMatchString(depositReg.getDepositBranch()));// 购卡客户
		}
		
		if (isCenterOrCenterDeptRoleLogined()){ // 如果登录用户为运营中心，运营中心部门时，查看所有
			showCardBranch = true;
			
		} else if ( isFenzhiRoleLogined()) { // 运营机构，查看自己及自己的下级分支机构管理的所有发卡机构的记录
			showCardBranch = true;
//			params.put("fenzhiCode", this.getLoginBranchCode());
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardSellRoleLogined()) { // 售卡代理
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", this.getLoginBranchCode());
			
		}else if(isCardDeptRoleLogined()){// 发卡机构部门
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", this.getSessionUser().getDeptId());
				
		} else if ( isCardRoleLogined()) { // 发卡机构登录
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
//			params.put("cardBranch", this.getLoginBranchCode());
			
		} else {
			throw new BizException("没有权限查看充值记录");
		}
		params.put("fromPage", DepositFromPageType.SINGLE.getValue());
		params.put("fileDeposit", Symbol.NO);
//		params.put("cancelFlag", Symbol.NO);
		params.put("depositCancel", DepositCancelFlag.NORMAL.getValue());// 正常充值
		
		this.page = this.depositRegDAO.findDepositReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 卡列表
	public String list() throws Exception{
		return execute();
	}
	
	// 明细页面
	public String detail() throws Exception{
		Assert.notNull(this.depositReg, "充值对象不能为空");	
		Assert.notNull(this.depositReg.getDepositBatchId(), "充值对象批次ID不能为空");			
		
		// 充值登记簿明细
		this.depositReg = (DepositReg)this.depositRegDAO.findByPk(this.depositReg.getDepositBatchId());
		
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.depositReg.getCardClass());
		
		// 账户ID
		this.cardInfo = (CardInfo)this.cardInfoDAO.findByPk(this.depositReg.getCardId());
		Assert.notNull(cardInfo, "卡号[" + depositReg.getCardId() + "]已经不存在");
		this.cardInfo.setAcctId(this.cardInfo.getAcctId());
//		this.cardInfo = new CardInfo();
//		this.cardInfo.setAcctId("0000-0000");


		// 购卡客户明细
		this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.depositReg.getCardCustomerId());
		// 卡BIN明细
		this.cardBin = (CardBin)this.cardBinDAO.findByPk(CardBin.getBinNo(this.depositReg.getCardId()));		
		// 充值返利明细
		this.rebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.depositReg.getRebateId());
		// 充值返利分段比例明细列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rebateId", this.depositReg.getRebateId());			
		this.rebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(params);
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询单笔充值批次[" + depositReg.getDepositBatchId() + "]明细！");
		
		return DETAIL;
	}
	
	public static void main(String[] args){
		System.out.print(System.currentTimeMillis());
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if ( !isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()){
			throw new BizException("非 发卡机构 、发卡机构网点、售卡代理禁止进入充值页面！");
		}
		
    	// 取得客户的返利方式
    	this.rebateTypeList = RebateType.getForSellSingle();
    	this.depositReg = new DepositReg();
    	
    	signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
    	depositReg.setRebateType(RebateType.SHARE.getValue()); // 默认是平摊
    	if (signatureReg) {
    		// 随机数
    		this.depositReg.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		} else {
			this.depositReg.setRandomSessionId("");
		}
    	
		logger.debug("用户[" + this.getSessionUserCode() + "]进入新增充值页面！");
		return ADD;
	}

	// 新增
	public String add() throws Exception {
		if ( !isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()){
			throw new BizException("非 发卡机构 、发卡机构网点、售卡代理禁止进入充值页面！");
		}
		
		String cardId = depositReg.getCardId();
		// 判断是否有给该卡充值的权限
		if (isCardSellRoleLogined()) {
			Assert.isTrue(hasCardSellPrivilegeByCardId(this.getSessionUser().getRole().getBranchNo(), cardId, Constants.PRIVILEGE_DEPOSIT_CARD), 
					"该售卡代理没有权限为该卡["+cardId+"]充值");
		}
		
		// 设置页面来源
		this.depositReg.setFromPage(DepositFromPageType.SINGLE.getValue());
		
		// 设置充值机构
		CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(this.depositReg.getCardId());
		if(BranchBizConfigCache.isSetDepositBranchByAppOrgId(cardInfo.getCardIssuer())){
			this.depositReg.setDepositBranch(cardInfo.getAppOrgId());
		}else{
			if(isCardDeptRoleLogined()){//部门登录
				this.depositReg.setDepositBranch(this.getSessionUser().getDeptId());
			}else{
				this.depositReg.setDepositBranch(this.getLoginBranchCode());
			}
		}
		
		
		// 充值类型：0-按次充值 1-按金额充值
		String type = request.getParameter("timesDepositType");
		if ("0".equals(type)){
			this.depositReg.setDepositType(DepositType.TIMES.getValue());
		} else if("1".equals(type)){
			this.depositReg.setDepositType(DepositType.AMT.getValue());
		} else if("2".equals(type)){
			this.depositReg.setDepositType(DepositType.REBATE.getValue());
		} else {
			throw new BizException("充值方式错误");
		}
		
		// 单张卡充值都是实时充值
		this.depositReg.setPreDeposit(PreDepositType.REAL_DEPOSIT.getValue());
		
		// 如果是购卡客户指定返利卡，则添加卡号到返利卡字段中
		CardCustomer cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(depositReg.getCardCustomerId());
		if (cardCustomer.getRebateType().equals(CustomerRebateType.SPECIFY_CARD.getValue())) {
			this.depositReg.setRebateType(RebateType.CARD.getValue());
			this.depositReg.setRebateCard(cardCustomer.getRebateCard());
		}
		this.depositReg.setCardBranch(cardCustomer.getCardBranch());//发卡机构
		
		this.depositReg.setFileDeposit(Symbol.NO);// 不是以文件方式充值
		
		String serialNo = request.getParameter("serialNo");
		
		long id = this.depositService.addDepositReg(this.depositReg, this.getSessionUser(), serialNo);
		
		String msg = "添加充值登记号[" + id + "]，卡号[" + this.depositReg.getCardId() + "]，实收金额[" + this.depositReg.getRealAmt() + "]成功！";
		String url = "/depositReg/list.do";
		this.addActionMessage(url, msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	public DepositReg getDepositReg() {
		return depositReg;
	}

	public void setDepositReg(DepositReg depositReg) {
		this.depositReg = depositReg;
	}

	public SaleCardReg getSaleCardReg() {
		return saleCardReg;
	}

	public void setSaleCardReg(SaleCardReg saleCardReg) {
		this.saleCardReg = saleCardReg;
	}

	public CardCustomer getCardCustomer() {
		return cardCustomer;
	}

	public void setCardCustomer(CardCustomer cardCustomer) {
		this.cardCustomer = cardCustomer;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public RebateRule getRebateRule() {
		return rebateRule;
	}

	public void setRebateRule(RebateRule rebateRule) {
		this.rebateRule = rebateRule;
	}

	public List<RebateRuleDetail> getRebateRuleDetailList() {
		return rebateRuleDetailList;
	}

	public void setRebateRuleDetailList(List<RebateRuleDetail> rebateRuleDetailList) {
		this.rebateRuleDetailList = rebateRuleDetailList;
	}

	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public SubAcctBal getSubAcctBal() {
		return subAcctBal;
	}

	public void setSubAcctBal(SubAcctBal subAcctBal) {
		this.subAcctBal = subAcctBal;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public CardBin getCardBin() {
		return cardBin;
	}

	public void setCardBin(CardBin cardBin) {
		this.cardBin = cardBin;
	}

	public String getResultEndCardId() {
		return resultEndCardId;
	}

	public void setResultEndCardId(String resultEndCardId) {
		this.resultEndCardId = resultEndCardId;
	}

	public List<CardTypeCode> getCardClassList() {
		return cardClassList;
	}

	public void setCardClassList(List<CardTypeCode> cardClassList) {
		this.cardClassList = cardClassList;
	}

	public CardSubClass getCardSubClass() {
		return cardSubClass;
	}

	public void setCardSubClass(CardSubClass cardSubClass) {
		this.cardSubClass = cardSubClass;
	}

	public List<AccuClassDef> getAccuClassList() {
		return accuClassList;
	}

	public void setAccuClassList(List<AccuClassDef> accuClassList) {
		this.accuClassList = accuClassList;
	}

	public List<PointClassDef> getPointClassList() {
		return pointClassList;
	}

	public void setPointClassList(List<PointClassDef> pointClassList) {
		this.pointClassList = pointClassList;
	}

	public List<MembClassDef> getMembClassList() {
		return membClassList;
	}

	public void setMembClassList(List<MembClassDef> membClassList) {
		this.membClassList = membClassList;
	}

	public List<DiscntClassDef> getDiscntClassList() {
		return discntClassList;
	}

	public void setDiscntClassList(List<DiscntClassDef> discntClassList) {
		this.discntClassList = discntClassList;
	}

	public String getActionSubName() {
		return actionSubName;
	}

	public void setActionSubName(String actionSubName) {
		this.actionSubName = actionSubName;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public SignCust getSignCust() {
		return signCust;
	}

	public void setSignCust(SignCust signCust) {
		this.signCust = signCust;
	}

	public CustomerRebateReg getCustomerRebateReg() {
		return customerRebateReg;
	}

	public void setCustomerRebateReg(CustomerRebateReg customerRebateReg) {
		this.customerRebateReg = customerRebateReg;
	}

	public List<CustomerRebateReg> getCustomerRebateRegList() {
		return customerRebateRegList;
	}

	public void setCustomerRebateRegList(
			List<CustomerRebateReg> customerRebateRegList) {
		this.customerRebateRegList = customerRebateRegList;
	}

	public SignRuleReg getSignRuleReg() {
		return signRuleReg;
	}

	public void setSignRuleReg(SignRuleReg signRuleReg) {
		this.signRuleReg = signRuleReg;
	}

	public SaleSignCardReg getSaleSignCardReg() {
		return saleSignCardReg;
	}

	public void setSaleSignCardReg(SaleSignCardReg saleSignCardReg) {
		this.saleSignCardReg = saleSignCardReg;
	}

	public List<SaleSignCardReg> getSaleSignCardRegList() {
		return saleSignCardRegList;
	}

	public void setSaleSignCardRegList(List<SaleSignCardReg> saleSignCardRegList) {
		this.saleSignCardRegList = saleSignCardRegList;
	}

	public List getRebateTypeList() {
		return rebateTypeList;
	}

	public void setRebateTypeList(List rebateTypeList) {
		this.rebateTypeList = rebateTypeList;
	}

	public List<RebateRule> getRebateRuleList() {
		return rebateRuleList;
	}

	public void setRebateRuleList(List<RebateRule> rebateRuleList) {
		this.rebateRuleList = rebateRuleList;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public String getResultCardBin() {
		return resultCardBin;
	}

	public void setResultCardBin(String resultCardBin) {
		this.resultCardBin = resultCardBin;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
