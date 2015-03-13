package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchFeeMSetDAO;

@Repository
public class MerchFeeMSetDAOImpl extends BaseDAOIbatisImpl implements MerchFeeMSetDAO {

    public String getNamespace() {
        return "MerchFeeMSet";
    }
    public Paginater findMerchFeeMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findMerchFeeMSet", params, pageNumber, pageSize);
    }
}