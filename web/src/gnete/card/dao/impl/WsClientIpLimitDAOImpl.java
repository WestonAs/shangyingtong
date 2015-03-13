package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WsClientIpLimitDAO;

@Repository
public class WsClientIpLimitDAOImpl extends BaseDAOIbatisImpl implements WsClientIpLimitDAO {

    public String getNamespace() {
        return "WsClientIpLimit";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
}