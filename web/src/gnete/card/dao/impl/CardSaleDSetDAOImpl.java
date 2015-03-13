package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardSaleDSetDAO;

@Repository
public class CardSaleDSetDAOImpl extends BaseDAOIbatisImpl implements CardSaleDSetDAO {

    public String getNamespace() {
        return "CardSaleDSet";
    }
    
    public Paginater findCardSaleDSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCardSaleDSet", params, pageNumber, pageSize);
    }
}