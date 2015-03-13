package gnete.card.web.releasecardfeeinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardIssuerFeeDTotalDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardIssuerFeeDTotal;
import gnete.card.entity.CardIssuerFeeDTotalKey;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 分支机构运营手续费查询
 * @author aps-lib
 *
 */
public class CardIssuerFeeInfoAction extends BaseAction{

	@Autowired
	private CardIssuerFeeDTotalDAO cardIssuerFeeDTotalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private CardIssuerFeeDTotal cardIssuerFeeDTotal;
	private Paginater page;
	private Collection<ChlFeeContentType> chlFeeContentTypeList;
	private Collection<ChlFeeType> feeTypeList;
	private String startDate;
	private String endDate;
	private boolean showFenzhi = false;
	private boolean showCard = false;
	private Collection<BranchInfo> chlList;
	private Collection<BranchInfo> branchList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.chlFeeContentTypeList = ChlFeeContentType.getList();
		this.feeTypeList = ChlFeeType.getList();
		
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
		
		if ( cardIssuerFeeDTotal != null) {
			params.put("chlName", MatchMode.ANYWHERE.toMatchString(cardIssuerFeeDTotal.getChlName()));
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardIssuerFeeDTotal.getBranchName()));
			params.put("transType", cardIssuerFeeDTotal.getTransType());
			params.put("feeType", cardIssuerFeeDTotal.getFeeType());
		}
		
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showFenzhi = true;
			showCard = false;
			this.chlList = new ArrayList<BranchInfo>();
			this.chlList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("chlCode", this.getSessionUser().getBranchNo());
			
		}
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			showFenzhi = false;
			showCard = true;
			this.branchList = new ArrayList<BranchInfo>();
			this.branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			showFenzhi = false;
			showCard = false;
		} else {
			throw new BizException("没有权限查询分支机构运营手续费！");
		}

 		this.page = this.cardIssuerFeeDTotalDAO.findCardIssuerFeeDTotal(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	//取得记录的明细
	public String detail() throws Exception {
		
		String branchNo = this.getSessionUser().getBranchNo();
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			if(!branchNo.equals(this.cardIssuerFeeDTotal.getChlCode())){
				throw new BizException("没有权限查询分支机构运营手续费！");
			}
		} 
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			if(!branchNo.equals(this.cardIssuerFeeDTotal.getBranchCode())){
				throw new BizException("没有权限查询分支机构运营手续费！");
			}
		}
		else if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			
		} else {
			throw new BizException("没有权限查询分支机构运营手续费！");
		}

		CardIssuerFeeDTotalKey key = new CardIssuerFeeDTotalKey();
		key.setBranchCode(this.cardIssuerFeeDTotal.getBranchCode());
		key.setChlCode(this.cardIssuerFeeDTotal.getChlCode());
		key.setFeeDate(this.cardIssuerFeeDTotal.getFeeDate());
		key.setTransType(this.cardIssuerFeeDTotal.getTransType());
		
		this.cardIssuerFeeDTotal = (CardIssuerFeeDTotal)this.cardIssuerFeeDTotalDAO.findByPk(key);
		return DETAIL;
	}

	public CardIssuerFeeDTotal getCardIssuerFeeDTotal() {
		return cardIssuerFeeDTotal;
	}

	public void setCardIssuerFeeDTotal(CardIssuerFeeDTotal cardIssuerFeeDTotal) {
		this.cardIssuerFeeDTotal = cardIssuerFeeDTotal;
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

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public Collection<BranchInfo> getChlList() {
		return chlList;
	}

	public void setChlList(Collection<BranchInfo> chlList) {
		this.chlList = chlList;
	}

	public Collection<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(Collection<BranchInfo> branchList) {
		this.branchList = branchList;
	}

}
