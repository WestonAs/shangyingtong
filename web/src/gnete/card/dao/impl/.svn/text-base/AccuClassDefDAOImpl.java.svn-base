package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.entity.AccuClassDef;

@Repository
public class AccuClassDefDAOImpl extends BaseDAOIbatisImpl implements AccuClassDefDAO {

    public String getNamespace() {
        return "AccuClassDef";
    }
    
    public Paginater findAccuClassDef(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findAccuClassDef", params, pageNumber, pageSize);
    }
    
    public List<AccuClassDef> findAccuClassList(Map<String, Object> params) {
    	return this.queryForList("findAccuClassDef", params);
    }
}