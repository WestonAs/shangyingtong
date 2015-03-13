package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardRiskChgDAO;

@Repository
public class CardRiskChgDAOImpl extends BaseDAOIbatisImpl implements CardRiskChgDAO {

    public String getNamespace() {
        return "CardRiskChg";
    }

	public Paginater findCardRiskChg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardRiskChg", params, pageNumber, pageSize);
	}
}