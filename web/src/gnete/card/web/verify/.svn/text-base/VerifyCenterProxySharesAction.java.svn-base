package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CenterProxySharesMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CenterProxySharesMSet;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifyCenterProxySharesAction.java
 *
 * @description: 运营代理商分润核销[运营中心与运营机构代理商分润月结算表]
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyCenterProxySharesAction extends BaseAction {
	
	@Autowired
	private CenterProxySharesMSetDAO centerProxySharesMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;
	
	private CenterProxySharesMSet mset;
	private Paginater page;
	
	private List chkStatusList;
	private List<BranchInfo> branchList;
	
	private String startDate;
	private String endDate;
	
	private boolean showCardBranch;
	
	// 剩余核销金额
	private String remainfeeAmt;
	// 核销类型
	private String verifyType;
	
	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(mset!=null){
			params.put("branchCode",mset.getBranchCode());
			params.put("chkStatus", mset.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 运营中心登录
		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
			showCardBranch = true;
			branchList = getMyCardBranch();
			params.put("branchList", branchList);
		// 登录用户为运营机构代理
		} else if (getLoginRoleType().equals(RoleType.AGENT.getValue())) {
			showCardBranch = false;
			branchList = getMyCardBranch();
			params.put("proxyId", getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询运营代理商分润核销记录");
		}
		this.page = centerProxySharesMSetDAO.findCenterProxySharesMSet(params, this.getPageNumber(), this.getPageSize());
//		List<BranchType> branchTypeList = new LinkedList<BranchType>();
//		branchTypeList.add(BranchType.CARD);
//		branchList = branchInfoDAO.findByTypes(branchTypeList);
//		this.log("查询运营代理商分润核销成功", UserLogType.SEARCH);
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()))) {
			throw new BizException("没有权限进行运营代理商分润核销操作！");
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("feeMonth", mset.getFeeMonth());
		map.put("branchCode",mset.getBranchCode());
		map.put("proxyId",mset.getProxyId());
		this.mset = (CenterProxySharesMSet)this.centerProxySharesMSetDAO.findByPk(map);
		
		// 应付金额 = 上次金额 + 中心实际分润金额
		BigDecimal shouldPayAmount = AmountUtil.add(mset.getLastFee(), mset.getPayAmt());
	
		// 剩余核销金额 = 应付金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset.getRecvAmt());
		remainfeeAmt = remainFee.toString();
		
		BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(mset.getBranchCode());
		this.mset.setBranchName(info.getBranchName());
		this.mset.setRecvAmt(remainFee);

		return "verify";
	}
	
	public String verify() throws Exception {
		
		this.verifyService.verifyCenterProxyShares(mset, verifyType, getSessionUser());
		
		String msg = LogUtils.r("运营代理商[{0}]分润核销成功", mset.getProxyId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/verify/verifyCenterProxyShares/list.do", msg);
		return SUCCESS;
	}

	public CenterProxySharesMSet getMset() {
		return mset;
	}

	public void setMset(CenterProxySharesMSet mset) {
		this.mset = mset;
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
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

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getRemainfeeAmt() {
		return remainfeeAmt;
	}

	public void setRemainfeeAmt(String remainfeeAmt) {
		this.remainfeeAmt = remainfeeAmt;
	}
}
