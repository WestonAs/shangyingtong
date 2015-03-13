package gnete.card.web.daytotal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchFeeDTotalDAO;
import gnete.card.dao.TransDAO;
import gnete.card.datasource.DbContextHolder;
import gnete.card.datasource.DbType;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchFeeDTotal;
import gnete.card.entity.MerchFeeDTotalKey;
import gnete.card.entity.MerchProxySharesDTotal;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.type.CardMerchFeeTransType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.TradeTypeMap;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 商户代理分润查询
 */
public class MerchProxySharesDTotalAction extends BaseAction{

	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchFeeDTotalDAO merchFeeDTotalDAO;
	@Autowired
	private TransDAO transDAO;
	
	private MerchFeeDTotal merchFeeDTotal;
	
	private MerchProxySharesDTotal merchProxySharesDTotal;
	private Paginater page;
	
	private List<BranchInfo> branchList;
	private List<BranchInfo> cardMerchList;
	private Collection<TransType> transTypeList;
	
	private boolean showCard = false;
	private boolean showCardMerchant = false;
	
	private BigDecimal curPageSum;
	private BigDecimal amountTotal;
	
	private String startDate;
	private String endDate;
	
	private List<BranchInfo> cardBranchList;
	
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
		
		if ( merchFeeDTotal != null) {
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(merchFeeDTotal.getBranchName()));
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(merchFeeDTotal.getMerchName()));
			params.put("merchProxyName", MatchMode.ANYWHERE.toMatchString(merchFeeDTotal.getMerchProxyName()));
			params.put("transType", merchFeeDTotal.getTransType());
		}
		
		cardBranchList = new ArrayList<BranchInfo>();
		
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
			branchList = this.getMyCardBranch();
			params.put("branchCode", branchList.get(0).getBranchCode());
		}
		// 商户代理
		else if (this.getLoginRoleType().equals(RoleType.CARD_MERCHANT.getValue())){
			showCardMerchant = true;
			List<BranchInfo> result = new ArrayList<BranchInfo>();
			result.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			cardMerchList = result;
			params.put("merchProxy", cardMerchList.get(0).getBranchCode());
		} 
		else {
			throw new BizException("没有权限查询！");
		}
		
		this.page = this.merchFeeDTotalDAO.findMerchFeeDTotal(params, this.getPageNumber(), this.getPageSize());
		
		this.curPageSum = new BigDecimal(0);
		this.amountTotal = this.merchFeeDTotalDAO.getMerchProxyFeeTotal(params);
		if(amountTotal==null){
			this.amountTotal = new BigDecimal(0);
		}
		
		ArrayList<MerchFeeDTotal> list = (ArrayList<MerchFeeDTotal>) this.page.getData();
		for(int i=0; i<list.size();i++ ){
			if(list.get(i).getMerchProxyFee()!=null){
				curPageSum = curPageSum.add(list.get(i).getMerchProxyFee());
			}
		}
		
		//this.log("查询发卡机构与商户代理分润日统计信息成功", UserLogType.SEARCH);
		return LIST;
	}
	
	//取得记录的明细
	public String detail() throws Exception {
		
		MerchFeeDTotalKey key = new MerchFeeDTotalKey();
		key.setBranchCode(this.merchFeeDTotal.getBranchCode());
		key.setCardBin(this.merchFeeDTotal.getCardBin());
		key.setFeeDate(this.merchFeeDTotal.getFeeDate());
		key.setMerchId(this.merchFeeDTotal.getMerchId());
		key.setTransType(this.merchFeeDTotal.getTransType());
		this.merchFeeDTotal = (MerchFeeDTotal)this.merchFeeDTotalDAO.findByPk(key);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", this.merchFeeDTotal.getBranchCode());
		params.put("merNo", this.merchFeeDTotal.getMerchId().trim());
		params.put("types", TradeTypeMap.getTransTradeType(this.merchFeeDTotal.getTransType()));
		params.put("settDate", this.merchFeeDTotal.getFeeDate());
		params.put("cardBin", this.merchFeeDTotal.getCardBin());
		params.put("procStatus", ProcState.DEDSUCCESS.getValue());
		
		try {
			DbContextHolder.setDbType(DbType.SETTLE_DB);
			this.page = this.transDAO.findTransJFLINK(params, this.getPageNumber(), this.getPageSize());
		} finally {
			DbContextHolder.setDbType(DbType.TRANS_DB);
		}
		logger.debug("查询发卡机构["+this.merchFeeDTotal.getBranchCode()+"]日统计信息");
		return DETAIL;
		
	}

	public MerchProxySharesDTotal getMerchProxySharesDTotal() {
		return merchProxySharesDTotal;
	}

	public void setMerchProxySharesDTotal(
			MerchProxySharesDTotal merchProxySharesDTotal) {
		this.merchProxySharesDTotal = merchProxySharesDTotal;
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

	public List<BranchInfo> getCardMerchList() {
		return cardMerchList;
	}

	public void setCardMerchList(List<BranchInfo> cardMerchList) {
		this.cardMerchList = cardMerchList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowCardMerchant() {
		return showCardMerchant;
	}

	public void setShowCardMerchant(boolean showCardMerchant) {
		this.showCardMerchant = showCardMerchant;
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

	public MerchFeeDTotal getMerchFeeDTotal() {
		return merchFeeDTotal;
	}

	public void setMerchFeeDTotal(MerchFeeDTotal merchFeeDTotal) {
		this.merchFeeDTotal = merchFeeDTotal;
	}

	public Collection<TransType> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection<TransType> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}
}
