package gnete.card.web.cardInfo;

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
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 预制卡查询
 * @author aps-lib
 *
 */
public class PreCardAction extends BaseAction{

	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private CardInfo cardInfo;
	private Paginater page;
	private Collection CardStatusList;
	private Collection cardClassList;
	private List<CardState> preCardState;
	private List<BranchInfo> cardBranchList;
	
	private String startCardId;
	private String endCardId;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		this.cardClassList = cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		
		// 预制卡状态
		this.CardStatusList = CardState.getPreCard();
		preCardState = CardState.getPreCard();
		String[] types = new String[preCardState.size()];
		
    	for (int i = 0; i < preCardState.size(); i++) {
    		types[i] = preCardState.get(i).getValue();
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
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if (cardInfo != null) {
			params.put("cardId", cardInfo.getCardId());
			params.put("cardClass", cardInfo.getCardClass());
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
				
				this.page = this.cardInfoDAO.findPreCard(params, this.getPageNumber(), this.getPageSize());
			}
		}
		
		return LIST;
	}
	
	public String detail() throws Exception{
		this.cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.cardInfo.getCardId());
		
		this.log("查询预制卡["+this.cardInfo.getCardId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfoDAO(CardInfoDAO cardInfoDAO) {
		this.cardInfoDAO = cardInfoDAO;
	}

	public CardInfoDAO getCardInfoDAO() {
		return cardInfoDAO;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setCardStatusList(Collection cardStatusList) {
		CardStatusList = cardStatusList;
	}

	public Collection getCardStatusList() {
		return CardStatusList;
	}

	public void setCardClassList(Collection cardClassList) {
		this.cardClassList = cardClassList;
	}

	public Collection getCardClassList() {
		return cardClassList;
	}

	public void setPreCardState(List<CardState> preCardState) {
		this.preCardState = preCardState;
	}

	public List<CardState> getPreCardState() {
		return preCardState;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
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
