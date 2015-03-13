package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardMerchGroupDAO;
import gnete.card.entity.CardMerchGroup;

@Repository
public class CardMerchGroupDAOImpl extends BaseDAOIbatisImpl implements
		CardMerchGroupDAO {

	public String getNamespace() {
		return "CardMerchGroup";
	}

	public Paginater findCardMerchGroup(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardMerchGroup", params, pageNumber,
				pageSize);
	}

	public List<CardMerchGroup> findCardMerchGroupList(
			Map<String, Object> params) {
		return this.queryForList("findCardMerchGroup", params);
	}
}