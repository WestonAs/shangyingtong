package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.state.CardState;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CardInfoDAOImpl extends BaseDAOIbatisImpl implements CardInfoDAO {

	public String getNamespace() {
		return "CardInfo";
	}

	public List<CardInfo> findByCardSubClass(String cardSubclass) {
		return this.queryForList("findByCardSubClass", cardSubclass);
	}
	
	public Long getCardNum(String strNo, String endNo) {
		Map<String, Object> params = new HashMap<String, Object>();
    	params.put("strNo", strNo);
    	params.put("endNo", endNo);
		return (Long) queryForObject("getCardNum", params);
	}

	public Paginater findPreCard(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findPreCard", params, pageNumber, pageSize);
	}

	public Paginater findByState(List<CardState> cardState, int pageNumber,
			int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
    	String[] types = new String[cardState.size()];
    	for (int i = 0; i < cardState.size(); i++) {
    		types[i] = cardState.get(i).getValue();
    	}
    	params.put("types", types);
		
		return this.queryForPage("findByState", params, pageNumber, pageSize);
	}

	public Paginater findExpireCard(Map<String, Object> params, int pageNumber,
			int pageSize) {
		
		return this.queryForPage("findExpireCard", params, pageNumber, pageSize);
	}

	public Paginater findCardInfoMemb(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardInfoMemb", params, pageNumber, pageSize);
	}
	
	public Paginater findCardInfo(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findCardInfo", params, pageNumber, pageSize);
	}

	public Long getCardNumByState(Map<String, Object> params) {
		return (Long) queryForObject("getCardNumByState", params);
	}

	public Paginater findCardFile(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findCardFile", params, pageNumber, pageSize);
	}

	public List<CardInfo> getCardList(String startCard, String endCard) {
		Map<String, Object> params = new HashMap<String, Object>();
    	params.put("startCard", startCard);
    	params.put("endCard", endCard);
    	return this.queryForList("getCardList", params);
	}

	public CardInfo findByPkWithCheck(Map<String, Object> params) {
		return (CardInfo) this.queryForObject("findByPkWithCheck", params);
	}

	public List<CardInfo> getCardListByCardBin(String strNo) {
		return (List<CardInfo>) this.queryForList("getCardListByCardBin", strNo);
	}

	public List<CardInfo> getCardListByAcctId(String acctId) {
		return (List<CardInfo>) this.queryForList("getCardListByAcctId", acctId);
	}

	public List<CardInfo> getConsmChargeBalDataList(Map<String, Object> params) {
		return this.queryForList("getConsmChargeBalDataList", params);
	}

	public List<CardInfo> findCardFileList(Map<String, Object> params) {
		return this.queryForList("findCardFile", params);
	}

	public Map<String, Object> findCardBanlanceAmt(BigDecimal strAvlbBal,
			BigDecimal endAvlbBal, String cardBranch) {
		Map<String, Object> params = new HashMap<String, Object>();
    	
		params.put("strAvlbBal", strAvlbBal);
    	params.put("endAvlbBal", endAvlbBal);
    	params.put("cardBranch", cardBranch);
    	
    	return (Map<String, Object>) this.queryForObject("findCardBanlanceAmt", params);
	}
	
	public boolean isExsitByCardIssuer(String cardIssuer) {
		return (Long) this.queryForObject("isExsitByCardIssuer", cardIssuer) > 0;
	}
	
	public Map findShineWayQueryCardIds(Map<String, Object> params) {
//		return this.queryForList("findShineWayQueryCardIds", params);
		return this.getSqlRunner().queryForMap(getStatementName("findShineWayQueryCardIds"), params, "card_id".toUpperCase());
	}
	
	public Paginater findShineWayQueryCardInfo(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findShineWayQueryCardInfo", params, pageNumber, pageSize);
	}
	
	public Map<String, Object> findTransByCardIdAndDate(String cardId,
			String consumeStartDate, String consumeEndDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardId", cardId);
		params.put("consumeStartDate", consumeStartDate);
		params.put("consumeEndDate", consumeEndDate);
		
		return (Map<String, Object>) this.queryForObject("findTransByCardIdAndDate", params);
	}

	@Override
	public int updatePhoneNum(String phoneNum, String cardId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phoneNum", phoneNum);
		params.put("updateTime", new Date());
		params.put("cardId", cardId);
		return this.update("updatePhoneNum", params);
	}

	@Override
	public void updateCardStatus(List<String> cardIds, String status) {
		if (CollectionUtils.isEmpty(cardIds)) {
			return;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (String cardId : cardIds) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardStatus", status);
			params.put("updateTime", new Date());
			params.put("cardId", cardId);
			list.add(params);
		}
		super.updateBatch("updateCardStatus", list);
	}
	
	@Override
	public void updateCardStatus(String cardId, String status) {
		this.updateCardStatus(Arrays.asList(cardId), status);
	}
}
