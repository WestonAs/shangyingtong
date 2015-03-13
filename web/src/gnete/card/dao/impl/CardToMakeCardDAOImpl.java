package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardToMakeCardDAO;

@Repository
public class CardToMakeCardDAOImpl extends BaseDAOIbatisImpl implements CardToMakeCardDAO {

    public String getNamespace() {
        return "CardToMakeCard";
    }

	public Paginater findCardToMakeCard(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardToMakeCard", params, pageNumber, pageSize);
	}
}