package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.CardMerchFeeDtl;

public interface CardMerchFeeDtlDAO extends BaseDAO {
	public Paginater findCardMerchFeeDtl(Map<String, Object> parma, int pageNumber,
    		int pageSize);
	
	/**
	 * 根据费率规则ID获得明细中的分段列表
	 * @param startCard
	 * @param endCard
	 * @return
	 */
	List<CardMerchFeeDtl> getCardMerchFeeDtlList(Map<String, Object> parma);
}