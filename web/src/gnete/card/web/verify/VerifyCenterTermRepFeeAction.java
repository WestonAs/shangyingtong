package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CenterTermRepFeeMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CenterTermRepFeeMSet;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifyCenterTermRepFeeAction.java
 *
 * @description: 机具维护方运营核销[运营中心与机具维护方汇总月结算表]
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyCenterTermRepFeeAction extends BaseAction {
	
	@Autowired
	private CenterTermRepFeeMSetDAO centerTermRepFeeMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;
	
	private CenterTermRepFeeMSet mset;
	private Paginater page;
	
	private List chkStatusList;
	private List<BranchInfo> termList;
	
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
		if(mset!=null){
			params.put("termCode", mset.getTermCode());
			params.put("chkStatus", mset.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 登录用户为运营中心
		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
			termList = branchInfoDAO.findByType(BranchType.TERMINAL_MAINTAIN.getValue());
			params.put("termList", termList);
		}
		// 登录用户为机具维护方
		else if (getLoginRoleType().equals(RoleType.TERMINAL_MAINTAIN.getValue())) {
			BranchInfo term = (BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo());
			termList = new LinkedList<BranchInfo>();
			termList.add(term);
			params.put("termList", termList);
		} else {
			throw new BizException("没有权限查询机具维护方分润核销记录");
		}
		this.page = centerTermRepFeeMSetDAO.findCenterTermRepFeeMSet(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()))) {
			throw new BizException("没有权限进行机具维护方分润核销操作！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("branchCode",mset.getBranchCode());
		map.put("feeMonth", mset.getFeeMonth());
		map.put("termCode", mset.getTermCode());
		this.mset = (CenterTermRepFeeMSet)this.centerTermRepFeeMSetDAO.findByPk(map);
		
		// 应付金额 = 上次金额 + 中心实际分润金额
		BigDecimal shouldPayAmount = AmountUtil.add(mset.getLastFee(), mset.getPayAmt());
	
		// 剩余核销金额 = 应付金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset.getRecvAmt());
		remainfeeAmt = remainFee.toString();
		
		BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(mset.getBranchCode());
		this.mset.setBranchName(card.getBranchName());
		BranchInfo term = (BranchInfo) branchInfoDAO.findByPk(mset.getTermCode());
		this.mset.setTermName(term.getBranchName());
		this.mset.setRecvAmt(remainFee);

		return "verify";
	}
	
	public String verify() throws Exception {
		
		this.verifyService.verifyCenterTermRepFee(mset, verifyType, getSessionUser());
		
		String msg = LogUtils.r("机具维护方[{0}]分润核销成功", mset.getTermCode());
		
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/verify/verifyCenterTermRepFee/list.do", msg);
		return SUCCESS;
	}

	public CenterTermRepFeeMSet getMset() {
		return mset;
	}

	public void setMset(CenterTermRepFeeMSet mset) {
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

	public List<BranchInfo> getTermList() {
		return termList;
	}

	public void setTermList(List<BranchInfo> termList) {
		this.termList = termList;
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
