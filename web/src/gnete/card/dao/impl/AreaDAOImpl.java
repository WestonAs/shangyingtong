package gnete.card.dao.impl;

import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.entity.Area;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class AreaDAOImpl extends BaseDAOIbatisImpl implements AreaDAO {

    public String getNamespace() {
        return "Area";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<Area> findAll(Map<String, Object> params) {
       return this.queryForList("find",params);	
    }
    
    public List<NameValuePair> findParent() {
    	return this.queryForList("findParent");
    }
    
    public List<NameValuePair> findCityByParent(String parent) {
    	return this.queryForList("findCityByParent", parent);
    }
    
}