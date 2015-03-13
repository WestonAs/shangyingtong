package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CenterProxySharesMSetDAO;

@Repository
public class CenterProxySharesMSetDAOImpl extends BaseDAOIbatisImpl implements CenterProxySharesMSetDAO {

    public String getNamespace() {
        return "CenterProxySharesMSet";
    }
    
    public Paginater findCenterProxySharesMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCenterProxySharesMSet", params, pageNumber, pageSize);
    }
}