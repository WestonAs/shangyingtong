package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.DepositReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.flag.DepositCancelFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.DepositService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: DepositCancelAction.java
 *
 * @description: 充值撤销
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-15
 */
public class DepositCancelAction extends BaseAction {
	
	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private DepositService depositService;
	
	private DepositReg depositReg;
	private CardCustomer cardCustomer;
	// 客户返利
	private RebateRule saleRebateRule;
	private List<RebateRuleDetail> rebateRuleDetailList;
	private CardTypeCode cardTypeCode;
	
	private Paginater page;
	private Paginater depositBatPage;
	
	private List<RegisterState> statusList;
	private List<CardType> cardTypeList;
	private List<DepositCancelFlag> cancelFlagList;
	
	private boolean depositTypeIsTimes;
	private String depositBatchId;
	
	/** 发卡机构列表 */
	private List<BranchInfo> cardBranchList;

	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		this.cardTypeList = CardType.getCardTypeWithoutPoint();
		this.cancelFlagList = DepositCancelFlag.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositReg != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(depositReg.getCardId()));
			params.put("depositBatchId", depositReg.getDepositBatchId());
			params.put("status", depositReg.getStatus());		
			params.put("cardClass", depositReg.getCardClass());	
			params.put("depositCancel", depositReg.getDepositCancel());
		}
		// 如果登录用户为运营中心，运营中心部门时，查看所有
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
		// params.put("fenzhiCode", this.getLoginBranchCode());
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardDeptRoleLogined() || isCardSellRoleLogined()) {// 登录用户为售卡代理或发卡机构部门时
			//this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", this.getSessionUser().getDeptId());
		} else if (isCardRoleLogined()) {// 如果登录用户为发卡机构时
		// params.put("cardBranch", super.getSessionUser().getBranchNo());
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看充值撤销记录");
		}
		
		//params.put("cancelFlag", Symbol.YES);
		//params.put("depositCancel", DepositCancelFlag.CANCEL.getValue());// 正常充值
		
		this.page = depositRegDAO.findDepositRegCancel(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String detail() throws Exception {
		detailPage();
		return DETAIL;
	}
	
	private void detailPage() {
		// 充值登记簿
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depositBatchId", this.depositReg.getDepositBatchId());
		this.depositReg = (DepositReg) this.depositRegDAO.findByPk(depositReg.getDepositBatchId());	
		
		RebateRule rebateRule = (RebateRule) rebateRuleDAO.findByPk(depositReg.getRebateId());
		this.depositReg.setRebateName(rebateRule.getRebateName());
		
		this.depositBatPage = this.depositBatRegDAO.findDepositBatRegByPage(params, this.getPageNumber(), this.getPageSize());
		
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
	}
	
	/**
	 * 要审核的充值撤销列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
//		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())
//				|| this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){
//			params.put("depositBranch", getSessionUser().getBranchNo());
//		} else if (this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())){
//			params.put("depositBranch", getSessionUser().getDeptId());
//		} else {
//			throw new BizException("没有权限进行充值撤销审核操作");
//		}
		
		if (isCardRoleLogined()) { // 发卡机构登录时
			// params.put("depositBranch", getSessionUser().getBranchNo());
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		} else if (isCardSellRoleLogined()) { // 售卡代理登录时
			params.put("depositBranch", getSessionUser().getBranchNo());
		} else if (isCardDeptRoleLogined()) { // 发卡机构部门
			params.put("depositBranch", getSessionUser().getDeptId());
		} else {
			throw new BizException("没有权限进行充值撤销审核操作");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_DEPOSIT_CANCEL, this.getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		params.put("ids", ids);
		this.page = depositRegDAO.findDepositRegCancel(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
	
	/**
	 * 要审核的充值撤销的明细
	 * @return
	 * @throws Exception
	 */
	public String checkDetail() throws Exception {
		detailPage();
		return "checkDetail";
	}
	
	/**
	 * 充值撤销操作
	 * @return
	 * @throws Exception
	 */
	public String cancel() throws Exception {
		hasRightTodo();
		depositReg = depositService.depositCancel(depositBatchId, getSessionUser());
		
		String msg = LogUtils.r("撤销充值批次为[{0}]的充值成功", depositReg.getOldDepositBatch());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/depositCancel/list.do", msg);
		return SUCCESS;
	}
	
	private void hasRightTodo() throws BizException {
		this.checkEffectiveCertUser();
		
		// 发卡机构和发卡机构网点和售卡代理
		if (!(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined())){
			throw new BizException("没有权限做充值撤销！");
		}
	}
	
	/**
	 * 取消充值,对于预充值还没激活的
	 * @return
	 * @throws Exception
	 */
	public String revoke() throws Exception {
		hasRightTodo();
		depositService.depositRevoke(depositBatchId, getSessionUser());
		
		String msg = LogUtils.r("取消充值批次为[{0}]的充值成功", depositBatchId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/depositCancel/list.do", msg);
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

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
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

	public List<DepositCancelFlag> getCancelFlagList() {
		return cancelFlagList;
	}

	public void setCancelFlagList(List<DepositCancelFlag> cancelFlagList) {
		this.cancelFlagList = cancelFlagList;
	}

	public String getDepositBatchId() {
		return depositBatchId;
	}

	public void setDepositBatchId(String depositBatchId) {
		this.depositBatchId = depositBatchId;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}
}
