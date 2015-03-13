package gnete.card.web.dayswitch;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchTransDSetDAO;
import gnete.card.dao.TransDAO;
import gnete.card.dao.TransRevocationDAO;
import gnete.card.datasource.DbContextHolder;
import gnete.card.datasource.DbType;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchTransDSet;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.state.RevcState;
import gnete.card.entity.state.RevcType;
import gnete.card.entity.type.DSetTransType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.TradeTypeMap;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 交易清算信息查询
 * @author lib
 * 
 */
public class MerchTransDSetAction extends BaseAction {

	@Autowired
	private MerchTransDSetDAO merchTransDSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TransDAO transDAO;
	@Autowired
	private TransRevocationDAO transRevocationDAO;

	private MerchTransDSet merchTransDSet;
	private Paginater page;
	private List transTypeList;
	private BigDecimal curPageSum;
	private BigDecimal amountTotal;;
	private List<BranchInfo> branchList;
	
	private boolean showTrans = false;
	private boolean showTransRevocation = false;
	
	private String startDate;
	private String endDate;
	
	private List<BranchInfo> cardBranchList;

	public String execute() throws Exception {
		this.transTypeList = DSetTransType.getTransSetTypeList();
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		params.put("transTypeList", transTypeList);
		
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
		
		if (merchTransDSet != null) {
			params.put("payCode",merchTransDSet.getPayCode());
			params.put("payName", MatchMode.ANYWHERE.toMatchString(merchTransDSet.getPayName()));
			params.put("recvCode",merchTransDSet.getRecvCode());
			params.put("recvName", MatchMode.ANYWHERE.toMatchString(merchTransDSet.getRecvName()));
			params.put("transType", merchTransDSet.getTransType());
		} 
		
	
		if (isCenterOrCenterDeptRoleLogined()) { // 如果登录用户为运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("payCodes", cardBranchList);
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构或者发卡机构部门
			branchList = this.getMyCardBranch();
			params.put("payCodes", branchList);
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("recvCode", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查询。");
		}
		
 		this.page = this.merchTransDSetDAO.findMerchTransDSet(params, this
				.getPageNumber(), this.getPageSize());
		
		this.curPageSum = new BigDecimal(0);
		this.amountTotal = this.merchTransDSetDAO.getAmounTotal(params);
		if(amountTotal==null){
			this.amountTotal = new BigDecimal(0);
		}
		
		ArrayList<MerchTransDSet> list = (ArrayList<MerchTransDSet>) this.page.getData();
		for(int i=0; i<list.size();i++ ){
			curPageSum = curPageSum.add(list.get(i).getFeeAmt());
		}
		return LIST;
	}
	
	// 取得发卡机构与商户日结算记录的明细
	public String detail() throws Exception {
		Map keyMap = new HashMap();
		keyMap.put("payCode", merchTransDSet.getPayCode());
		keyMap.put("recvCode", merchTransDSet.getRecvCode());
		keyMap.put("setDate", merchTransDSet.getSetDate());
		keyMap.put("transType", merchTransDSet.getTransType());
		keyMap.put("curCode", merchTransDSet.getCurCode());
		this.merchTransDSet = (MerchTransDSet) this.merchTransDSetDAO.findByPkWithName(keyMap);

		Map<String, Object> params = new HashMap<String, Object>();
		
		// 交易类型是调账、退货，查询《交易撤销/退货/冲正》表
		if(DSetTransType.RETGOODS.getValue().equals(this.merchTransDSet.getTransType())||
				DSetTransType.ADJUST.getValue().equals(this.merchTransDSet.getTransType())){
			showTransRevocation = true;
			showTrans = false;
			params.put("origCardIssuer", this.merchTransDSet.getPayCode());
			params.put("merchNo", (this.merchTransDSet.getRecvCode()).trim());
			// 退货
			if(DSetTransType.RETGOODS.getValue().equals(this.merchTransDSet.getTransType())){
				params.put("revcType", RevcType.RETURN_GOODS.getValue());
			}
			// 调账
			else {
				params.put("revcType", RevcType.ADJACC.getValue());
			}
			params.put("settDate", this.merchTransDSet.getSetDate());
			params.put("revcStatus", RevcState.SUCCESS.getValue());
			this.page = this.transRevocationDAO.findTransRevocation(params, this.getPageNumber(), this.getPageSize());
		} else {
			showTransRevocation = false;
			showTrans = true;
			params.put("cardIssuer", merchTransDSet.getPayCode());
			params.put("merNo", merchTransDSet.getRecvCode());
			params.put("types", TradeTypeMap.getTransTradeType(merchTransDSet.getTransType()));
			params.put("settDate", merchTransDSet.getSetDate());
			params.put("procStatus", ProcState.DEDSUCCESS.getValue());
		}
		try {
			DbContextHolder.setDbType(DbType.SETTLE_DB);
			this.page = this.transDAO.findTransJFLINK(params, this.getPageNumber(), this.getPageSize());
		} finally {
			DbContextHolder.setDbType(DbType.TRANS_DB);
		}
		
		return DETAIL;
	}

	public MerchTransDSet getMerchTransDSet() {
		return merchTransDSet;
	}

	public void setMerchTransDSet(MerchTransDSet merchTransDSet) {
		this.merchTransDSet = merchTransDSet;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List transTypeList) {
		this.transTypeList = transTypeList;
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
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

	public boolean isShowTrans() {
		return showTrans;
	}

	public void setShowTrans(boolean showTrans) {
		this.showTrans = showTrans;
	}

	public boolean isShowTransRevocation() {
		return showTransRevocation;
	}

	public void setShowTransRevocation(boolean showTransRevocation) {
		this.showTransRevocation = showTransRevocation;
	}

	/*public Collection<VerifyCheckState> getVerifyCheckStateList() {
		return verifyCheckStateList;
	}

	public void setVerifyCheckStateList(
			Collection<VerifyCheckState> verifyCheckStateList) {
		this.verifyCheckStateList = verifyCheckStateList;
	}*/


}
