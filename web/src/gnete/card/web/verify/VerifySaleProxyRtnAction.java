package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.SaleProxyRtnMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.SaleProxyRtnMSet;
import gnete.card.entity.flag.VerifyJzFlag;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.VerifyType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifySaleProxyRtnAction.java
 * 
 * @description: 售卡代理商返利核销[发卡机构与售卡代理商返利月结算表]
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifySaleProxyRtnAction extends BaseAction {

	@Autowired
	private SaleProxyRtnMSetDAO saleProxyRtnMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private SaleProxyRtnMSet mset;

	private String startDate;
	private String endDate;

	private Paginater page;

	private List jzFlagList;
	private List chkStatusList;
	private List<BranchInfo> branchList;
	private List<BranchInfo> saleList;

	// 剩余核销金额
	private String remainfeeAmt;
	// 核销类型
	private String verifyType;

	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (mset != null) {
			params.put("orgId", mset.getOrgId());
			params.put("proxyId", mset.getProxyId());
			params.put("chkStatus", mset.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 当前登录用户为发卡机构或售卡代理时
		if (getLoginRoleType().equals(RoleType.CARD.getValue())
				|| getLoginRoleType().equals(RoleType.CARD_SELL.getValue())) {
			branchList = getMyCardBranch();
			saleList = getMySellBranch();
			if (CollectionUtils.isEmpty(branchList)) {
				branchList.add(new BranchInfo());
			}
			if (CollectionUtils.isEmpty(saleList)) {
				saleList.add(new BranchInfo());
			}
			params.put("branchList", branchList);
			params.put("saleList", saleList);
		} else {
			throw new BizException("没有权限查询售卡代理商返利核销记录！");
		}
		this.page = saleProxyRtnMSetDAO.findSaleProxyRtnMSet(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("没有权限进行售卡代理商返利核销操作！");
		}
		jzFlagList = VerifyJzFlag.getList();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("feeMonth", mset.getFeeMonth());
		map.put("orgId", mset.getOrgId());
		map.put("proxyId", mset.getProxyId());
		map.put("curCode", mset.getCurCode());
		this.mset = (SaleProxyRtnMSet) this.saleProxyRtnMSetDAO.findByPk(map);

		// 应付金额 = 上次金额 + 本期金额
		BigDecimal shouldPayAmount = AmountUtil.add(mset.getLastFee(), mset
				.getFeeAmt());

		// 剩余核销金额 = 应付金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset
				.getRecvAmt());
		remainfeeAmt = remainFee.toString();

		BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(this.mset
				.getProxyId());
		this.mset.setProxyName(info.getBranchName());
		return "verify";
	}

	public String verify() throws Exception {

		// 核销类型目前改为等额核销
		this.verifyService.verifySaleProxyRtn(mset, VerifyType.EQUA.getValue(),
				getSessionUser());

		String msg = LogUtils.r("核销售卡代理商返利核销[{0}]成功", mset.getProxyId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/verify/verifySaleProxyRtn/list.do", msg);
		return SUCCESS;
	}

	public SaleProxyRtnMSet getMset() {
		return mset;
	}

	public void setMset(SaleProxyRtnMSet mset) {
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

	public List<BranchInfo> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<BranchInfo> saleList) {
		this.saleList = saleList;
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
