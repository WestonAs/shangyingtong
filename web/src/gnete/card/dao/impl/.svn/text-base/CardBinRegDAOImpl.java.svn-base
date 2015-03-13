package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardBinRegDAO;

@Repository
public class CardBinRegDAOImpl extends BaseDAOIbatisImpl implements CardBinRegDAO {

    public String getNamespace() {
        return "CardBinReg";
    }

	public Paginater findCardBinReg(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findCardBinReg", params, pageNumber, pageSize);
	}
}