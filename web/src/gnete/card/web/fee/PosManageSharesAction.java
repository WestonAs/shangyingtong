package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.PosManageSharesDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PosManageShares;
import gnete.card.entity.PosManageSharesKey;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.PosManageSharesFeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PosManageSharesService;
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
 * @File: PosManageSharesAction.java
 *
 * @description: 机具维护方分润参数管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-10
 */
public class PosManageSharesAction extends BaseAction {
	
	@Autowired
	private PosManageSharesDAO posManageSharesDAO;
	@Autowired
	private PosManageSharesService posManageSharesService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private PosManageShares posManageShares;
	private Paginater page;
	private Collection posManageList;
	private String posManageId;
	private String branchCode;
	private String ulMoney;
	private Collection costCycleList;

	private boolean showPos = false;
	private boolean showChl = false;
	
	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	
	private List<BranchInfo> branchList;
	private List<Type> feeTypeList;
	private String updateUlmoney;
	private String branchName = null;
	
	@Override
	public String execute() throws Exception {
		feeTypeList = PosManageSharesFeeType.getFeeType();
		costCycleList = CostCycleType.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(posManageShares != null){
			params.put("posManageId", posManageShares.getPosManageId());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(posManageShares.getBranchName()));
			params.put("posManageName", MatchMode.ANYWHERE.toMatchString(posManageShares.getPosManageName()));
			params.put("feeType", posManageShares.getFeeType());
		}
		
		// 机具维护方
		if (this.getLoginRoleType().equals(RoleType.TERMINAL_MAINTAIN.getValue())){
			showPos = true;
			this.showChl = true;
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("posManageId", branchList.get(0).getBranchCode());
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
			branchList = this.branchInfoDAO.findByType(RoleType.TERMINAL_MAINTAIN.getValue());
			this.showChl = false;
		} 
		// 营运中心、中心部门
		else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			branchList = this.branchInfoDAO.findByType(RoleType.TERMINAL_MAINTAIN.getValue());
			this.showChl = true;
		} else {
			throw new BizException("没有权限查询分支机构与机具维护方分润参数！");
		}
		
		this.page = posManageSharesDAO.findPosManageShares(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构没有权限设置机具维护方分润参数！");
		}
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		Assert.notNull(branchInfo, "登录机构信息不存在！");
		this.branchName = branchInfo.getBranchName();
		
		feeTypeList = PosManageSharesFeeType.getFeeType();
		costCycleList = CostCycleType.getMonth();
		branchList = this.branchInfoDAO.findByType(RoleType.TERMINAL_MAINTAIN.getValue());
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		this.posManageShares.setBranchCode(this.getSessionUser().getBranchNo());
		
		// 如果是分段的
		List<PosManageShares> feeList = new ArrayList<PosManageShares>();
		if (posManageShares.getFeeType().equals(PosManageSharesFeeType.TRADEMONEY.getValue())
				|| posManageShares.getFeeType().equals(PosManageSharesFeeType.SSUM.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				PosManageShares fee = new PosManageShares();
				fee = new PosManageShares();
				formToBo(fee, this.posManageShares);
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				
				feeList.add(fee);
			}
		} else {
			PosManageShares fee = new PosManageShares();
			fee = new PosManageShares();
			formToBo(fee, this.posManageShares);
			fee.setUlMoney(Constants.FEE_MAXACCOUNT);
			fee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			feeList.add(fee);
		}
		
		this.posManageSharesService.addPosManageShares(feeList.toArray(new PosManageShares[feeList.size()]), this.getSessionUserCode());	
		String msg = "添加分支机构与机具维护方分润参数["+ posManageShares.getPosManageId() +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/posManageShares/list.do", msg);
		return SUCCESS;
	}

	private void formToBo(PosManageShares dest, PosManageShares src){
		dest.setBranchCode(src.getBranchCode());
		dest.setPosManageId(src.getPosManageId());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
	}

	public String showModify() throws Exception {
		
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构没有权限设置机具维护方分润参数！");
		}
		
		PosManageSharesKey key = new PosManageSharesKey();
		key.setBranchCode(this.getBranchCode());
		key.setPosManageId(this.getPosManageId());
		key.setUlMoney(new BigDecimal(this.getUlMoney()));
		this.posManageShares = (PosManageShares)this.posManageSharesDAO.findByPk(key);
		posManageShares.setPosManageName(((BranchInfo)branchInfoDAO.findByPk(posManageShares.getPosManageId())).getBranchName());
		this.feeRateComma = this.posManageShares.getFeeRate().toString();
		
		this.updateUlmoney = posManageShares.getUlMoney().toString();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			posManageShares.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		posManageShares.setUpdateBy(this.getSessionUserCode());
		posManageShares.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		
		this.posManageSharesService.modifyPosManageShares(posManageShares);
		String msg = "修改分支机构与机具维护方分润参数["+ posManageShares.getPosManageId() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/posManageShares/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构没有权限设置机具维护方分润参数！");
		}
		
		posManageShares.setPosManageId(this.getPosManageId());
		posManageShares.setUlMoney(new BigDecimal(this.getUlMoney()));
		posManageShares.setBranchCode(this.getBranchCode());
		
		this.posManageSharesService.deletePosManageShares(posManageShares);
		String msg = "删除分支机构与机具维护方分润参数["+ posManageShares.getPosManageId() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/posManageShares/list.do", msg);
		return SUCCESS;
	}

	public PosManageShares getPosManageShares() {
		return posManageShares;
	}

	public void setPosManageShares(PosManageShares posManageShares) {
		this.posManageShares = posManageShares;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getPosManageList() {
		return posManageList;
	}

	public void setPosManageList(Collection posManageList) {
		this.posManageList = posManageList;
	}

	public String getPosManageId() {
		return posManageId;
	}

	public void setPosManageId(String posManageId) {
		this.posManageId = posManageId;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
	}

	public boolean isShowPos() {
		return showPos;
	}

	public void setShowPos(boolean showPos) {
		this.showPos = showPos;
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

	public boolean isShowChl() {
		return showChl;
	}

	public void setShowChl(boolean showChl) {
		this.showChl = showChl;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUlMoney() {
		return ulMoney;
	}

	public void setUlMoney(String ulMoney) {
		this.ulMoney = ulMoney;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}
	
}
