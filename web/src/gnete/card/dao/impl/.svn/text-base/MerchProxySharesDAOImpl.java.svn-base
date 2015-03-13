package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchProxySharesDAO;
@Repository
public class MerchProxySharesDAOImpl extends BaseDAOIbatisImpl implements MerchProxySharesDAO {

    public String getNamespace() {
        return "MerchProxyShares";
    }
    
    public Paginater findMerchProxyShares(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findMerchProxyShares", params, pageNumber, pageSize);
    }
}