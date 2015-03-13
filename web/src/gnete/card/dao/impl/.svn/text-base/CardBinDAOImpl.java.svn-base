package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardBinDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.state.CardBinState;

@Repository
public class CardBinDAOImpl extends BaseDAOIbatisImpl implements CardBinDAO {

	public String getNamespace() {
		return "CardBin";
	}

	public Paginater findCardBin(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return queryForPage("findCardBin", params, pageNumber, pageSize);
	}

	public List<CardBin> findCardBin(Map<String, Object> params) {
		return queryForList("findCardBin", params);
	}

	public List<CardBin> findCardBin(String cardIssuer, String cardType, String state) {
		if (StringUtils.isBlank(cardType) || StringUtils.isBlank(cardType))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", cardIssuer); // 机构号
		params.put("cardType", cardType);
		params.put("status", state);
		return findCardBin(params);
	}
	
	public List<CardBin> findCardSubclass(Map<String, Object> params) {
		return queryForList("findCardSubclass", params);
	}
}