package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.flag.DepositCancelFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.DepositFromPageType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PreDepositType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.DepositService;
import gnete.card.service.RebateRuleService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: DepositBatRegFileAction.java
 *
 * @description: 以上传文件的方式进行批量充值操作
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-7
 */
public class DepositBatRegFileAction extends BaseAction {
	
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;
	@Autowired
	private DepositService depositService;
	@Autowired
	private RebateRuleService rebateRuleService;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
//	@Autowired
//	private CardInfoDAO cardInfoDAO;
	
	private DepositReg depositReg;
	private DepositBatReg depositBatReg;
	private CardCustomer cardCustomer;
	// 客户返利
	private RebateRule saleRebateRule;
	private List<RebateRuleDetail> rebateRuleDetailList;
	private CardTypeCode cardTypeCode;
	
	private Paginater page;
	private Paginater depositBatPage;
	
	private List<RegisterState> statusList;
	private List<CardType> cardTypeList;
	private List<RebateType> rebateTypeList;
	
	private String totalNum;
	private boolean signatureReg;
	private String rebCard; // 返利到指定卡，是为Y，否为N
	private String timesDepositType; //充值方式
	private String calcMode; //手工计算，还是自动计算 0自动，1手工
	private boolean depositTypeIsTimes;
	private boolean isOldFileFmt; //文件格式：是否是旧文件格式（积分卡一代）
	
	private File upload;//上传的文件
	private String uploadFileName;// 上传的文件名
	
