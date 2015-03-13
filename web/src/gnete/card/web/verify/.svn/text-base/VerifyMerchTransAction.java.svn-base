package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchTransDSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchTransDSet;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.VerifyType;
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
 * @File: VerifyMerchTransAction.java
 * 
 * @description: 商户交易核销处理[发卡机构、商户交易结算日结算表]
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyMerchTransAction extends BaseAction {

	@Autowired
	private MerchTransDSetDAO merchTransDSetDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private MerchTransDSet mset;
	private List jzFlagList;
	private List transTypeList;

	private List<BranchInfo> branchList;
	private List<MerchInfo> merchList;
	private List chkStatusList;

	private String startDate;
	private String endDate;

	private Paginater page;
	
	// 剩余核销金额
	private String remainfeeAmt;
	// 核销类型
	private String verifyType;

	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (mset != null) {
			params.put("payCode", mset.getPayCode());
			params.put("recvCode", mset.getRecvCode());
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
			merchList = getMyFranchMerch(getSessionUser().getBranchNo());
			params.put("branchList", branchList);
			params.put("merchList", merchList);
			// 当前登录用户为商户
		} else if (this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			branchList = branchInfoDAO.findByMerch(getSessionUser()
					.getMerchantNo());
			merchList = new ArrayList<MerchInfo>();
			MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(getSessionUser()
					.getMerchantNo());
			merchList.add(info);
			params.put("branchList", branchList);
			params.put("merchList", merchList);
		} else {
			throw new BizException("没有权限查询商户交易核销记录！");
		}
		this.page = this.merchTransDSetDAO.findMerchTrans(params, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("没有权限进行商户交易核销操作！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("recvCode", mset.getRecvCode());
		map.put("payCode", mset.getPayCode());
		map.put("setDate", mset.getSetDate());
		map.put("transType", mset.getTransType());
		map.put("curCode", mset.getCurCode());
		this.mset = (MerchTransDSet) this.merchTransDSetDAO.findByPk(map);
		
		// 应付金额 = 上次金额 + 本期金额 - 手续费
		BigDecimal shouldPayAmount = AmountUtil.subtract(AmountUtil.add(mset
				.getLastFee(), mset.getFeeAmt()), mset.getFeeAmount());
		
		// 剩余核销金额 = 应付金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset.getRecvAmt());
		remainfeeAmt = remainFee.toString();
		MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(mset.getRecvCode());
		
		this.mset.setRecvAmt(remainFee);
		this.mset.setRecvName(info.getMerchName());
		return "verify";
	}

	public String verify() throws Exception {

		// 目前发卡机构付钱的情况只允许为等额核销
		this.verifyService.verifyMerchTrans(mset, VerifyType.EQUA.getValue(), getSessionUser());
		//this.verifyService.verifyMerchTrans(mset, verifyType, getSessionUser());

		String msg = LogUtils.r("商户[{0}]交易核销成功", mset.getRecvCode());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/verify/verifyMerchTrans/list.do", msg);
		return SUCCESS;
	}

	public MerchTransDSet getMset() {
		return mset;
	}

	public void setMset(MerchTransDSet mset) {
		this.mset = mset;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getJzFlagList() {
		return jzFlagList;
	}

	public void setJzFlagList(List jzFlagList) {
		this.jzFlagList = jzFlagList;
	}

	public List getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List transTypeList) {
		this.transTypeList = transTypeList;
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

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public List getChkStatusList() {
		return chkStatusList;
	}

	public void setChkStatusList(List chkStatusList) {
		this.chkStatusList = chkStatusList;
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
