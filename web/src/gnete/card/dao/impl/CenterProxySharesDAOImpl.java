package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CenterProxySharesDAO;

@Repository
public class CenterProxySharesDAOImpl extends BaseDAOIbatisImpl implements CenterProxySharesDAO {

    public String getNamespace() {
        return "CenterProxyShares";
    }
    
    public Paginater findCenterProxyShares(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCenterProxyShares", params, pageNumber, pageSize);
    }
}