package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.CardIssuerFeeMSetDAO;

@Repository
public class CardIssuerFeeMSetDAOImpl extends BaseDAOIbatisImpl implements CardIssuerFeeMSetDAO {

    public String getNamespace() {
        return "CardIssuerFeeMSet";
    }

	public Paginater findCardIssuerFeeMSet(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardIssuerFeeMSet", params, pageNumber, pageSize);
	}
}