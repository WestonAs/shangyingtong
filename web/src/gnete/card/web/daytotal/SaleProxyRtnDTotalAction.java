package gnete.card.web.daytotal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.SaleProxyRtnDTotalDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.SaleProxyRtnDTotal;
import gnete.card.entity.SaleProxyRtnDTotalKey;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 商户代理返利查询
 */
public class SaleProxyRtnDTotalAction extends BaseAction{

	@Autowired
	private SaleProxyRtnDTotalDAO saleProxyRtnDTotalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private SaleProxyRtnDTotal saleProxyRtnDTotal;
	private Paginater page;
	private BigDecimal curPageSum;
	private BigDecimal amountTotal;
	private BigDecimal shareAmt;
	
	private List<BranchInfo> orgList;
	private List<BranchInfo> proxyList;
	
	private boolean showCard = false;
	private boolean showProxy = false;
	
	private String startDate;
	private String endDate;
	
	private List<BranchInfo> cardBranchList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();

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
		
		if ( saleProxyRtnDTotal != null) {
			params.put("orgName", MatchMode.ANYWHERE.toMatchString(saleProxyRtnDTotal.getOrgName()));
			params.put("proxyName", MatchMode.ANYWHERE.toMatchString(saleProxyRtnDTotal.getProxyName()) );
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				cardBranchList.add(new BranchInfo());
			}
		}
		// 发卡机构或者发卡机构部门
		else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())||
				this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())){
			showCard = true;
			orgList = this.getMyCardBranch();
			params.put("orgId", orgList.get(0).getBranchCode());
		} 
		// 售卡代理
		else if (this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){
			showProxy = true;
			List<BranchInfo> result = new ArrayList<BranchInfo>();
			result.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			proxyList = result;
			params.put("proxyId", proxyList.get(0).getBranchCode());
		} 
		else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("orgIds", cardBranchList);
		}

 		this.page = this.saleProxyRtnDTotalDAO.findSaleProxyRtnDTotal(params, this.getPageNumber(), this.getPageSize());
		
 		this.curPageSum = new BigDecimal(0);
		this.amountTotal = this.saleProxyRtnDTotalDAO.getAmounTotal(params);
		if(amountTotal==null){
			this.amountTotal = new BigDecimal(0);
		}
		
		ArrayList<SaleProxyRtnDTotal> list = (ArrayList<SaleProxyRtnDTotal>) this.page.getData();
		for(int i=0; i<list.size();i++ ){
			if(list.get(i).getShareAmtSuff()!=null){
				curPageSum = curPageSum.add(list.get(i).getShareAmt());
			}
		}
		
		return LIST;
	}
	
	//取得记录的明细
	public String detail() throws Exception {
		
		SaleProxyRtnDTotalKey key = new SaleProxyRtnDTotalKey();
		key.setFeeDate(saleProxyRtnDTotal.getFeeDate());
		key.setOrgId(saleProxyRtnDTotal.getOrgId());
		key.setProxyId(saleProxyRtnDTotal.getProxyId());
		key.setCardBin(saleProxyRtnDTotal.getCardBin());
		this.saleProxyRtnDTotal = (SaleProxyRtnDTotal)this.saleProxyRtnDTotalDAO.findByPk(key);
		
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(saleProxyRtnDTotal.getOrgId());
		String orgName = branchInfo.getBranchName();
		branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(saleProxyRtnDTotal.getProxyId());
		String proxyName = branchInfo.getBranchName();
		this.saleProxyRtnDTotal.setOrgName(orgName);
		this.saleProxyRtnDTotal.setProxyName(proxyName);
		this.shareAmt = this.saleProxyRtnDTotal.getShareAmtSale().add(this.saleProxyRtnDTotal.getShareAmtSuff());
		
		this.log("查询发卡机构["+this.saleProxyRtnDTotal.getOrgId()+"]日统计信息成功", UserLogType.SEARCH);
		return DETAIL;
	}

	public SaleProxyRtnDTotal getSaleProxyRtnDTotal() {
		return saleProxyRtnDTotal;
	}

	public void setSaleProxyRtnDTotal(SaleProxyRtnDTotal saleProxyRtnDTotal) {
		this.saleProxyRtnDTotal = saleProxyRtnDTotal;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BigDecimal getCurPageSum() {
		return curPageSum;
	}

	public void setCurPageSum(BigDecimal curPageSum) {
		this.curPageSum = curPageSum;
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = amountTotal;
	}

	public List<BranchInfo> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<BranchInfo> orgList) {
		this.orgList = orgList;
	}

	public List<BranchInfo> getProxyList() {
		return proxyList;
	}

	public void setProxyList(List<BranchInfo> proxyList) {
		this.proxyList = proxyList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowProxy() {
		return showProxy;
	}

	public void setShowProxy(boolean showProxy) {
		this.showProxy = showProxy;
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

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public BigDecimal getShareAmt() {
		return shareAmt;
	}

	public void setShareAmt(BigDecimal shareAmt) {
		this.shareAmt = shareAmt;
	}

}
