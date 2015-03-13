package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.PointBalDAO;
import gnete.card.entity.PointBal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PointBalDAOImpl extends BaseDAOIbatisImpl implements PointBalDAO {

    public String getNamespace() {
        return "PointBal";
    }
    
    public List findPointBal(Map<String, Object> params) {
    	return this.queryForList("findPointBal", params);
    }

	public List findPointBalAval(Map<String, Object> params){
		return this.queryForList("findPointBalAval", params);
	}

	public List<PointBal> getPointBalList(Map<String, Object> params) {
		return this.queryForList("getPointBalList", params);
	}

	public Paginater getPointBalList(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("getPointBalList", params, pageNumber, pageSize);
	}

}