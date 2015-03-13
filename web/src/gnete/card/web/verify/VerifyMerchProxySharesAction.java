package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchProxySharesMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchProxySharesMSet;
import gnete.card.entity.flag.VerifyJzFlag;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.ProxyType;
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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifyMerchProxySharesAction.java
 * 
 * @description: 商户代理分润核销[发卡机构与商户手续费月结算表]
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyMerchProxySharesAction extends BaseAction {

	@Autowired
	private MerchProxySharesMSetDAO merchProxySharesMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private MerchProxySharesMSet mset;
	private Paginater page;

	private List<BranchInfo> branchList;
	private List<BranchInfo> saleList; // 商户代理
	private List chkStatusList;
	private List jzFlagList;

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
			params.put("merchProxy", mset.getMerchProxy());
			params.put("chkStatus", mset.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 当前登录用户为发卡机构
		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo());
			branchList = new ArrayList<BranchInfo>();
			branchList.add(branchInfo);
			saleList = branchInfoDAO.findCardProxy(getSessionUser()
					.getBranchNo(), ProxyType.MERCHANT);
			params.put("branchList", branchList);
			params.put("saleList", saleList);
			// 当前登录用户为商户代理
		} else if (getLoginRoleType().equals(RoleType.CARD_MERCHANT.getValue())) {
			branchList = getMyCardBranch();
			saleList = new ArrayList<BranchInfo>();
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo());
			saleList.add(branchInfo);
			if (CollectionUtils.isEmpty(branchList)) {
				branchList.add(new BranchInfo());
			}
			params.put("branchList", branchList);
			params.put("saleList", saleList);
		} else {
			throw new BizException("没有权限查询商户代理分润核销记录！");
		}
		this.page = merchProxySharesMSetDAO.findMerchProxySharesMSet(params,
				this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("没有权限进行商户代理分润核销操作！");
		}
		jzFlagList = VerifyJzFlag.getList();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("branchCode", mset.getBranchCode());
		map.put("feeMonth", mset.getFeeMonth());
		map.put("merchId", mset.getMerchId());
		map.put("merchProxy", mset.getMerchProxy());
		mset = (MerchProxySharesMSet) merchProxySharesMSetDAO.findByPk(map);

		// 应付金额 = 上期结转 + 发卡机构应付
		BigDecimal shouldPayAmount = AmountUtil.add(mset.getLastFee(), mset.getFeeAmt());

		// 剩余核销金额 = 应付金额 - 实收手续费金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset.getRecvAmt());
		remainfeeAmt = remainFee.toString();
		
		BranchInfo cardBranch = (BranchInfo) branchInfoDAO.findByPk(mset.getBranchCode());
		BranchInfo proxtBranch = (BranchInfo) branchInfoDAO.findByPk(mset.getMerchProxy());
		MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(mset.getMerchId());
		mset.setBranchName(cardBranch.getBranchName());
		mset.setProxyName(proxtBranch.getBranchName());
		mset.setMerchName(merchInfo.getMerchName());
		mset.setRecvAmt(remainFee);

		return "verify";
	}

	public String verify() throws Exception {

		// 目前先暂时改为付钱只能等额核销
		this.verifyService.verifyMerchProxyShares(mset, VerifyType.EQUA.getValue(), getSessionUser());

		String msg = LogUtils.r("核销商户代理分润核销[{0}]成功", mset.getBranchCode());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/verify/verifyMerchProxyShares/list.do", msg);
		return SUCCESS;
	}

	public MerchProxySharesMSet getMset() {
		return mset;
	}

	public void setMset(MerchProxySharesMSet mset) {
		this.mset = mset;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public List getChkStatusList() {
		return chkStatusList;
	}

	public void setChkStatusList(List chkStatusList) {
		this.chkStatusList = chkStatusList;
	}

	public List getJzFlagList() {
		return jzFlagList;
	}

	public void setJzFlagList(List jzFlagList) {
		this.jzFlagList = jzFlagList;
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
