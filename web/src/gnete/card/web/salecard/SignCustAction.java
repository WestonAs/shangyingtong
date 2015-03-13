package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.SignCustDAO;
import gnete.card.entity.SignCust;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRuleService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SignCustAction.java
 * 
 * @description: 签单客户管理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: BenYan
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-8-14
 */
public class SignCustAction extends BaseAction {

	@Autowired
	private SignCustDAO signCustDAO;
	@Autowired
	private SaleCardRuleService saleCardRuleService;

	private SignCust signCust;
	private Paginater page;

	private List statusList;
	private List credTypeList;

	private Long signCustId;
	private String usedLabel;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {

		this.statusList = CommonState.getAll();
		this.setUsedLabel(CommonState.USED.getValue());

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
		if (signCust != null) {
			params.put("signCustId", signCust.getSignCustId());
			params.put("signCustName", MatchMode.ANYWHERE
					.toMatchString(signCust.getSignCustName()));
			params.put("status", signCust.getStatus());
		}
		this.page = this.signCustDAO.findSignCust(params, this.getPageNumber(),
				this.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		signCust = (SignCust) signCustDAO.findByPk(signCust.getSignCustId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if (!getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("只有发卡机构才能新增签单客户");
		}
		// this.rebateTypeList = RebateType.ALL.values();
		this.credTypeList = CertType.getAll();

		return ADD;
	}

	// 新增
	public String add() throws Exception {
		this.saleCardRuleService.addSignCust(this.signCust, this
				.getSessionUser());

		String msg = LogUtils.r("添加签单卡客户ID为[{0}]的签单客户成功！", signCust
				.getSignCustId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/signCardMgr/signCust/list.do", msg);
		return SUCCESS;
	}

	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		if (!getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("只有发卡机构才能修改签单客户信息");
		}
		// this.rebateTypeList = RebateType.ALL.values();
		this.credTypeList = CertType.getAll();
		signCust = (SignCust) signCustDAO.findByPk(signCust.getSignCustId());

		return MODIFY;
	}

	// 修改
	public String modify() throws Exception {
		this.saleCardRuleService.modifySignCust(signCust, getSessionUser());

		String msg = LogUtils.r("修改签单卡客户ID为[{0}]的签单客户成功！", signCust
				.getSignCustId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/signCardMgr/signCust/list.do", msg);
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		if (!getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("只有发卡机构才能删除签单客户信息");
		}
		this.saleCardRuleService.deleteSignCust(this.getSignCustId());

		String msg = LogUtils.r("删除签单卡客户ID为[{0}]的签单客户成功！", signCust
				.getSignCustId());
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/signCardMgr/signCust/list.do", msg);
		return SUCCESS;
	}

	public SignCust getSignCust() {
		return signCust;
	}

	public void setSignCust(SignCust signCust) {
		this.signCust = signCust;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Long getSignCustId() {
		return signCustId;
	}

	public void setSignCustId(Long signCustId) {
		this.signCustId = signCustId;
	}

	public List getCredTypeList() {
		return credTypeList;
	}

	public void setCredTypeList(List credTypeList) {
		this.credTypeList = credTypeList;
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

}
