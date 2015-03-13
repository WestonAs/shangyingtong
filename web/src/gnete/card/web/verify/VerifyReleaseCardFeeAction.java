package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ReleaseCardFeeMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReleaseCardFeeMSet;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifyReleaseCardFeeAction.java
 * 
 * @description: 发卡机构手续费核销[运营中心与发卡机构手续费汇总月结算]
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyReleaseCardFeeAction extends BaseAction {

	@Autowired
	private ReleaseCardFeeMSetDAO releaseCardFeeMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private ReleaseCardFeeMSet mset;
	private Paginater page;

	private List chkStatusList;
	private List<BranchInfo> branchList;
	private List<BranchInfo> manageList;

	private String startDate;
	private String endDate;
	
	// 剩余核销金额
	private String remainfeeAmt;
	// 核销类型
	private String verifyType;

	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (mset != null) {
			params.put("branchCode", mset.getBranchCode());
			params.put("chlCode", mset.getChlCode());
			params.put("chkStatus", mset.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 当前登录用户为发卡机构
		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			BranchInfo info = (BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo());
			branchList = new ArrayList<BranchInfo>();
			branchList.add(info);
			BranchInfo manage = (BranchInfo) branchInfoDAO.findByPk(info
					.getParent());
			manageList = new ArrayList<BranchInfo>();
			manageList.add(manage);
			params.put("branchList", branchList);
//			params.put("manageList", manageList);
		}
		// 当前登录用户为运营中心或运营中心部门时
		else if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
			branchList = getMyCardBranch();
			BranchInfo manage = (BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo());
			manageList = new ArrayList<BranchInfo>();
			manageList.add(manage);
			params.put("branchList", branchList);
//			params.put("manageList", manageList);
		} else {
			throw new BizException("没有权限查询发卡机构手续费核销");
		}
		this.page = releaseCardFeeMSetDAO.findReleaseCardFeeMSet(params, this
				.getPageNumber(), this.getPageSize());
		// List<BranchType> branchTypeList = new LinkedList<BranchType>();
		// branchTypeList.add(BranchType.CARD);
		// branchList = branchInfoDAO.findByTypes(branchTypeList);
		// this.log("查询运营中心发卡机构手续费核销成功", UserLogType.SEARCH);
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()))) {
			throw new BizException("没有权限进行发卡机构手续费核销操作！");
		}

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("branchCode", mset.getBranchCode());
		map.put("feeMonth", mset.getFeeMonth());
		mset = (ReleaseCardFeeMSet) releaseCardFeeMSetDAO.findByPk(map);
		
		// 应付金额 = 上期结转手续费 + 本期手续费
		BigDecimal shouldPayAmount = AmountUtil.add(mset.getLastFee(), mset.getFeeAmt());
		
		// 剩余核销金额 = 应收金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset.getRecvAmt());
		remainfeeAmt = remainFee.toString();
		
		BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(mset.getBranchCode());
		this.mset.setBranchName(info.getBranchName());
		this.mset.setRecvAmt(remainFee);

		return "verify";
	}

	public String verify() throws Exception {
		this.verifyService.verifyReleaseCardFee(mset, verifyType, getSessionUser());
		
		String msg = LogUtils.r("运营中心发卡机构[{0}]手续费核销成功", mset.getBranchCode());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/verify/verifyReleaseCardFee/list.do", msg);
		return SUCCESS;
	}

	public ReleaseCardFeeMSet getMset() {
		return mset;
	}

	public void setMset(ReleaseCardFeeMSet mset) {
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

	public List<BranchInfo> getManageList() {
		return manageList;
	}

	public void setManageList(List<BranchInfo> manageList) {
		this.manageList = manageList;
	}

	public String getRemainfeeAmt() {
		return remainfeeAmt;
	}

	public void setRemainfeeAmt(String remainfeeAmt) {
		this.remainfeeAmt = remainfeeAmt;
	}

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}
}
