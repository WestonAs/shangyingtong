package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CenterTermFeeMSetDAO;
import gnete.card.entity.CenterTermFeeMSet;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CenterTermFeeMSetDAOImpl extends BaseDAOIbatisImpl implements CenterTermFeeMSetDAO {

    public String getNamespace() {
        return "CenterTermFeeMSet";
    }
    public Paginater findCenterTermFeeMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCenterTermFeeMSet", params, pageNumber, pageSize);
    }
    
    public List<CenterTermFeeMSet> findCenterTermFeeMSet(Map<String, Object> params) {
    	return this.queryForList("findCenterTermFeeMSet", params);
    }
}