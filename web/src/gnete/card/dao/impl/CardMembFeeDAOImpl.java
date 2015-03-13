package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardMembFeeDAO;
import gnete.card.entity.CardMembFee;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CardMembFeeDAOImpl extends BaseDAOIbatisImpl implements CardMembFeeDAO {

	@Override
	public String getNamespace() {
		return "CardMembFee";
	}

	@Override
	public CardMembFee findBy(String branchCode, String cardId, String transType){
		CardMembFee cmf = new CardMembFee();
		cmf.setBranchCode(branchCode);
		cmf.setCardId(cardId);
		cmf.setTransType(transType);
		
		Map params = new HashMap();
		params.put("cardMembFee", cmf);
		
		return (CardMembFee)queryForObject("findCardMembFee", params);
	}
	@Override
	public Paginater findCardMembFee(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findCardMembFee", params, pageNumber, pageSize);
	}
}