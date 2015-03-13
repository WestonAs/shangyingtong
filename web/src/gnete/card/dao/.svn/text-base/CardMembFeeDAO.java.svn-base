package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardMembFee;

import java.util.Map;

public interface CardMembFeeDAO extends BaseDAO {
	
	public Paginater findCardMembFee(Map<String, Object> params, int pageNumber, int pageSize);

	CardMembFee findBy(String branchCode, String cardId, String transType);
	
}