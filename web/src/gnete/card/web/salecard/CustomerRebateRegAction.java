package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CustomerRebateRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.type.RebateRuleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRuleService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CustomerRebateRegAction.java
 * 
 * @description: 为购卡客户配置返利规则
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: BenYan
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class CustomerRebateRegAction extends BaseAction {

	@Autowired
	private CustomerRebateRegDAO customerRebateRegDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private SaleCardRuleService saleCardRuleService;
//	@Autowired
//	private BranchProxyDAO branchProxyDAO;
	
	private String msg;
	
	private CustomerRebateReg customerRebateReg;
	private CardCustomer cardCustomer;
	private CardBin cardBin;
	private RebateRule saleRebateRule;
	private RebateRule depositRebateRule;

	private List<CardCustomer> cardCustomerList;
	private List<CardBin> cardBinList;
	private List<RebateRule> rebateRuleList;
	private List<RebateRuleDetail> saleRebateRuleDetailList;
	private List<RebateRuleDetail> depositRebateRuleDetailList;
	/** 发卡机构列表  */
	private List<BranchInfo> cardBranchList; 
	private String customerRebateRegId;

	private Paginater page;

	private String detailType;

	// 界面选择时是否单选
	private boolean radio;
	private String cardType;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (customerRebateReg != null) {
			params.put("cardCustomerId", customerRebateReg.getCardCustomerId());
			params.put("cardCustomerName", MatchMode.ANYWHERE.toMatchString(customerRebateReg.getCardCustomerName()));
			params.put("binNo", customerRebateReg.getBinNo());
			params.put("binName", MatchMode.ANYWHERE.toMatchString(customerRebateReg.getBinName()));
			params.put("saleRebateId", customerRebateReg.getSaleRebateId());
			params.put("saleRebateName", MatchMode.ANYWHERE.toMatchString(customerRebateReg.getSaleRebateName()));
			params.put("depositRebateId", customerRebateReg.getDepositRebateId());
			params.put("depositRebateName", MatchMode.ANYWHERE.toMatchString(customerRebateReg.getDepositRebateName()));
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) {
//			params.put("cardBranch", getSessionUser().getBranchNo());
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
			params.put("cardBranchList", cardBranchList);
			
		} else {
			throw new BizException("没有权限查询购卡客户返利规则");
		}
//		// 当前登录用户角色为运营中心或运营分支机构时
//		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
//				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())
//				|| RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
//			List<BranchInfo> branchList = super.getMyCardBranch();
//			if (CollectionUtils.isEmpty(branchList)) {
//				branchList.add(new BranchInfo());
//			}
//			params.put("cardBranchList", branchList);
//		}

		this.page = this.customerRebateRegDAO.findCustomerRebateRegList(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 明细页面
	 */
	public String detail() throws Exception {
		Assert.notNull(customerRebateReg, "客户返利对象不能为空");
		Assert.notNull(customerRebateReg.getCustomerRebateRegId(), "客户返利ID不能为空");
		// Assert.notNull(customerRebateReg.getBinNo(), "卡BIN不能为空");

		// 客户返利明细
		this.customerRebateReg = (CustomerRebateReg) this.customerRebateRegDAO.findByPk(customerRebateReg.getCustomerRebateRegId());
		// 购卡客户明细
		this.cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(this.customerRebateReg.getCardCustomerId());
		// 卡BIN明细
		this.cardBin = (CardBin) this.cardBinDAO.findByPk(this.customerRebateReg.getBinNo());
		// 售卡返利明细
		this.saleRebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.customerRebateReg.getSaleRebateId());
		// 售卡返利分段比例明细列表
		Map<String, Object> saleParams = new HashMap<String, Object>();
		saleParams.put("rebateId", customerRebateReg.getSaleRebateId());
		this.saleRebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(saleParams);
		// 充值返利明细
		this.depositRebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.customerRebateReg.getDepositRebateId());
		// 充值返利分段比例明细列表
		Map<String, Object> depositParams = new HashMap<String, Object>();
		depositParams.put("rebateId", customerRebateReg.getDepositRebateId());
		this.depositRebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(depositParams);

		return DETAIL;
	}

	/**
	 * 打开新增页面的初始化操作
	 */
	public String showAdd() throws Exception {
		// 当前登录用户角色只能是发卡机构
		if (isCardRoleLogined()) {
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
			
		} else {
			throw new BizException("只有发卡机构能够发起客户返利申请");
		}

		return ADD;
	}
	
	/**
	 * 加载购卡客户列表
	 * @throws Exception
	 */
	public void findCustomerRebateType() throws Exception {
		String cardBranch = request.getParameter("cardBranch");
		if (StringUtils.isEmpty(cardBranch)) {
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardBranch", cardBranch);
		params.put("isCommons", new String[]{RebateRuleType.NO.getValue(),RebateRuleType.TOTAL.getValue()});
		
		List<CardCustomer> list = this.cardCustomerDAO.findCardCustomer(params);
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("<option value=\"\">--请选择--</option>");
		for (CardCustomer cardCustomer : list) {
			sb.append("<option value=\"").append(cardCustomer.getCardCustomerId()).append("\">")
				.append(cardCustomer.getCardCustomerName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	/**
	 * 加载返利规则列表
	 * @throws Exception
	 */
	public void findRebateRule() throws Exception {
		String cardBranch = request.getParameter("cardBranch");
		if (StringUtils.isEmpty(cardBranch)) {
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardBranch", cardBranch);
		params.put("isCommons", new String[]{RebateRuleType.NO.getValue()});
		
		List<RebateRule> list = this.rebateRuleDAO.findRebateRule(params);
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("<option value=\"\">--请选择--</option>");
		for (RebateRule rebateRule : list) {
			sb.append("<option value=\"").append(rebateRule.getRebateId()).append("\">")
				.append(rebateRule.getRebateName()).append("</option>");
		}
		this.respond(sb.toString());
	}

	/**
	 * 当前登录角色为发卡机构时，得到发卡机构的机构号
	 */
	public String getCardIssuerBin() {
		String cardIssuerBin = "";
		if (isCardRoleLogined()) {
			cardIssuerBin = getSessionUser().getBranchNo();
		}
		return cardIssuerBin;
	}

	/**
	 * 新增
	 */
	public String add() throws Exception {

		// 插入客户返利记录及登记簿记录
		// this.customerRebateService.addCustomerRebate(this.customerRebate,
		// this.getSessionUserCode());
		CustomerRebateReg reg = this.saleCardRuleService.addCustomerRebateReg(this.customerRebateReg, this.getSessionUser());

		// 启动单个流程（后续错误，但数据已提交）
		this.workflowService.startFlow(reg, "customerRebateRegAdapter", Long.toString(reg.getCustomerRebateRegId()), this.getSessionUser());

		String msg = LogUtils.r("客户[{0}]的卡BIN号为[{1}]的客户返利申请已经提交", reg.getCardCustomerId(), reg.getBinNo());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/customerRebateMgr/customerRebateReg/list.do?goBack=goBack", msg);

		return SUCCESS;
	}
	
	/**
	 * 停用
	 * @return
	 * @throws Exception
	 */
	public String stop() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构能够停用客户返利规则");
		}
		this.saleCardRuleService.stopCustomerRebateReg(customerRebateRegId, this.getSessionUser());
		
		String msg = LogUtils.r("停用客户返利登记ID为[{0}]的记录成功", customerRebateRegId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/customerRebateMgr/customerRebateReg/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	/**
	 * 启用
	 * @return
	 * @throws Exception
	 */
	public String start() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构能够启用客户返利规则");
		}
		this.saleCardRuleService.startCustomerRebateReg(customerRebateRegId, this.getSessionUser());
		
		String msg = LogUtils.r("启用客户返利登记ID为[{0}]的记录成功", customerRebateRegId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/customerRebateMgr/customerRebateReg/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showSelect() throws Exception {
		return "select";
	}

	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardBin == null || StringUtils.isEmpty(cardBin.getBinNo())) {
			this.setMsg("请先输入卡号再选择购卡客户");
			return "data";
		}
		CardBin bin = (CardBin) this.cardBinDAO.findByPk(cardBin.getBinNo());
		if (bin == null) {
			this.setMsg("卡不存在！");
			return "data";
		}
		if (cardType != null && !StringUtils.equals(cardType, bin.getCardType())) {
			this.setMsg("卡种类不符！");
			return "data";
		}

		if (isCardOrCardDeptRoleLogined()){
			params.put("cardBranch", bin.getCardIssuer());
			
		} else if (isCardSellRoleLogined()){
			params.put("cardBranch", bin.getCardIssuer());
			
		} else {
			this.setMsg("非售卡机构禁止进入售卡！");
			return "data";
		}
		
		if (cardCustomer != null) {
			params.put("cardCustomerId", cardCustomer.getCardCustomerId());
			params.put("cardCustomerName", MatchMode.ANYWHERE.toMatchString(cardCustomer.getCardCustomerName()));
			params.put("cardType", cardType);
		}
		this.page = this.cardCustomerDAO.findCardCustomer(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}

	// 打开修改页面的初始化操作
	@Deprecated
	public String showModify() throws Exception {
		// 当前登录用户角色只能是发卡机构
		if (!getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("只有发卡机构才能修改客户返利申请。");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		// 返利列表
		this.rebateRuleList = this.rebateRuleDAO.findRebateRule(params);
		return MODIFY;
	}

	// 修改 已经去掉该功能
	@Deprecated
	public String modify() throws Exception {

		this.saleCardRuleService.modifyCustomerRebateReg(
				this.customerRebateReg, this.getSessionUser());

		String msg = LogUtils.r("修改购卡客户对应规则ID为[{0}]的记录成功！", customerRebateReg.getCustomerRebateRegId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/customerRebateMgr/customerRebate/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	// 删除
	@Deprecated
	public String delete() throws Exception {

		// this.customerRebateRegService.deleteCustomerRebateReg(this.customerRebateReg.getCardCustomerRegId());

		String msg = LogUtils.r("删除购卡客户对应规则ID为[{0}]的记录成功！", customerRebateReg.getCustomerRebateRegId());
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/customerRebateMgr/customerRebate/list.do", msg);
		return SUCCESS;
	}

	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] customerRebateRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_CUSTOMER_REBATE_REG, this.getSessionUser());

		if (ArrayUtils.isEmpty(customerRebateRegIds)) {
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", customerRebateRegIds);
		this.page = this.customerRebateRegDAO.findCustomerRebateRegList(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception {

		// 客户返利登记簿明细
		this.customerRebateReg = (CustomerRebateReg) this.customerRebateRegDAO.findByPk(customerRebateReg.getCustomerRebateRegId());
		// 购卡客户明细
		this.cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(this.customerRebateReg.getCardCustomerId());
		// 卡BIN明细
		this.cardBin = (CardBin) this.cardBinDAO.findByPk(this.customerRebateReg.getBinNo());
		// 售卡返利明细
		this.saleRebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.customerRebateReg.getSaleRebateId());
		// 售卡返利分段比例明细列表
		Map<String, Object> saleParams = new HashMap<String, Object>();
		saleParams.put("rebateId", customerRebateReg.getSaleRebateId());
		this.saleRebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(saleParams);
		// 充值返利明细
		this.depositRebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.customerRebateReg.getDepositRebateId());
		// 充值返利分段比例明细列表
		Map<String, Object> depositParams = new HashMap<String, Object>();
		depositParams.put("rebateId", customerRebateReg.getDepositRebateId());
		this.depositRebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(depositParams);

		return DETAIL;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public RebateRule getDepositRebateRule() {
		return depositRebateRule;
	}

	public void setDepositRebateRule(RebateRule depositRebateRule) {
		this.depositRebateRule = depositRebateRule;
	}

	public CardBin getCardBin() {
		return cardBin;
	}

	public void setCardBin(CardBin cardBin) {
		this.cardBin = cardBin;
	}

	public List<RebateRuleDetail> getSaleRebateRuleDetailList() {
		return saleRebateRuleDetailList;
	}

	public void setSaleRebateRuleDetailList(
			List<RebateRuleDetail> saleRebateRuleDetailList) {
		this.saleRebateRuleDetailList = saleRebateRuleDetailList;
	}

	public List<RebateRuleDetail> getDepositRebateRuleDetailList() {
		return depositRebateRuleDetailList;
	}

	public void setDepositRebateRuleDetailList(
			List<RebateRuleDetail> depositRebateRuleDetailList) {
		this.depositRebateRuleDetailList = depositRebateRuleDetailList;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public List<CardCustomer> getCardCustomerList() {
		return cardCustomerList;
	}

	public void setCardCustomerList(List<CardCustomer> cardCustomerList) {
		this.cardCustomerList = cardCustomerList;
	}

	public List<CardBin> getCardBinList() {
		return cardBinList;
	}

	public void setCardBinList(List<CardBin> cardBinList) {
		this.cardBinList = cardBinList;
	}

	public List<RebateRule> getRebateRuleList() {
		return rebateRuleList;
	}

	public void setRebateRuleList(List<RebateRule> rebateRuleList) {
		this.rebateRuleList = rebateRuleList;
	}

	public CustomerRebateReg getCustomerRebateReg() {
		return customerRebateReg;
	}

	public void setCustomerRebateReg(CustomerRebateReg customerRebateReg) {
		this.customerRebateReg = customerRebateReg;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCustomerRebateRegId() {
		return customerRebateRegId;
	}

	public void setCustomerRebateRegId(String customerRebateRegId) {
		this.customerRebateRegId = customerRebateRegId;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}
}
