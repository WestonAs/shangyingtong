package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.entity.CardStockInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CardStockInfoDAOImpl extends BaseDAOIbatisImpl implements
		CardStockInfoDAO {

	public String getNamespace() {
		return "CardStockInfo";
	}

	public CardStockInfo findCardStockInfoByCardId(String cardId) {
		return (CardStockInfo) this.queryForObject("findCardStockInfoByCardId", cardId);
	}
	
	public String findFirstCardToSold(String appOrgId,String cardType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appOrgId", appOrgId);
		params.put("cardType", cardType);
		return (String)this.queryForObject("findFirstCardToSold",params);
	}
	
	public Paginater findCardStockInfoPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardStockInfoPage", params, pageNumber, pageSize);
	}
	
	public List<CardStockInfo> findCardStockInfoList(Map<String, Object> params) {
		return this.queryForList("findCardStockInfoPage", params);
	}
	
	public Long findEligible(Map<String, Object> params) {
		return (Long) queryForObject("findEligible", params);
	}
	
	public String getStrNo(Map<String, Object> params) {
		return (String) queryForObject("getStrNo", params);
	}
	
	public Long getCouldReceive(Map<String, Object> params) {
		return (Long) queryForObject("getCouldReceive", params);
	}
	
	public Long getCouldReceiveThisTime(Map<String, Object> params) {
		return (Long) queryForObject("getCouldReceiveThisTime", params);
	}
	
	public Long getCouldReceiveThisTimeFromSell(Map<String, Object> params) {
		return (Long) queryForObject("getCouldReceiveThisTimeFromSell", params);
	}
	
	public boolean isInStock(Map<String, Object> params) {
		return (Long)queryForObject("isInStock", params) > 0;
	}
	
	public void updateStatus(List<String> cardIds, String status) {
		if (CollectionUtils.isEmpty(cardIds)) {
			return;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (String cardId : cardIds) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardStatus", status);
			params.put("cardId", cardId);
			list.add(params);
		}
		super.updateBatch("updateStatus", list);
	}
	
	public void updateStatus(String cardId, String status) {
		this.updateStatus(Arrays.asList(cardId), status);
	}
	
	public int updateStockBatch(Map<String, Object> params) {
		return this.update("updateStockInfoBatch", params);
	}
	
	public void updateStockBatch(List<Map<String, Object>> params) {
		super.updateBatch("updateBatch", params);
	}
	
	public boolean deleteByCardId(String cardId) {
		return this.delete("deleteByCardId", cardId) > 0;
	}
	
	public int deleteByMakeId(String makeId) {
		return this.delete("deleteByMakeId", makeId);
	}
	
	public long getInStockNum(String strCardId, String endCardId, String status, String cardSector) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("strCardId", strCardId);
		params.put("endCardId", endCardId);
		params.put("status", status);
		params.put("appOrgId", cardSector);
		return (Long) this.queryForObject("getInStockNum", params);
	}
	
	@Deprecated
	public String getCantReceiveCardId(String cardSubClass, String cardStatus,
			String strNo, String cardSector) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardSubclass", cardSubClass);
		params.put("cardStatus", cardStatus);
		params.put("strNo", strNo);
		params.put("appOrgId", cardSector);
		
		return (String) this.queryForObject("getCantReceiveCardId", params);
	}
	
	public String getCantReceiveCardIdInStock(String cardSubClass, String strNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardSubclass", cardSubClass);
		params.put("strNo", strNo);
		
		return (String) this.queryForObject("getCantReceiveCardIdInStock", params);
	}
	
	public String getCantReceiveCardIdReceived(String cardSubClass,
			String strNo, String appOrgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardSubclass", cardSubClass);
		params.put("strNo", strNo);
		params.put("appOrgId", appOrgId);
		
		return (String) this.queryForObject("getCantReceiveCardIdReceived", params);
	}
}