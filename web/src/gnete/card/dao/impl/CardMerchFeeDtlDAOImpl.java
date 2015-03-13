package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardMerchFeeDtlDAO;
import gnete.card.entity.CardMerchFeeDtl;

@Repository
public class CardMerchFeeDtlDAOImpl extends BaseDAOIbatisImpl implements CardMerchFeeDtlDAO {

    public String getNamespace() {
        return "CardMerchFeeDtl";
    }

	public Paginater findCardMerchFeeDtl(Map<String, Object> parma,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardMerchFeeDtl", parma, pageNumber, pageSize);
	}

	public List<CardMerchFeeDtl> getCardMerchFeeDtlList(Map<String, Object> parma) {
		return this.queryForList("getCardMerchFeeDtlList", parma);
	}
}