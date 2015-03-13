package gnete.card.web.receive;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.AppRegDAO;
import gnete.card.entity.AppReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.flag.ReceiveFlag;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardReceiveService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardWithdrawAction.java
 * 
 * @description: 返库相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-23
 */
public class CardWithdrawAction extends BaseAction {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardReceiveService cardReceiveService;

	private AppReg appReg; // 领卡登记表

	private List statusList;
	private List flagList;
	private List<BranchInfo> branchList;

	private Paginater page;

	private String startDate;
	private String endDate;
	
	private List<NameValuePair> appOrgList;

	@Override
	public String execute() throws Exception {
		statusList = CheckState.getAll();
		flagList = ReceiveFlag.getAll();
		// 当前登录用户所属机构可以管理的所有机构
		branchList = getMyCardBranch();

		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("flag", ReceiveFlag.WITHDRAW.getValue());
		if (appReg != null) {
			params.put("cardIssuer", MatchMode.ANYWHERE.toMatchString(appReg
					.getCardIssuer()));
			params.put("status", appReg.getStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 运营中心或运营中心部门
		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
		}
		// 登陆用户为分支机构
		else if (getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			params.put("fenzhiCode", this.getLoginBranchCode());
		}
		// 登陆用户为发卡机构
		else if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			params.put("cardBranch", getSessionUser().getBranchNo());
		}
		// 登陆用户为发卡机构网点
		else if (getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())) {
			params.put("appOrgId", this.getSessionUser().getDeptId());
		}
		// 登陆用户为售卡代理
		else if (getLoginRoleType().equals(RoleType.CARD_SELL.getValue())) {
			params.put("appOrgId", this.getSessionUser().getBranchNo());
		}
		// 登陆用户为商户
		else if (getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询返库记录");
		}
		
//		appOrgList = this.getMyReciveIssuer();// 得到我所管理的领卡机构，领卡部门编号
//		if (CollectionUtils.isEmpty(appOrgList)) {
//			appOrgList.add(new NameValuePair());
//		}
		
		page = appRegDAO.findAppRegPage(params, getPageNumber(), getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		appReg = (AppReg) appRegDAO.findByPk(appReg.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		return ADD;
	}

	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 只有发卡机构才能做返库审核
		if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			params.put("cardIssuer", getSessionUser().getBranchNo());
		}
		// 售卡代理做领卡审核，对从自己这里领走的卡做审核
		else if (RoleType.CARD_SELL.getValue().equals(super.getLoginRoleType())) {
		} else {
			throw new BizException("没有权限做返库审核操作。");
		}
		params.put("cardSectorId", getSessionUser().getBranchNo());
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(WorkflowConstants.CARD_WITHDRAW, getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		params.put("ids", ids);
		params.put("flag", ReceiveFlag.WITHDRAW.getValue());
		page = appRegDAO.findAppRegPage(params, getPageNumber(), getPageSize());
		return CHECK_LIST;
	}
	
	public String checkDetail() throws Exception {
		appReg = (AppReg) appRegDAO.findByPk(appReg.getId());
		return DETAIL;
	}

	/**
	 * 返库记录新建
	 */
	public String add() throws Exception {
		AppReg reg = cardReceiveService.addCardWithdraw(appReg, getSessionUser());

		String msg = LogUtils.r("ID为[{0}]的返库卡申请已经提交。", reg.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardReceive/withdraw/list.do", msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public AppReg getAppReg() {
		return appReg;
	}

	public void setAppReg(AppReg appReg) {
		this.appReg = appReg;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List getFlagList() {
		return flagList;
	}

	public void setFlagList(List flagList) {
		this.flagList = flagList;
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<NameValuePair> getAppOrgList() {
		return appOrgList;
	}

	public void setAppOrgList(List<NameValuePair> appOrgList) {
		this.appOrgList = appOrgList;
	}
}
