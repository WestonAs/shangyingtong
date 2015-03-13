package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PointChgRegDAO;

@Repository
public class PointChgRegDAOImpl extends BaseDAOIbatisImpl implements PointChgRegDAO {

    public String getNamespace() {
        return "PointChgReg";
    }
    
    public Paginater findPointChgReg(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPointChgReg", params, pageNumber, pageSize);
    }
}