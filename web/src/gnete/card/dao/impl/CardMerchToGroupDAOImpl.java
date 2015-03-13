package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.CardMerchToGroupDAO;
import gnete.card.entity.CardMerchToGroup;
import gnete.card.entity.CardMerchToGroupKey;

@Repository
public class CardMerchToGroupDAOImpl extends BaseDAOIbatisImpl implements
		CardMerchToGroupDAO {

	public String getNamespace() {
		return "CardMerchToGroup";
	}

	public List<CardMerchToGroup> findByGroupIdAndBranch(
			Map<String, Object> params) {
		return this.queryForList("findCardMerchToGroup", params);
	}

	public boolean deleteByBranchAndGroupId(CardMerchToGroupKey key) {
		return this.delete("deleteByBranchAndGroupId", key) > 0;
	}
}