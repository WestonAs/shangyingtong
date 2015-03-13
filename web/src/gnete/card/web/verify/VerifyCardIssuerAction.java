package gnete.card.web.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardIssuerFeeMSetDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardIssuerFeeMSet;
import gnete.card.entity.CardIssuerFeeMSetKey;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.card.entity.type.UserLogType;

/**
 * @File: VerifyCardIssuerAction.java
 * 
 * @description: 发卡机构手续费核销[分支机构与发卡机构手续费汇总月结算]
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: lib
 * @version: 1.0
 * @since 1.0 2011-3-9
 */

public class VerifyCardIssuerAction extends BaseAction{

	@Autowired
	private CardIssuerFeeMSetDAO cardIssuerFeeMSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VerifyService verifyService;

	private CardIssuerFeeMSet cardIssuerFeeMSet;
	private Paginater page;
	private List chkStatusList;
	private String startDate;
	private String endDate;
	private List<BranchInfo> branchList;
	private List<BranchInfo> chlList;
	private boolean showFenzhi = false;
	private boolean showCard = false;
	private String remainfeeAmt; // 剩余核销金额
	
	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardIssuerFeeMSet != null) {
			params.put("chlName", MatchMode.ANYWHERE.toMatchString(cardIssuerFeeMSet.getChlName()));
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardIssuerFeeMSet.getBranchName()));
			params.put("chkStatus", cardIssuerFeeMSet.getChkStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 当前登录用户为发卡机构
		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) branchInfoDAO
					.findByPk(this.getSessionUser().getBranchNo()));
			this.showCard = true;
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			this.chlList = new ArrayList<BranchInfo>();
			this.chlList.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			this.showFenzhi = true;
			params.put("chlCode", this.getSessionUser().getBranchNo());
		}
		// 当前登录用户为运营中心或运营中心部门时
		else if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
		} else {
			throw new BizException("没有权限查询发卡机构手续费核销");
		}
		this.page = this.cardIssuerFeeMSetDAO.findCardIssuerFeeMSet(params, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showVerify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("没有权限进行发卡机构手续费核销操作！");
		}

		CardIssuerFeeMSetKey key = new CardIssuerFeeMSetKey();
		key.setBranchCode(this.cardIssuerFeeMSet.getBranchCode());
		key.setChlCode(this.cardIssuerFeeMSet.getChlCode());
		key.setFeeMonth(this.cardIssuerFeeMSet.getFeeMonth());
		this.cardIssuerFeeMSet = (CardIssuerFeeMSet) this.cardIssuerFeeMSetDAO.findByPk(key);
		
		// 应付金额 = 上期结转手续费 + 本期手续费
		BigDecimal shouldPayAmount = AmountUtil.add(cardIssuerFeeMSet.getLastFee(), cardIssuerFeeMSet.getChlFeeAmt());
		
		// 剩余核销金额 = 应收金额 - 实收金额
		BigDecimal remainFee = AmountUtil.subtract(shouldPayAmount, cardIssuerFeeMSet.getRecvAmt());
		this.remainfeeAmt = remainFee.toString();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(this.cardIssuerFeeMSet.getBranchCode());
		this.cardIssuerFeeMSet.setBranchName(branch.getBranchName());
		
		return "verify";
	}

	public String verify() throws Exception {
		this.verifyService.verifyCardIssuer(this.cardIssuerFeeMSet, this.getSessionUserCode());
		
		String msg = LogUtils.r("分支机构发卡机构[{0}]手续费核销成功", cardIssuerFeeMSet.getBranchCode());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/verify/verifyCardIssuerFee/list.do", msg);
		return SUCCESS;
	}


	public CardIssuerFeeMSet getCardIssuerFeeMSet() {
		return cardIssuerFeeMSet;
	}

	public void setCardIssuerFeeMSet(CardIssuerFeeMSet cardIssuerFeeMSet) {
		this.cardIssuerFeeMSet = cardIssuerFeeMSet;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getChkStatusList() {
		return chkStatusList;
	}

	public void setChkStatusList(List chkStatusList) {
		this.chkStatusList = chkStatusList;
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
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

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public String getRemainfeeAmt() {
		return remainfeeAmt;
	}

	public void setRemainfeeAmt(String remainfeeAmt) {
		this.remainfeeAmt = remainfeeAmt;
	}


}
