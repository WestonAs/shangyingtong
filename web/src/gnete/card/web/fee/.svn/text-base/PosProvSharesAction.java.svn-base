package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.PosProvSharesDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PosProvShares;
import gnete.card.entity.PosProvSharesKey;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.PosProvSharesFeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PosProvSharesService;
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
 * @File: PosProvSharesAction.java
 *
 * @description: 机具出机方分润参数管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-10
 */
public class PosProvSharesAction extends BaseAction {
	
	@Autowired
	private PosProvSharesDAO posProvSharesDAO;
	@Autowired
	private PosProvSharesService posProvSharesService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private PosProvShares posProvShares;
	private Paginater page;
	private String posProvId;
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
		feeTypeList = PosProvSharesFeeType.getFeeType();
		costCycleList = CostCycleType.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(posProvShares!=null){
			params.put("posProvId",posProvShares.getPosProvId());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(posProvShares.getBranchName()));
			params.put("posProvName", MatchMode.ANYWHERE.toMatchString(posProvShares.getPosProvName()));
			params.put("feeType",posProvShares.getFeeType());
		}
		
		// 机具出机方
		if (this.getLoginRoleType().equals(RoleType.TERMINAL.getValue())){
			showPos = true;
			this.showChl = true;
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("posProvId", branchList.get(0).getBranchCode());
		} 
		// 分支机构
		else if(this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
			branchList = this.branchInfoDAO.findByType(RoleType.TERMINAL.getValue());
			this.showChl = false;
		} 
		// 营运中心、中心部门
		else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			branchList = this.branchInfoDAO.findByType(RoleType.TERMINAL.getValue());
			this.showChl = true;
		}
		// 其他
		else {
			throw new BizException("没有权限查询机具出机方分润参数！");
		}
		
		this.page = posProvSharesDAO.findPosProvShares(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("非分支机构没有权限设置机具出机方分润参数！");
		}
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		Assert.notNull(branchInfo, "登录机构信息不存在！");
		this.branchName = branchInfo.getBranchName();
		
		feeTypeList = PosProvSharesFeeType.getFeeType();
		costCycleList = CostCycleType.getMonth();
		
		branchList = this.branchInfoDAO.findByType(RoleType.TERMINAL.getValue());
		return ADD;
	}
	
	public String add() throws Exception {
		
		this.posProvShares.setBranchCode(this.getSessionUser().getBranchNo());
		
		// 如果是分段的
		List<PosProvShares> feeList = new ArrayList<PosProvShares>();
		if (posProvShares.getFeeType().equals(PosProvSharesFeeType.TRADEMONEY.getValue())
				|| posProvShares.getFeeType().equals(PosProvSharesFeeType.SSUM.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				PosProvShares fee = new PosProvShares();
				fee = new PosProvShares();
				formToBo(fee, this.posProvShares);
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				feeList.add(fee);
			}
		} else {
			PosProvShares fee = new PosProvShares();
			fee = new PosProvShares();
			formToBo(fee, this.posProvShares);
			fee.setUlMoney(Constants.FEE_MAXACCOUNT);
			fee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			feeList.add(fee);
		}
		
		this.posProvSharesService.addPosProvShares(feeList.toArray(new PosProvShares[feeList.size()]), this.getSessionUserCode());	
		String msg = "添加分支机构与机具出机方分润参数["+ posProvShares.getPosProvId() +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/posProvShares/list.do", msg);
		return SUCCESS;
	}

	private void formToBo(PosProvShares dest, PosProvShares src){
		dest.setBranchCode(src.getBranchCode());
		dest.setPosProvId(src.getPosProvId());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
	}

	public String showModify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("非分支机构没有权限设置机具出机方分润参数！");
		}
		PosProvSharesKey key = new PosProvSharesKey();
		key.setBranchCode(this.getBranchCode());
		key.setPosProvId(this.getPosProvId());
		key.setUlMoney(new BigDecimal(this.getUlMoney()));
		this.posProvShares = (PosProvShares)this.posProvSharesDAO.findByPk(key);
		posProvShares.setPosProvName(((BranchInfo)branchInfoDAO.findByPk(posProvShares.getPosProvId())).getBranchName());
		this.feeRateComma = this.posProvShares.getFeeRate().toString();
		this.updateUlmoney = posProvShares.getUlMoney().toString();
		return MODIFY;
	}
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			posProvShares.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		posProvShares.setUpdateBy(this.getSessionUserCode());
		posProvShares.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		
		this.posProvSharesService.modifyPosProvShares(posProvShares);
		String msg = "修改分支机构与机具出机方分润参数["+ posProvShares.getPosProvId() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/posProvShares/list.do", msg);
		return SUCCESS;
	}
	public String delete() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("非分支机构没有权限设置机具出机方分润参数！");
		}
		posProvShares.setBranchCode(this.getBranchCode());
		posProvShares.setPosProvId(this.getPosProvId());
		posProvShares.setUlMoney(new BigDecimal(this.getUlMoney()));
		this.posProvSharesService.deletePosProvShares(posProvShares);
		String msg = "删除分支机构与机具出机方分润参数["+ posProvShares.getPosProvId() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/posProvShares/list.do", msg);
		return SUCCESS;
	}

	public PosProvShares getPosProvShares() {
		return posProvShares;
	}

	public void setPosProvShares(PosProvShares posProvShares) {
		this.posProvShares = posProvShares;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getPosProvId() {
		return posProvId;
	}

	public void setPosProvId(String posProvId) {
		this.posProvId = posProvId;
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

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
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
