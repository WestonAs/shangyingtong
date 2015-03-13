package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchTransDSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchTransDSet;
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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifyCardBranchAction.java
 * 
 * @description: 发卡机构售卡核销处理[发卡机构、商户交易结算日结算表],售卡充值核销
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyCardBranchAction extends BaseAction {

	@Autowired
	private MerchTransDSetDAO merchTransDSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private MerchTransDSet mset;

	private String startDate;
	private String endDate;

	private Paginater page;
	private List<BranchInfo> branchList;
	private List<BranchInfo> saleList;
	private List jzFlagList;
	private List chkStatusList;

	// 剩余核销金额
	private String remainfeeAmt;
	// 核销类型
	private String verifyType;

	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (mset != null) {
			params.put("payCode", mset.getPayCode()); // 付款方为售卡机构
			params.put("recvCode", mset.getRecvCode());// 收款方为发卡机构
			params.put("chkStatus", mset.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 当前登录用户为发卡机构
		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo());
			branchList = new ArrayList<BranchInfo>();
			branchList.add(info);
			saleList = getMySellBranch();
			params.put("branchList", branchList);
			params.put("saleList", saleList);
			// 当前登录用户为售卡代理
		} else if (this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())) {
			branchList = getMyCardBranch();
			saleList = new ArrayList<BranchInfo>();
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo());
			saleList.add(info);
			if (CollectionUtils.isEmpty(branchList)) {
				branchList.add(new BranchInfo());
			}
			params.put("branchList", branchList);
			params.put("saleList", saleList);
		} else {
			throw new BizException("没有权限查询发卡机构售卡核销记录！");
		}
		page = merchTransDSetDAO.findCardSale(params, getPageNumber(), getPageSize());
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("没有权限进行发卡机构售卡核销操作！");
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

		BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(mset.getPayCode());
		this.mset.setRecvAmt(remainFee);
		this.mset.setPayName(info.getBranchName());
		return "verify";
	}

	public String verify() throws Exception {

		this.verifyService.verifyCardBranch(mset, verifyType, getSessionUser());

		String msg = LogUtils.r("发卡机构售卡核销[{0}]成功", mset.getPayCode());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/verify/verifyCardBranch/list.do", msg);
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<BranchInfo> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<BranchInfo> saleList) {
		this.saleList = saleList;
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
