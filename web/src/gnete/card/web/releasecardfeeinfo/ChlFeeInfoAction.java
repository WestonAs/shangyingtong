package gnete.card.web.releasecardfeeinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ChlFeeDTotalDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ChlFeeDTotal;
import gnete.card.entity.ChlFeeDTotalKey;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 平台运营手续费查询
 * @author aps-lib
 *
 */
public class ChlFeeInfoAction extends BaseAction{

	@Autowired
	private ChlFeeDTotalDAO chlFeeDTotalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private ChlFeeDTotal chlFeeDTotal;
	private Paginater page;
	private Collection<ChlFeeContentType> chlFeeContentTypeList;
	private Collection<ChlFeeType> feeTypeList;
	private String startDate;
	private String endDate;
	private boolean showFenzhi = false;
	private List<BranchInfo> branchList;

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.chlFeeContentTypeList = ChlFeeContentType.getList();
		this.feeTypeList = ChlFeeType.getForChlFee();
		
		if(StringUtils.isNotBlank(startDate)){
			params.put("startDate", startDate);
		}else{
			startDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		if(StringUtils.isNotBlank(endDate)){
			params.put("endDate", endDate);
		}else{
			endDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		if ( chlFeeDTotal != null) {
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(chlFeeDTotal.getChlName()));
			params.put("transType", chlFeeDTotal.getTransType());
			params.put("feeType", chlFeeDTotal.getFeeType());
		}
		
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showFenzhi = true;
			this.branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("chlCode", this.getSessionUser().getBranchNo());
			
		} else if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			
		} else {
			throw new BizException("没有权限查询平台运营手续费！");
		}

 		this.page = this.chlFeeDTotalDAO.findChlFeeDTotal(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	//取得记录的明细
	public String detail() throws Exception {
		
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showFenzhi = true;
			
		} else if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			
		} else {
			throw new BizException("没有权限查询平台运营手续费！");
		}

		ChlFeeDTotalKey key = new ChlFeeDTotalKey();
		key.setChlCode(this.chlFeeDTotal.getChlCode());
		key.setFeeDate(this.chlFeeDTotal.getFeeDate());
		key.setTransType(this.chlFeeDTotal.getTransType());
		
		this.chlFeeDTotal = (ChlFeeDTotal)this.chlFeeDTotalDAO.findByPk(key);
		return DETAIL;
	}

	public ChlFeeDTotal getChlFeeDTotal() {
		return chlFeeDTotal;
	}

	public void setChlFeeDTotal(ChlFeeDTotal chlFeeDTotal) {
		this.chlFeeDTotal = chlFeeDTotal;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection<ChlFeeContentType> getChlFeeContentTypeList() {
		return chlFeeContentTypeList;
	}

	public void setChlFeeContentTypeList(
			Collection<ChlFeeContentType> chlFeeContentTypeList) {
		this.chlFeeContentTypeList = chlFeeContentTypeList;
	}

	public Collection<ChlFeeType> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(Collection<ChlFeeType> feeTypeList) {
		this.feeTypeList = feeTypeList;
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

	public boolean isShowFenzhi() {
		return showFenzhi;
	}

	public void setShowFenzhi(boolean showFenzhi) {
		this.showFenzhi = showFenzhi;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}
		

}
