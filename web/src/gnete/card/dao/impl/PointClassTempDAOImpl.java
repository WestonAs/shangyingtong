package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PointClassTempDAO;
import gnete.card.entity.PointClassTemp;
@Repository
public class PointClassTempDAOImpl extends BaseDAOIbatisImpl implements PointClassTempDAO {

    public String getNamespace() {
        return "PointClassTemp";
    }
    public Paginater findPointClassTemp(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPointClassTemp", params, pageNumber, pageSize);
    }
	public List<PointClassTemp> findList(Map<String, Object> params) {
		return this.queryForList("findPointClassTemp", params);
	}
    
}