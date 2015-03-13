package gnete.card.web.cardInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 过期卡查询
 * @author aps-lib
 *
 */
public class ExpireCardAction extends BaseAction{

	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private CardInfo cardInfo;
	private Paginater page;
	private List<CardState> expireCardState; // 过期卡状态
	private String startDate;
	private String endDate;
	private BigDecimal startAvbl_Bal;
	private BigDecimal endAvbl_Bal;
	private String expireDate;
	private List<BranchInfo> cardBranchList;
    private CardTypeCode cardTypeCode; // 卡类型
    private Collection CardStatusList;
    
    private String startCardId;
	private String endCardId;
    
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
    	
    	// 过期卡的状态
    	this.CardStatusList = CardState.getExpireCard();
    	expireCardState = CardState.getExpireCard();
		String[] types = new String[expireCardState.size()];
    	for (int i = 0; i < expireCardState.size(); i++) {
    		types[i] = expireCardState.get(i).getValue();
    	}
    	params.put("types", types);
    	
    	// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(branchInfoDAO
					.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				cardBranchList.add(new BranchInfo());
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo()));
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("appOrgId", this.getSessionUser().getBranchNo());
		} 
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if(cardInfo != null){
    		params.put("cardId", cardInfo.getCardId());
    		params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("cardStatus", cardInfo.getCardStatus());
			
			// 如果只是输入开始卡号和结束卡号中的一个，则不予查询
			if((StringUtils.isNotEmpty(this.getStartCardId())&&StringUtils.isEmpty(this.getEndCardId()))||
					(StringUtils.isEmpty(this.getStartCardId())&&StringUtils.isNotEmpty(this.getEndCardId()))){ 
				return LIST;
			}
			
			params.put("startCardId", this.getStartCardId());
			params.put("endCardId", this.getEndCardId());
			
			// 卡BIN是必选查询项
			if(StringUtils.isNotEmpty(cardInfo.getCardIssuer()) || StringUtils.isNotEmpty(cardInfo.getCardBin())){
				params.put("cardBin", cardInfo.getCardBin());
				
				this.page = this.cardInfoDAO.findExpireCard(params, this.getPageNumber(), this.getPageSize());
			}
    	}
		
		return LIST;
	}
	
	// 显示过期卡详细信息，包括子账户部分信息
	public String detail()throws Exception{
		
		Map<String, Object> subAcctParams = new HashMap<String, Object>();
		
		Assert.notNull(cardInfo, "卡对象不能为空");	
		
		// 过期信息明细
		this.cardInfo = (CardInfo)this.cardInfoDAO.findByPk(this.cardInfo.getCardId());
		
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.cardInfo.getCardClass());
		
		// 子账户列表信息
		subAcctParams.put("acctId", cardInfo.getAcctId());
		this.page = this.subAcctBalDAO.findSubAcctBal(subAcctParams, this.getPageNumber(), this.getPageSize());
		
		this.log("查询过期卡["+this.cardInfo.getCardId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
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

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	public CardInfo getCardInfo() {
		return cardInfo;
	}
	public void setPage(Paginater page) {
		this.page = page;
	}
	public Paginater getPage() {
		return page;
	}

	public void setExpireCardState(List<CardState> expireCardState) {
		this.expireCardState = expireCardState;
	}

	public List<CardState> getExpireCardState() {
		return expireCardState;
	}

	public BigDecimal getStartAvbl_Bal() {
		return startAvbl_Bal;
	}

	public void setStartAvbl_Bal(BigDecimal startAvblBal) {
		startAvbl_Bal = startAvblBal;
	}

	public BigDecimal getEndAvbl_Bal() {
		return endAvbl_Bal;
	}

	public void setEndAvbl_Bal(BigDecimal endAvblBal) {
		endAvbl_Bal = endAvblBal;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public Collection getCardStatusList() {
		return CardStatusList;
	}

	public void setCardStatusList(Collection cardStatusList) {
		CardStatusList = cardStatusList;
	}

	public String getStartCardId() {
		return startCardId;
	}

	public void setStartCardId(String startCardId) {
		this.startCardId = startCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}

}
