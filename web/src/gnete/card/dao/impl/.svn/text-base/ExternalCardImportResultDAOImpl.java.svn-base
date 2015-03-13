package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.ExternalCardImportResultDAO;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public class ExternalCardImportResultDAOImpl extends BaseDAOIbatisImpl implements ExternalCardImportResultDAO {

    public String getNamespace() {
        return "ExternalCardImportResult";
    }
    
    public Paginater findPage(Long impId, int pageNumber,
    		int pageSize) {
    	HashMap<String, Long> map = new HashMap<String, Long>();
    	map.put("impId", impId);
    	return this.queryForPage("findPage", map, pageNumber, pageSize);
    	
    }
}