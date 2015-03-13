package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardToMerchDAO;

@Repository
public class CardToMerchDAOImpl extends BaseDAOIbatisImpl implements CardToMerchDAO {

    public String getNamespace() {
        return "CardToMerch";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
}