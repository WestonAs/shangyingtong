package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CardCustomerState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRuleService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardCustomerAction.java
 * 
 * @description: 购卡客户登记
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: BenYan
 * @modify: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-22
 */
public class CardCustomerAction extends BaseAction {

	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private SaleCardRuleService saleCardRuleService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private CardCustomer cardCustomer;
	private Paginater page;

	private List statusList;
	private List rebateTypeList;
	/** 证件类型 */
	private List<CertType> certTypeList;

	private Long cardCustomerId;
	private String usedLabel;
	
	/** 界面选择时是否单选 */
	private boolean radio;
	private List<BranchInfo> cardBranchList;
	
	/** 发卡机构名称 */
	private String cardBranchName;
	private List<YesOrNoFlag> isCommonList;

	// // 测试测试
	// public String calAmt(String amt){
	// return BigDecimal.valueOf(Double.valueOf(amt) * 2).toString();
	// }

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		rebateTypeList = CustomerRebateType.getAll();
		statusList = CardCustomerState.getAll();
		usedLabel = CardCustomerState.USED.getValue();
		this.isCommonList = YesOrNoFlag.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardCustomer != null) {
			params.put("cardCustomerId", cardCustomer.getCardCustomerId());
			params.put("cardCustomerName", MatchMode.ANYWHERE.toMatchString(cardCustomer.getCardCustomerName()));
			params.put("rebateType", cardCustomer.getRebateType());
			params.put("status", cardCustomer.getStatus());
			params.put("cardBranch", cardCustomer.getCardBranch());
			params.put("isCommon", cardCustomer.getIsCommon());
			params.put("riskLevel", cardCustomer.getRiskLevel());
		}
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心（部门）
			
		} else if (isFenzhiRoleLogined()) {// 分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardOrCardDeptRoleLogined()) {
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
			params.put("cardBranchList", cardBranchList);
			
		} else {
			throw new BizException("没有权限查看购卡客户登记记录");
		}
		this.page = cardCustomerDAO.findCardCustomer(params, getPageNumber(), getPageSize());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查看购卡客户登记列表成功");
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		cardCustomer = (CardCustomer) cardCustomerDAO.findByPk(cardCustomer.getCardCustomerId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		// 当前登录用户角色只能是发卡机构
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能新增购卡客户。");
		}
		rebateTypeList = CustomerRebateType.getAll();
		this.certTypeList = CertType.getAll();
		
		BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
		this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
		return ADD;
	}

	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		// 当前登录用户角色只能是发卡机构
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能修改购卡客户信息。");
		}
		rebateTypeList = CustomerRebateType.getAll();
		this.certTypeList = CertType.getAll();
		cardCustomer = (CardCustomer) cardCustomerDAO.findByPk(cardCustomer.getCardCustomerId());
		return MODIFY;
	}

	/**
	 * 是否是正确的卡号
	 */
	public void isCorrectRebateCard() throws Exception {
		boolean isCorrectRebateCard = saleCardRuleService.isCorrectRebateCard(cardCustomer, getSessionUser());
		this.respond("{'isCorrectRebateCard':" + isCorrectRebateCard + "}");
	}

	/**
	 * 新增
	 */
	public String add() throws Exception {
		cardCustomer.setIsCommon(Symbol.NO);
		saleCardRuleService.addCardCustomer(cardCustomer, getSessionUser());
		String msg = LogUtils.r("添加购卡客户ID为[{0}]的购卡客户成功！", cardCustomer.getCardCustomerId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/customerRebateMgr/cardCustomer/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String modify() throws Exception {
		saleCardRuleService.modifyCardCustomer(cardCustomer, getSessionUser());
		String msg = LogUtils.r("修改购卡客户ID为[{0}]的购卡客户成功！", cardCustomer.getCardCustomerId());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/customerRebateMgr/cardCustomer/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/**
	 * 删除购卡客户
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		saleCardRuleService.deleteCardCustomer(cardCustomerId);
		String msg = LogUtils.r("删除购卡客户ID为[{0}]的购卡客户成功！", cardCustomerId);
		log(msg, UserLogType.DELETE);
		addActionMessage("/customerRebateMgr/cardCustomer/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showSelect() throws Exception {
		if (isCardOrCardDeptRoleLogined()) {
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
		}
		return "select";
	}
	
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardCustomer != null) {
			params.put("cardCustomerId", cardCustomer.getCardCustomerId());
			params.put("cardCustomerName", MatchMode.ANYWHERE.toMatchString(cardCustomer.getCardCustomerName()));
			params.put("cardBranch", cardCustomer.getCardBranch());
		}
		if (isCardOrCardDeptRoleLogined()) {
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
		}
		params.put("cardBranchList", cardBranchList);
		page = cardCustomerDAO.findCardCustomer(params, getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	public CardCustomer getCardCustomer() {
		return cardCustomer;
	}

	public void setCardCustomer(CardCustomer cardCustomer) {
		this.cardCustomer = cardCustomer;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CardCustomerDAO getCardCustomerDAO() {
		return cardCustomerDAO;
	}

	public void setCardCustomerDAO(CardCustomerDAO cardCustomerDAO) {
		this.cardCustomerDAO = cardCustomerDAO;
	}

	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getUsedLabel() {
		return usedLabel;
	}

	public void setUsedLabel(String usedLabel) {
		this.usedLabel = usedLabel;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List getRebateTypeList() {
		return rebateTypeList;
	}

	public void setRebateTypeList(List rebateTypeList) {
		this.rebateTypeList = rebateTypeList;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<CertType> getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(List<CertType> certTypeList) {
		this.certTypeList = certTypeList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public List<YesOrNoFlag> getIsCommonList() {
		return isCommonList;
	}

	public void setIsCommonList(List<YesOrNoFlag> isCommonList) {
		this.isCommonList = isCommonList;
	}

}
