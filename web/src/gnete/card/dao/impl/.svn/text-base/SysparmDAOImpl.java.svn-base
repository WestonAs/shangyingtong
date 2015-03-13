package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SysparmDAO;

@Repository
public class SysparmDAOImpl extends BaseDAOIbatisImpl implements SysparmDAO {

    public String getNamespace() {
        return "Sysparm";
    }
    
    public Paginater findSysparm(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findSysparm", params, pageNumber, pageSize);
    }
    
}