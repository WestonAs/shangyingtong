package gnete.card.web.fee;

import flink.util.AmountUtil;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchSharesDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchShares;
import gnete.card.entity.BranchSharesKey;
import gnete.card.entity.type.BranchSharesFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchSharesService;
import gnete.card.web.BaseAction;
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
 * 分支机构分润
 *
 */
public class BranchSharesAction extends BaseAction {
	
	@Autowired
	private BranchSharesDAO branchSharesDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchSharesService branchSharesService;
	
	
	private BranchShares branchShares;
	private Paginater page;
	private String branchCode;
	private Collection costCycleList;

	private boolean showFenzhi = false;
	
	private String[] ulimit;
	private String[] feeRate;
	
	private List<BranchInfo> branchList;
	private List<Type> feeTypeList;
	private String updateUlmoney;
	
	@Override
	public String execute() throws Exception {
		feeTypeList = BranchSharesFeeType.getList();
		costCycleList = CostCycleType.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(branchShares!=null){
			params.put("branchCode",branchShares.getBranchCode());
			params.put("feeType", branchShares.getFeeType());
			params.put("costCycle", branchShares.getCostCycle());
		}
		if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			showFenzhi = true;
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("branchCode", branchList.get(0).getBranchCode());
			
		} else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue());
			
		} else {
			throw new BizException("没有权限查询分支机构分润参数！");
		}
		this.page = branchSharesDAO.findBranchShares(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()))){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue());
		
		feeTypeList = BranchSharesFeeType.getList();
		costCycleList = CostCycleType.getMonth();
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		// 如果是分段的
		List<BranchShares> feeList = new ArrayList<BranchShares>();
		if (branchShares.getFeeType().equals(BranchSharesFeeType.TRADEMONEY.getValue())
				|| branchShares.getFeeType().equals(BranchSharesFeeType.SSUM.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				BranchShares fee = new BranchShares();
				fee = new BranchShares();
				formToBo(fee, this.branchShares);
				fee.setUlMoney(new BigDecimal(ulimit[i]));
				fee.setFeeRate(new BigDecimal(feeRate[i]));
				feeList.add(fee);
			}
		} else {
			BranchShares fee = new BranchShares();
			fee = new BranchShares();
			formToBo(fee, this.branchShares);
			fee.setUlMoney(Constants.FEE_MAXACCOUNT);
			fee.setFeeRate(this.branchShares.getFeeRate());
			feeList.add(fee);
		}
		
		this.branchSharesService.addBranchShares(feeList.toArray(new BranchShares[feeList.size()]), this.getSessionUserCode());	
		String msg = "添加运营中心与分支机构分润参数["+ branchShares.getBranchCode() +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/branchShares/list.do", msg);
		return SUCCESS;
	}
	
	private void formToBo(BranchShares dest, BranchShares src){
		dest.setBranchCode(src.getBranchCode());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
	}

	public String showModify() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()))){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		BranchSharesKey key = new BranchSharesKey();
		key.setBranchCode(branchShares.getBranchCode());
		key.setUlMoney(AmountUtil.format(branchShares.getUlMoney()));
		this.branchShares = (BranchShares)this.branchSharesDAO.findByPk(key);

		this.updateUlmoney = branchShares.getUlMoney().toString();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			branchShares.setNewUlMoney(new BigDecimal(updateUlmoney));
		}
		branchShares.setUpdateBy(this.getSessionUserCode());
		this.branchSharesService.modifyBranchShares(branchShares);
		String msg = "修改运营中心与分支机构分润参数["+ branchShares.getBranchCode() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/branchShares/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue()))){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		branchShares.setBranchCode(branchCode);
		branchShares.setUlMoney(AmountUtil.format(new BigDecimal(request.getParameter("ulMoney"))));
		
		this.branchSharesService.deleteBranchShares(branchShares);
		String msg = "删除运营中心与分支机构分润参数["+ branchShares.getBranchCode() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/branchShares/list.do", msg);
		return SUCCESS;
	}
	

	public BranchShares getBranchShares() {
		return branchShares;
	}

	public void setBranchShares(BranchShares branchShares) {
		this.branchShares = branchShares;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
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

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
	}

	public boolean isShowFenzhi() {
		return showFenzhi;
	}

	public void setShowFenzhi(boolean showFenzhi) {
		this.showFenzhi = showFenzhi;
	}

}
