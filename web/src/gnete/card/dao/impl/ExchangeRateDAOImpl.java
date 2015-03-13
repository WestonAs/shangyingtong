package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.ExchangeRateDAO;

@Repository
public class ExchangeRateDAOImpl extends BaseDAOIbatisImpl implements ExchangeRateDAO {

    public String getNamespace() {
        return "ExchangeRate";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
}