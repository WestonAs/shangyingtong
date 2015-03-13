package gnete.card.web.releasecardfeeinfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.Paginater;
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
 * 出机方分润查询
 */
public class PosProvSharesInfoAction extends BaseAction{

	@Autowired
	private ReleaseCardFeeDTotalDAO releaseCardFeeDTotalDAO;

	private ReleaseCardFeeDTotal releaseCardFeeDTotal;
	private Paginater page;
	private Collection<FeeType> feeTypeList;
	private Collection<TransType> transTypeList;
	private List<BranchInfo> posProvList;
	private boolean showTerminal = false;
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
			params.put("posProvName", MatchMode.ANYWHERE.toMatchString(releaseCardFeeDTotal.getPosProvName()));
			params.put("transType", releaseCardFeeDTotal.getTransType());
		}
		
		// 营运中心、中心部门
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			params.put("chlCode", this.getSessionUser().getBranchNo());
		}
		// 机具出机方
		else if (RoleType.TERMINAL.getValue().equals(this.getLoginRoleType())){
			showTerminal = true;
			posProvList = this.getMyCardBranch();
			params.put("proxyId", posProvList.get(0).getBranchCode());
		} else {
			throw new BizException("没有权限查询出机方分润！");
		}

 		this.page = this.releaseCardFeeDTotalDAO.findPosProvSharesInfo(params, this
				.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	//取得记录的明细
	public String detail() throws Exception {
		
		if (this.getLoginRoleType().equals(RoleType.TERMINAL.getValue())){
			showTerminal = true;
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

	public List<BranchInfo> getPosProvList() {
		return posProvList;
	}

	public void setPosProvList(List<BranchInfo> posProvList) {
		this.posProvList = posProvList;
	}

	public boolean isShowTerminal() {
		return showTerminal;
	}

	public void setShowTerminal(boolean showTerminal) {
		this.showTerminal = showTerminal;
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
