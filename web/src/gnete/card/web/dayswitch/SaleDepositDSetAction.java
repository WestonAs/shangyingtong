package gnete.card.web.dayswitch;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchTransDSetDAO;
import gnete.card.dao.TransDAO;
import gnete.card.datasource.DbContextHolder;
import gnete.card.datasource.DbType;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchTransDSet;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.type.DSetTransType;
import gnete.card.entity.type.RoleType;
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
 * 售卡充值清算信息查询
 * @author lih
 * 
 */
public class SaleDepositDSetAction extends BaseAction {

	@Autowired
	private MerchTransDSetDAO merchTransDSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TransDAO transDAO;

	private MerchTransDSet merchTransDSet;
	private Paginater page;
	private List transTypeList;
	private BigDecimal curPageSum;
	private BigDecimal amountTotal;;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchList;
	
	private boolean showCard = false;
	private boolean showCardSell = false;
	
	private String startDate;
	private String endDate;
	
	private List<BranchInfo> cardBranchList;

	public String execute() throws Exception {
		this.transTypeList = DSetTransType.getSellCardTypeList();
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		params.put("transTypeList", transTypeList);

		if (StringUtils.isNotBlank(startDate)) {
			params.put("startDate", startDate);
		} else {
			startDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		if (StringUtils.isNotBlank(endDate)) {
			params.put("endDate", endDate);
		} else {
			endDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if (merchTransDSet != null) {
			params.put("payName", MatchMode.ANYWHERE.toMatchString(merchTransDSet.getPayName()));
			params.put("recvName", MatchMode.ANYWHERE.toMatchString(merchTransDSet.getRecvName()));
			params.put("transType", merchTransDSet.getTransType());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}

		if (isCenterOrCenterDeptRoleLogined()) {// 如果登录用户为运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("payCodes", cardBranchList);
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构或者发卡机构部门
			showCard = true;
			branchList = this.getMyCardBranch();
			params.put("recvCode", branchList.get(0).getBranchCode());
		} else if (isCardSellRoleLogined()) {// 售卡代理
			showCardSell = true;
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("payCode", branchList.get(0).getBranchCode());
		} else {
			throw new BizException("没有权限查询。");
		}

		this.page = this.merchTransDSetDAO.findSaleDepositTransDSet(params, this.getPageNumber(), this
				.getPageSize());

		this.curPageSum = new BigDecimal(0);
		this.amountTotal = this.merchTransDSetDAO.getSaleDepositAmounTotal(params);
		if (amountTotal == null) {
			this.amountTotal = new BigDecimal(0);
		}

		ArrayList<MerchTransDSet> list = (ArrayList<MerchTransDSet>) this.page.getData();
		for (int i = 0; i < list.size(); i++) {
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
		params.put("cardIssuer", merchTransDSet.getRecvCode());
		if (DSetTransType.ACT_DEPOSIT.getValue().equals(merchTransDSet.getTransType())
				|| DSetTransType.ACT_SELLCARD.getValue().equals(merchTransDSet.getTransType())) {
			params.put("merProxyNo", merchTransDSet.getPayCode());
		} else {
			params.put("merNo", merchTransDSet.getPayCode());
		}
		params.put("types", TradeTypeMap.getTransTradeType(merchTransDSet.getTransType()));
		params.put("settDate", merchTransDSet.getSetDate());
		params.put("procStatus", ProcState.DEDSUCCESS.getValue());
		
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

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
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

	public List getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List transTypeList) {
		this.transTypeList = transTypeList;
	}

	public boolean isShowCardSell() {
		return showCardSell;
	}

	public void setShowCardSell(boolean showCardSell) {
		this.showCardSell = showCardSell;
	}

	/*public Collection<VerifyCheckState> getVerifyCheckStateList() {
		return verifyCheckStateList;
	}

	public void setVerifyCheckStateList(
			Collection<VerifyCheckState> verifyCheckStateList) {
		this.verifyCheckStateList = verifyCheckStateList;
	}*/


}
