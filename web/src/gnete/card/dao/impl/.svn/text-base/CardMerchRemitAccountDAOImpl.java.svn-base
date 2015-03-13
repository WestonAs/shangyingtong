package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardMerchRemitAccountDAO;

@Repository
public class CardMerchRemitAccountDAOImpl extends BaseDAOIbatisImpl implements CardMerchRemitAccountDAO {

    public String getNamespace() {
        return "CardMerchRemitAccount";
    }

	public Paginater findRemitAccount(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findRemitAccount", params, pageNumber, pageSize);
	}
}