	private String preDeposit; //预充值标志，0：实时充值，1：预充值
	 // 页面Action子名
    private String actionSubName;
    private String fromPage;// 页面来源
    
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

	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		this.cardTypeList = CardType.getCardTypeWithoutPoint();
		this.rebateTypeList = RebateType.getForSellBatchFile();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositBatReg != null) {
			params.put("depositBatchId", depositBatReg.getDepositBatchId());
			params.put("status", depositBatReg.getStatus());		
			params.put("cardClass", depositBatReg.getCardClass());	
			
			params.put("cardBranch", depositReg.getCardBranch()); //发卡机构
			params.put("cardCustomer", MatchMode.ANYWHERE.toMatchString(depositReg.getCardCustomerName()));// 购卡客户
			params.put("rebateType", depositReg.getRebateType()); // 返利方式
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		if (isCenterOrCenterDeptRoleLogined()) {
			showCardBranch = true;
			
		} else if (isFenzhiRoleLogined()) {// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
			showCardBranch = true;
			// params.put("fenzhiCode", this.getLoginBranchCode());
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardDeptRoleLogined() || isCardSellRoleLogined()) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", super.getSessionUser().getBranchNo());
			
		} else if (isCardRoleLogined()) {
			this.cardBranchList = this.getMyCardBranch();
			// params.put("cardBranch", super.getSessionUser().getBranchNo());
			params.put("cardBranchList", cardBranchList);
			
		} else {
			throw new BizException("没有权限查看批量充值记录");
		}
		if (StringUtils.isNotBlank(fromPage)) {
			params.put("fromPage", fromPage);
		} else {
			params.put("fromPage", DepositFromPageType.FILE.getValue());
		}
		params.put("fileDeposit", Symbol.YES);
		params.put("cancelFlag", Symbol.NO);
		params.put("depositCancel", DepositCancelFlag.NORMAL.getValue());
		
		this.page = depositBatRegDAO.findDepositReg(params, this.getPageNumber(), this.getPageSize());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询[" 
				+ DepositFromPageType.valueOf((String) params.get("fromPage")).getName() +"]列表成功！");
		return LIST;
	}
	
	public String listPreFile() throws Exception {
		this.fromPage = DepositFromPageType.PRE_FILE.getValue();
		return execute();
	}
	
	public String detail() throws Exception {
		// 充值登记簿
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depositBatchId", this.depositBatReg.getDepositBatchId());
		this.depositReg = (DepositReg)(this.depositBatRegDAO.findDepositReg(params)).get(0);	
		
		this.depositBatPage = this.depositBatRegDAO.findDepositBatRegByPage(params, this.getPageNumber(), this.getPageSize());
		
		 if (StringUtils.equals(this.depositReg.getFromPage(), DepositFromPageType.PRE_FILE.getValue())) {
			this.setActionSubName("PreFile");
		}
		// 充值类型：0-按金额充值 1-按次充值
		if(this.depositReg.getDepositType().equals(DepositType.TIMES.getValue())){
			this.setDepositTypeIsTimes(true);
		}else{
			this.setDepositTypeIsTimes(false);
		}
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.depositReg.getCardClass());
		
		// 购卡客户明细
		this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.depositReg.getCardCustomerId());
		// 充值返利明细
		this.saleRebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.depositReg.getRebateId());
		// 充值返利分段比例明细列表
		params = new HashMap<String, Object>();
		params.put("rebateId", this.depositReg.getRebateId());			
		this.rebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(params);
		
		return DETAIL;
	}
	
	/**
	 * 预充值
	 * @return
	 * @throws Exception
	 */
	public String showPreAdd() throws Exception {
		showAddInitPage();
		this.actionSubName = "PreFile";
		this.preDeposit = PreDepositType.PRE_DEPOSIT.getValue();
		this.fromPage = DepositFromPageType.PRE_FILE.getValue();
		return ADD;
	}
	
	/**
	 * 正常充值
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		this.setFromPage(DepositFromPageType.FILE.getValue());
		showAddInitPage();
		return ADD;
	}
	
	/**
	 * 新增数据预处理
	 * @throws Exception
	 */
	private void showAddInitPage() throws Exception {
		this.checkEffectiveCertUser();
		
		// 发卡机构和发卡机构网点和售卡代理
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined(), "非充值机构禁止充值！");
		
		this.cardTypeList = CardType.getCardTypeWithoutPoint();
		this.rebateTypeList = RebateType.getForSellBatchFile();
		
		depositReg = new DepositReg();
		depositReg.setRebateType(RebateType.SHARE.getValue());
		depositReg.setCardClass(CardType.DEPOSIT.getValue());
		// 是否需要进行签名
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
    		// 随机数
    		this.depositReg.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		} else {
			this.depositReg.setRandomSessionId("");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isCommon", Symbol.YES);
		map.put("cardBranch", getSessionUser().getBranchNo());
		//通用购卡客户
		List<CardCustomer> cardCustomerList = cardCustomerDAO.findCardCustomer(map);
		if(cardCustomerList == null || cardCustomerList.size() == 0){
			throw new BizException("通用购卡客户不存在！");
		}else{
			depositReg.setCardCustomerId(cardCustomerList.get(0).getCardCustomerId());
			depositReg.setCardCustomerName(cardCustomerList.get(0).getCardCustomerName());
		}
		
		//通用返利规则
		List<RebateRule> rebateRuleList = rebateRuleDAO.findRebateRule(map);
		if(rebateRuleList == null || rebateRuleList.size() == 0){
			throw new BizException("通用返利规则不存在！");
		}else{
			depositReg.setRebateId(rebateRuleList.get(0).getRebateId());
			depositReg.setRebateName(rebateRuleList.get(0).getRebateName());
		}
	}
	
	/**
	 * 根据购卡客户取得返利规则列表
	 * @return
	 * @throws Exception
	 */
	public void getRebateRuleList() throws Exception {
		String cardCustomerId = request.getParameter("cardCustomerId");
		if (StringUtils.isEmpty(cardCustomerId)) {
			return;
		}
		List<RebateRule> ruleList = new ArrayList<RebateRule>();
		
		//根据选中的购卡客户，取得它的发卡机构，取得该发卡机构的通用返利规则
		CardCustomer customer = (CardCustomer) cardCustomerDAO.findByPk(NumberUtils.toLong(cardCustomerId));
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("isCommon", Symbol.YES);
		map.put("cardBranch", customer.getCardBranch());
		ruleList.addAll(this.rebateRuleDAO.findRebateRule(map));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depositCardCustomerId", cardCustomerId);
		ruleList.addAll(rebateRuleDAO.findRebateRule(params));
		
		StringBuilder sb = new StringBuilder(128);
		for (RebateRule rule : ruleList) {
			sb.append("<option value=\"")
				.append(rule.getRebateId()).append("\">")
				.append(rule.getRebateName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	/**
	 * 根据客户端传来的数据取得实收金额，返利金额
	 * @return
	 * @throws Exception
	 */
	public void calcRealAmt() throws Exception {
		JSONObject object = new JSONObject();
		
		Map<String, Object> params = new HashMap<String, Object>(); 
//		String depositType = request.getParameter("depositType"); // 充值方式，按次充值，还是按金额充值
//		String cardClass = request.getParameter("cardClass");  //卡类型
		
		String totalAmt = request.getParameter("totalAmt"); //充值总金额
		String rebateType = request.getParameter("rebateType"); //返利方式
		String rebateId = request.getParameter("rebateId"); //返利规则ID
		String feeRate = request.getParameter("feeRate"); //手续费比例
		
		
		if (StringUtils.isBlank(totalAmt) 
				|| StringUtils.isBlank(rebateType)
				|| StringUtils.isBlank(rebateId)) {
			return;
		}
		
		BigDecimal actual = NumberUtils.createBigDecimal(totalAmt);
		BigDecimal feeRateSum = NumberUtils.createBigDecimal(feeRate);
		if (feeRateSum == null) {
			return;
		}

		// 充值手续费 充值手续费 = 充值总金额*手续费比例/100
		BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, feeRateSum), new BigDecimal(100));
		
		params.put("amt", totalAmt);
		params.put("rebateType", rebateType);
		
		RebateRule rebateRule = (RebateRule) rebateRuleDAO.findByPk(NumberUtils.toLong(rebateId));
		params.put("rebateRule", rebateRule);
		
		Rebate rebate = this.rebateRuleService.calculateRebate(params);
		
		// 实收金额 = 实收金额 + 手续费
		object.put("realAmt", AmountUtil.add(rebate.getRealAmt(), feeAmt));
		object.put("rebateAmt", rebate.getRebateAmt());
		object.put("feeAmt", feeAmt); // 手续费
		
		this.respond(object.toString());
		
//		String realAmt = rebate.getRealAmt().toString();
//		String rebateAmt = rebate.getRebateAmt().toString();		
//		this.respond("{'realAmt':"+ realAmt + ", 'rebateAmt':" + rebateAmt + "}");
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		long totalCount = Long.parseLong(totalNum);
		
		if ("0".equals(timesDepositType)){
			this.depositReg.setDepositType(DepositType.TIMES.getValue());
		} else if("1".equals(timesDepositType)){
			this.depositReg.setDepositType(DepositType.AMT.getValue());
		} else if("2".equals(timesDepositType)){
			this.depositReg.setDepositType(DepositType.REBATE.getValue());
		} else {
			throw new BizException("充值方式错误");
		}
		
		String url = "/depositBatRegFile/list.do";
		// 设置页面来源
		this.depositReg.setFromPage(this.fromPage);
		
		// 预充值
		if (StringUtils.equals(preDeposit, PreDepositType.PRE_DEPOSIT.getValue())){
			this.depositReg.setPreDeposit(preDeposit);
			url = "/depositBatReg/listPreFile.do";
		} else {
			this.depositReg.setPreDeposit(PreDepositType.REAL_DEPOSIT.getValue());
		}
		
		// 设置是否使用了手工指定返利，如果是需要手工发起一个流程
		if (StringUtils.equals(calcMode, "1") && isCardSellRoleLogined()) {
			this.depositReg.setManual(true);
		} else {
			this.depositReg.setManual(false);
		}
		
		Assert.isTrue(IOUtil.testFileFix(uploadFileName, Arrays.asList("txt", "csv")), "充值文件的格式只能是文本文件");
		
		String name = "充值的文件名为：" + uploadFileName;
		if (name.length() >= 200) {
			depositReg.setRemark(StringUtils.substring(name, 0, 190));
		} else {
			depositReg.setRemark(name);
		}
		
		String serialNo = request.getParameter("serialNo");
		
		depositReg = depositService.addDepositBatRegFile(upload, this.isOldFileFmt, depositReg, totalCount,
				getSessionUser(), Constants.DEPOSITBATREGFILE_ADD, serialNo);
		
		String msg = LogUtils.r("添加文件方式批量充值记录[{0}]成功", depositReg.getDepositBatchId());
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

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}

	public DepositBatReg getDepositBatReg() {
		return depositBatReg;
	}

	public void setDepositBatReg(DepositBatReg depositBatReg) {
		this.depositBatReg = depositBatReg;
	}

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public List<RebateType> getRebateTypeList() {
		return rebateTypeList;
	}

	public void setRebateTypeList(List<RebateType> rebateTypeList) {
		this.rebateTypeList = rebateTypeList;
	}

	public String getRebCard() {
		return rebCard;
	}

	public void setRebCard(String rebCard) {
		this.rebCard = rebCard;
	}

	public String getTimesDepositType() {
		return timesDepositType;
	}

	public void setTimesDepositType(String timesDepositType) {
		this.timesDepositType = timesDepositType;
	}

	public String getCalcMode() {
		return calcMode;
	}

	public void setCalcMode(String calcMode) {
		this.calcMode = calcMode;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Paginater getDepositBatPage() {
		return depositBatPage;
	}

	public void setDepositBatPage(Paginater depositBatPage) {
		this.depositBatPage = depositBatPage;
	}

	public boolean isDepositTypeIsTimes() {
		return depositTypeIsTimes;
	}

	public void setDepositTypeIsTimes(boolean depositTypeIsTimes) {
		this.depositTypeIsTimes = depositTypeIsTimes;
	}

	public CardCustomer getCardCustomer() {
		return cardCustomer;
	}

	public void setCardCustomer(CardCustomer cardCustomer) {
		this.cardCustomer = cardCustomer;
	}

	public RebateRule getSaleRebateRule() {
		return saleRebateRule;
	}

	public void setSaleRebateRule(RebateRule saleRebateRule) {
		this.saleRebateRule = saleRebateRule;
	}

	public List<RebateRuleDetail> getRebateRuleDetailList() {
		return rebateRuleDetailList;
	}

	public void setRebateRuleDetailList(List<RebateRuleDetail> rebateRuleDetailList) {
		this.rebateRuleDetailList = rebateRuleDetailList;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String getPreDeposit() {
		return preDeposit;
	}

	public void setPreDeposit(String preDeposit) {
		this.preDeposit = preDeposit;
	}

	public String getActionSubName() {
		return actionSubName;
	}

	public void setActionSubName(String actionSubName) {
		this.actionSubName = actionSubName;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
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

	public void setOldFileFmt(boolean isOldFileFmt) {
		this.isOldFileFmt = isOldFileFmt;
	}
}
