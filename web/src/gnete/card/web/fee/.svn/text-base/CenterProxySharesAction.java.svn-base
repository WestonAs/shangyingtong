package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CenterProxySharesDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CenterProxyShares;
import gnete.card.entity.CenterProxySharesKey;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.CenterProxySharesFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SetModeType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CenterProxySharesService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 营运代理机构分润
 * 
 */
public class CenterProxySharesAction extends BaseAction {

	@Autowired
	private CenterProxySharesDAO centerProxySharesDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CenterProxySharesService centerProxySharesService;

	private Paginater page;

	private CenterProxyShares centerProxyShares;
	private CenterProxySharesKey key;
	private Collection costCycleList;

	private boolean showProxy = false;

	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	private String branchName;

	private List<BranchInfo> branchList;
	private List<Type> feeTypeList;
	private String updateUlmoney;

	@Override
	public String execute() throws Exception {
		costCycleList = CostCycleType.getList();
		feeTypeList = CenterProxySharesFeeType.getFeeType();

		Map param = new HashMap();
		if (this.getLoginRoleType().equals(RoleType.AGENT.getValue())) {
			showProxy = true;
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this
					.getSessionUser().getBranchNo()));
			param.put("proxyId", branchList.get(0).getBranchCode());
		} else if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			param.put("branchCode", this.getSessionUser().getBranchNo());
			Map params = new HashMap();
			params.put("parent", this.getSessionUser().getBranchNo());
			params.put("type", BranchType.AGENT.getValue());
			branchList = this.branchInfoDAO.findAgent(params);
		} else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(
						RoleType.CENTER_DEPT.getValue())) {
			branchList = this.branchInfoDAO.findByType(RoleType.AGENT
					.getValue());

		} else {
			throw new BizException("没有权限查询运营机构代理商分润参数！");
		}

		if (centerProxyShares != null) {
			param.put("proxyId", centerProxyShares.getProxyId());
			param.put("proxyName", MatchMode.ANYWHERE.toMatchString(centerProxyShares.getProxyName()));
			param.put("feeType", centerProxyShares.getFeeType());
			param.put("costCycle", centerProxyShares.getCostCycle());
		}

		page = this.centerProxySharesDAO.findCenterProxyShares(param, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showAdd() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			throw new BizException("只有分支机构有权限设置运营机构代理商分润参数！");
		}
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		Assert.notNull(branchInfo, "登录机构机构信息为空。");
		this.branchName = branchInfo.getBranchName();
		costCycleList = CostCycleType.getMonth();
		feeTypeList = CenterProxySharesFeeType.getFeeType();

		Map param = new HashMap();
		param.put("type", RoleType.AGENT.getValue());
		param.put("parent", this.getSessionUser().getBranchNo());
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(
						RoleType.CENTER_DEPT.getValue())) {
			param.put("setMode", SetModeType.SHARE.getValue());
		} else if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			param.put("setMode", SetModeType.COST.getValue());
		}
		branchList = this.branchInfoDAO.findAgent(param);
		return ADD;
	}

	public String add() throws Exception {
		
		this.centerProxyShares.setBranchCode(this.getSessionUser().getBranchNo());
		
		// 如果是分段的
		List<CenterProxyShares> feeList = new ArrayList<CenterProxyShares>();
		if (centerProxyShares.getFeeType().equals(
				CenterProxySharesFeeType.TRADEMONEY.getValue())
				|| centerProxyShares.getFeeType().equals(
						CenterProxySharesFeeType.SSUM.getValue())) {

			for (int i = 0; i < ulimit.length; i++) {
				CenterProxyShares fee = new CenterProxyShares();
				fee = new CenterProxyShares();
				formToBo(fee, this.centerProxyShares);
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				
				feeList.add(fee);
			}
		} else {
			CenterProxyShares fee = new CenterProxyShares();
			fee = new CenterProxyShares();
			formToBo(fee, this.centerProxyShares);
			fee.setUlMoney(Constants.FEE_MAXACCOUNT);
			fee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			feeList.add(fee);
		}

		// 营运中心及其部门设置
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())|| 
				this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			for (int i = 0; i < feeList.size(); i++) {
				feeList.get(i).setBranchCode(Constants.CENTER);
			}
		} else if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			for (int i = 0; i < feeList.size(); i++) {
				feeList.get(i).setBranchCode(this.getSessionUser().getBranchNo());
			}
		}

		centerProxySharesService.addCenterProxyShares(feeList
				.toArray(new CenterProxyShares[feeList.size()]), this
				.getSessionUserCode());
		String msg = "添加运营机构与运营机构代理商分润参数[" + centerProxyShares.getProxyId()
				+ "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/centerProxyShares/list.do", msg);
		return SUCCESS;
	}

	private void formToBo(CenterProxyShares dest, CenterProxyShares src) {
		dest.setBranchCode(src.getBranchCode());
		dest.setProxyId(src.getProxyId());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
	}

	public String showModify() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			throw new BizException("只有分支机构有权限设置运营机构代理商分润参数！");
		}
	
		key.setUlMoney(AmountUtil.format(key.getUlMoney()));
		centerProxyShares = (CenterProxyShares) this.centerProxySharesDAO
				.findByPk(key);

		this.feeRateComma = this.centerProxyShares.getFeeRate().toString();
		this.updateUlmoney = centerProxyShares.getUlMoney().toString();
		return MODIFY;
	}

	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			centerProxyShares.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		centerProxyShares.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		centerProxyShares.setUpdateBy(this.getSessionUserCode());
		centerProxySharesService.modifyCenterProxyShares(centerProxyShares);
		String msg = "修改运营机构与运营机构代理商分润参数[" + centerProxyShares.getProxyId()
				+ "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/centerProxyShares/list.do", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue()) && 
				!this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()) && 
				!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			throw new BizException("没有权限设置运营机构代理商分润参数！");
		}
		centerProxyShares.setProxyId(request.getParameter("proxyId"));
		centerProxyShares.setUlMoney(new BigDecimal(request
				.getParameter("ulMoney")));
		centerProxyShares.setBranchCode(request.getParameter("branchCode"));
		centerProxySharesService.deleteCenterProxyShares(centerProxyShares);
		String msg = "删除运营机构与运营机构代理商分润参数[" + centerProxyShares.getProxyId()
				+ "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/centerProxyShares/list.do", msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CenterProxyShares getCenterProxyShares() {
		return centerProxyShares;
	}

	public void setCenterProxyShares(CenterProxyShares releaseCardFee) {
		this.centerProxyShares = releaseCardFee;
	}

	public CenterProxySharesKey getKey() {
		return key;
	}

	public void setKey(CenterProxySharesKey key) {
		this.key = key;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
	}

	public boolean isShowProxy() {
		return showProxy;
	}

	public void setShowProxy(boolean showProxy) {
		this.showProxy = showProxy;
	}

	public String[] getUlimit() {
		return ulimit;
	}

	public void setUlimit(String[] ulimit) {
		this.ulimit = ulimit;
	}

	public String[] getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String[] feeRate) {
		this.feeRate = feeRate;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
