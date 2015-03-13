package gnete.card.web.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ChlFeeMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ChlFeeMSet;
import gnete.card.entity.ChlFeeMSetKey;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

public class VerifyChlFeeAction extends BaseAction{

	@Autowired
	private ChlFeeMSetDAO chlFeeMSetDAO;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private ChlFeeMSet chlFeeMSet;
	private Paginater page;
	private List chkStatusList;
	private String startDate;
	private String endDate;
	private List<BranchInfo> chlList;
	private boolean showFenzhi = false;
	private String remainfeeAmt; // 剩余核销金额
	
	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (chlFeeMSet != null) {
			params.put("chlName", MatchMode.ANYWHERE.toMatchString(chlFeeMSet.getChlName()));
			params.put("chkStatus", chlFeeMSet.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 分支机构
		if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			this.chlList = new ArrayList<BranchInfo>();
			this.chlList.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			this.showFenzhi = true;
			params.put("chlCode", this.getSessionUser().getBranchNo());
		}
		// 当前登录用户为运营中心或运营中心部门时
		else if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
		} else {
			throw new BizException("没有权限查询分支机构平台运营手续费核销");
		}
		this.page = this.chlFeeMSetDAO.findChlFeeMSet(params, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())&&
			!this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限进行分支机构平台运营手续费核销操作！");
		}

		ChlFeeMSetKey key = new ChlFeeMSetKey();
		key.setChlCode(this.chlFeeMSet.getChlCode());
		key.setFeeMonth(this.chlFeeMSet.getFeeMonth());
		this.chlFeeMSet = (ChlFeeMSet) this.chlFeeMSetDAO.findByPk(key);
		
		// 应付金额 = 上期结转手续费 + 本期手续费
		BigDecimal shouldPayAmount = AmountUtil.add(chlFeeMSet.getLastFee(), chlFeeMSet.getChlFeeAmt());
		
		// 剩余核销金额 = 应收金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, chlFeeMSet.getRecvAmt());
		this.remainfeeAmt = remainFee.toString();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(this.chlFeeMSet.getChlCode());
		this.chlFeeMSet.setChlName(branch.getBranchName());
		
		return "verify";
	}
	
	public String verify() throws Exception {
		this.verifyService.verifyChlFee(this.chlFeeMSet, this.getSessionUserCode());
		
		String msg = LogUtils.r("运营中心分支机构[{0}]手续费核销成功", chlFeeMSet.getChlCode());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/verify/verifyChlFee/list.do", msg);
		return SUCCESS;
	}

	public ChlFeeMSet getChlFeeMSet() {
		return chlFeeMSet;
	}

	public void setChlFeeMSet(ChlFeeMSet chlFeeMSet) {
		this.chlFeeMSet = chlFeeMSet;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getChkStatusList() {
		return chkStatusList;
	}

	public void setChkStatusList(List chkStatusList) {
		this.chkStatusList = chkStatusList;
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

	public List<BranchInfo> getChlList() {
		return chlList;
	}

	public void setChlList(List<BranchInfo> chlList) {
		this.chlList = chlList;
	}

	public boolean isShowFenzhi() {
		return showFenzhi;
	}

	public void setShowFenzhi(boolean showFenzhi) {
		this.showFenzhi = showFenzhi;
	}

	public String getRemainfeeAmt() {
		return remainfeeAmt;
	}

	public void setRemainfeeAmt(String remainfeeAmt) {
		this.remainfeeAmt = remainfeeAmt;
	}

}
