package gnete.card.web.verify;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchFeeMSetDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchFeeMSet;
import gnete.card.entity.MerchInfo;
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
 * @File: VerifyMerchFeeAction.java
 * 
 * @description: 商户手续费核销[发卡机构与商户手续费月结算表]
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifyMerchFeeAction extends BaseAction {

	@Autowired
	private MerchFeeMSetDAO merchFeeMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private MerchFeeMSet mset;

	private Paginater page;

	private List<BranchInfo> branchList;
	private List<MerchInfo> merchList;
	private List chkStatusList;

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
			params.put("merchId", mset.getMerchId());
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
			throw new BizException("没有权限查询商户手续费核销记录！");
		}
		this.page = merchFeeMSetDAO.findMerchFeeMSet(params, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			throw new BizException("没有权限进行商户手续费核销操作！");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("branchCode", mset.getBranchCode());
		map.put("feeMonth", mset.getFeeMonth());
		map.put("merchId", mset.getMerchId());
		this.mset = (MerchFeeMSet) this.merchFeeMSetDAO.findByPk(map);

		// 应付金额 = 上期结转 + 商户应付
		BigDecimal shouldPayAmount = AmountUtil.add(mset.getLastFee(), mset
				.getFeeAmt());

		// 剩余核销金额 = 应付金额 - 实收手续费金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, mset
				.getRecvAmt());
		remainfeeAmt = remainFee.toString();

		BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(mset
				.getMerchProxy());
		MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(mset
				.getMerchId());
		this.mset.setProxyName(info.getBranchName());
		this.mset.setMerchName(merchInfo.getMerchName());
		this.mset.setRecvAmt(remainFee);
		
		return "verify";
	}

	public String verify() throws Exception {

		this.verifyService.verifyMerchFee(mset, verifyType, getSessionUser());

		String msg = LogUtils.r("核销商户代理分润核销[{0}]成功", mset.getBranchCode());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/verify/verifyMerchFee/list.do", msg);
		return SUCCESS;
	}

	public MerchFeeMSet getMset() {
		return mset;
	}

	public void setMset(MerchFeeMSet mset) {
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
