package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.dao.SignCustDAO;
import gnete.card.dao.SignRuleRegDAO;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SignCust;
import gnete.card.entity.SignRuleReg;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.DerateType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRuleService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SignRuleRegAction.java
 * 
 * @description: 签单规则管理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: BenYan
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-8-14
 */
public class SignRuleRegAction extends BaseAction {

	@Autowired
	private SignRuleRegDAO signRuleRegDAO;
	@Autowired
	private SignCustDAO signCustDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private SaleCardRuleService saleCardRuleService;

	private SignRuleReg signRuleReg;
	private SignCust signCust;
	private RebateRule rebateRule;

	private List<SignCust> signCustList;
	private List<RebateRule> rebateRuleList;
	private List<RebateRuleDetail> rebateRuleDetailList;
	private List derateTypeList;

	private Paginater page;

	private String usedLabel;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {

		this.setUsedLabel(RegisterState.PASSED.getValue());

		Map<String, Object> params = new HashMap<String, Object>();
		// 当前登录用户角色是发卡机构时，能看到当前机构所定义的签单客户
		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			params.put("cardBranch", getSessionUser().getBranchNo());
		}
		// 当前登录用户角色为运营中心或运营分支机构时，能看到它所管理的发卡机构的签单客户
		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
			params.put("cardBranchList", getMyCardBranch());
		}
		if (signRuleReg != null) {
			params.put("signRuleId", signRuleReg.getSignRuleId());
			params.put("signRuleName", MatchMode.ANYWHERE
					.toMatchString(signRuleReg.getSignRuleName()));
			params.put("signCustId", signRuleReg.getSignCustId());
			params.put("signCustName", MatchMode.ANYWHERE
					.toMatchString(signRuleReg.getSignCustName()));
		}
		this.page = this.signRuleRegDAO.findSignRuleReg(params, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		Assert.notNull(this.signRuleReg, "签单规则对象不能为空");
		Assert.notNull(this.signRuleReg.getSignRuleId(), "购卡客户ID不能为空");

		// 签单规则明细
		this.signRuleReg = (SignRuleReg) this.signRuleRegDAO
				.findByPk(this.signRuleReg.getSignRuleId());
		// 签单客户明细
		this.signCust = (SignCust) this.signCustDAO.findByPk(this.signRuleReg
				.getSignCustId());
		// 充值返利规则
		this.rebateRule = (RebateRule) this.rebateRuleDAO
				.findByPk(this.signRuleReg.getDepositRebateId());
		// 充值返利规则明细列表
		Map<String, Object> params = new HashMap<String, Object>();
		if (!(this.rebateRule == null)) {
			params.put("rebateId", this.rebateRule.getRebateId());
			this.rebateRuleDetailList = this.rebateRuleDetailDAO
					.findRebateRuleDetail(params);
		}

		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if (!getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("只有发卡机构才能新增签单规则");
		}

		this.derateTypeList = DerateType.getAll();
		// 购卡客户列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardBranch", getSessionUser().getBranchNo());
		this.signCustList = this.signCustDAO.findSignCust(params);

		String[] statusArray = new String[]{RebateRuleState.NORMAL.getValue(), RebateRuleState.USED.getValue()};
		params.put("statusArray", statusArray);
		
		// 返利列表
		this.rebateRuleList = this.rebateRuleDAO.findRebateRule(params);

		return ADD;
	}

	// 新增
	public String add() throws Exception {
		// 插入签单规则记录及登记簿记录
		SignRuleReg reg = this.saleCardRuleService.addSignRuleReg(
				this.signRuleReg, this.getSessionUser());

		// 启动单个流程（后续错误，但数据已提交）
		this.workflowService.startFlow(signRuleReg, "signRuleRegAdapter", Long
				.toString(reg.getSignRuleId()), this.getSessionUser());

		String msg = LogUtils.r("签单规则ID为[{0}]的签单规则申请已经提交！", reg.getSignRuleId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/signCardMgr/signRuleReg/list.do", msg);

		return SUCCESS;
	}

	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		if (!getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("只有发卡机构才能修改签单规则");
		}
		this.signRuleReg = (SignRuleReg) this.signRuleRegDAO
				.findByPk(this.signRuleReg.getSignRuleId());

		// 购卡客户列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardBranch", getSessionUser().getBranchNo());
		this.signCustList = this.signCustDAO.findSignCust(params);
		this.signCust = (SignCust) this.signCustDAO.findByPk(this.signRuleReg
				.getSignCustId());
//		// 返利列表
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("cardBranch", getSessionUser().getBranchNo());

		String[] statusArray = new String[]{RebateRuleState.NORMAL.getValue(), RebateRuleState.USED.getValue()};
		params.put("statusArray", statusArray);

		this.rebateRuleList = this.rebateRuleDAO.findRebateRule(params);
		
		this.derateTypeList = DerateType.getAll();
		return MODIFY;
	}

	// 修改
	public String modify() throws Exception {
		this.saleCardRuleService.modifySignRuleReg(this.signRuleReg, this
				.getSessionUser());

		String msg = LogUtils.r("修改签单规则ID为[{0}]的签单规则成功！", signRuleReg.getSignRuleId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/signCardMgr/signRuleReg/list.do", msg);
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {

		// this.signRuleRegService.deleteSignRuleReg(this.signRuleReg.getCardCustomerId(),
		// this.signRuleReg.getBinNo());

		this.addActionMessage("/signCardMgr/signRuleReg/list.do", "删除签单规则成功！");
		return SUCCESS;
	}

	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] signRuleRegIds = this.workflowService.getMyJob(
				Constants.WORKFLOW_SIGN_RULE_REG, this.getSessionUser());

		if (ArrayUtils.isEmpty(signRuleRegIds)) {
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", signRuleRegIds);
		this.page = this.signRuleRegDAO.findSignRuleReg(params, this
				.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception {
		// 签单规则登记簿明细
		this.signRuleReg = (SignRuleReg) this.signRuleRegDAO
				.findByPk(signRuleReg.getSignRuleId());
		// 购卡客户明细
		this.signCust = (SignCust) this.signCustDAO.findByPk(this.signRuleReg
				.getSignCustId());
		// 充值返利规则
		this.rebateRule = (RebateRule) this.rebateRuleDAO
				.findByPk(this.signRuleReg.getDepositRebateId());
		// 充值返利规则明细列表
		Map<String, Object> params = new HashMap<String, Object>();
		if (this.rebateRule != null) {
			params.put("rebateId", this.rebateRule.getRebateId());
			this.rebateRuleDetailList = this.rebateRuleDetailDAO
					.findRebateRuleDetail(params);
		}

		return DETAIL;
	}

	public SignRuleReg getSignRuleReg() {
		return signRuleReg;
	}

	public void setSignRuleReg(SignRuleReg signRuleReg) {
		this.signRuleReg = signRuleReg;
	}

	public SignCust getSignCust() {
		return signCust;
	}

	public void setSignCust(SignCust signCust) {
		this.signCust = signCust;
	}

	public List<SignCust> getSignCustList() {
		return signCustList;
	}

	public void setSignCustList(List<SignCust> signCustList) {
		this.signCustList = signCustList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getDerateTypeList() {
		return derateTypeList;
	}

	public void setDerateTypeList(List derateTypeList) {
		this.derateTypeList = derateTypeList;
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

	public void setRebateRuleDetailList(
			List<RebateRuleDetail> rebateRuleDetailList) {
		this.rebateRuleDetailList = rebateRuleDetailList;
	}

	public List<RebateRule> getRebateRuleList() {
		return rebateRuleList;
	}

	public void setRebateRuleList(List<RebateRule> rebateRuleList) {
		this.rebateRuleList = rebateRuleList;
	}

	public String getUsedLabel() {
		return usedLabel;
	}

	public void setUsedLabel(String usedLabel) {
		this.usedLabel = usedLabel;
	}

}
