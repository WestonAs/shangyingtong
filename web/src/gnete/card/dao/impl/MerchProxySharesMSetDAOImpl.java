package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchProxySharesMSetDAO;

@Repository
public class MerchProxySharesMSetDAOImpl extends BaseDAOIbatisImpl implements MerchProxySharesMSetDAO {

    public String getNamespace() {
        return "MerchProxySharesMSet";
    }
    
    public Paginater findMerchProxySharesMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findMerchProxySharesMSet", params, pageNumber, pageSize);
    }
}