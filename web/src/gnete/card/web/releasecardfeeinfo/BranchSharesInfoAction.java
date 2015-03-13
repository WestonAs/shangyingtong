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
import gnete.card.dao.ReleaseCardFeeDTotalDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReleaseCardFeeDTotal;
import gnete.card.entity.ReleaseCardFeeDTotalKey;
import gnete.card.entity.type.CardMerchFeeTransType;
import gnete.card.entity.type.FeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 运营分支机构分润查询
 */
public class BranchSharesInfoAction extends BaseAction{

	@Autowired
	private ReleaseCardFeeDTotalDAO releaseCardFeeDTotalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private ReleaseCardFeeDTotal releaseCardFeeDTotal;
	private Paginater page;
	private Collection<FeeType> feeTypeList;
	private Collection<TransType> transTypeList;
	private List<BranchInfo> chlList;
	private boolean showFenzhi = false;
	private String startDate;
	private String endDate;
	
	@Override
	public String execute() throws Exception {
		this.transTypeList = CardMerchFeeTransType.getList();
		Map<String, Object> params = new HashMap<String, Object>();
		
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
		
		if ( releaseCardFeeDTotal != null) {
			params.put("chlName", MatchMode.ANYWHERE.toMatchString(releaseCardFeeDTotal.getChlName()));
			params.put("transType", releaseCardFeeDTotal.getTransType());
		}
		
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showFenzhi = true;
			chlList = new ArrayList<BranchInfo>();
			chlList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser()
					.getBranchNo()));
			params.put("chlCode", chlList.get(0).getBranchCode() );
		} else if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			
		} else {
			throw new BizException("没有权限查询运营分支机构分润！");
		}

 		this.page = this.releaseCardFeeDTotalDAO.findBranchSharesInfo(params, this
				.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	//取得记录的明细
	public String detail() throws Exception {
		
		if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			showFenzhi = true;
		} else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())){
			
		} 
		ReleaseCardFeeDTotalKey key = new ReleaseCardFeeDTotalKey();
		key.setBranchCode(this.releaseCardFeeDTotal.getBranchCode());
		key.setCardBin(this.releaseCardFeeDTotal.getCardBin());
		key.setFeeDate(this.releaseCardFeeDTotal.getFeeDate());
		key.setMerchId(this.releaseCardFeeDTotal.getMerchId());
		key.setPosManageId(this.releaseCardFeeDTotal.getPosManageId());
		String posProvId = this.releaseCardFeeDTotal.getPosProvId();
		key.setPosProvId(StringUtils.isEmpty(posProvId) ? " " : posProvId);
		key.setTransType(this.releaseCardFeeDTotal.getTransType());
		this.releaseCardFeeDTotal = (ReleaseCardFeeDTotal)this.releaseCardFeeDTotalDAO.findByPk(key);
		
		return DETAIL;
	}

	public ReleaseCardFeeDTotal getReleaseCardFeeDTotal() {
		return releaseCardFeeDTotal;
	}

	public void setReleaseCardFeeDTotal(ReleaseCardFeeDTotal releaseCardFeeDTotal) {
		this.releaseCardFeeDTotal = releaseCardFeeDTotal;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection<FeeType> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(Collection<FeeType> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public Collection<TransType> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection<TransType> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<BranchInfo> getChlList() {
		return chlList;
	}

	public void setChlList(List<BranchInfo> chlList) {
		this.chlList = chlList;
	}

	public boolean isShowFenzhi() {
		return showFenzhi;
	}

	public void setShowFenzhi(boolean showFenzhi) {
		this.showFenzhi = showFenzhi;
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

}
