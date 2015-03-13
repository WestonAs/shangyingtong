package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardRiskBalanceDAO;

@Repository
public class CardRiskBalanceDAOImpl extends BaseDAOIbatisImpl implements CardRiskBalanceDAO {

    public String getNamespace() {
        return "CardRiskBalance";
    }

	public Paginater findCardRiskBalance(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardRiskBalance", params, pageNumber, pageSize);
	}
}