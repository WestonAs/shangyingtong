package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CenterTermRepFeeMSetDAO;
import gnete.card.entity.CenterTermRepFeeMSet;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CenterTermRepFeeMSetDAOImpl extends BaseDAOIbatisImpl implements CenterTermRepFeeMSetDAO {

    public String getNamespace() {
        return "CenterTermRepFeeMSet";
    }
    public Paginater findCenterTermRepFeeMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCenterTermFeeMSet", params, pageNumber, pageSize);
    }
    
    public List<CenterTermRepFeeMSet> findCenterTermRepFeeMSet(Map<String, Object> params) {
    	return this.queryForList("findCenterTermFeeMSet", params);
    }
}