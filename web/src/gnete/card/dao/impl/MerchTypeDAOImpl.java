package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchTypeDAO;
import gnete.card.entity.MerchType;

@Repository
public class MerchTypeDAOImpl extends BaseDAOIbatisImpl implements MerchTypeDAO {

    public String getNamespace() {
        return "MerchType";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<MerchType> findList(Map<String, Object> params) {
    	return this.queryForList("find", params);
    }
    
    public List<MerchType> findByStatus(String status) {
    	return this.queryForList("findByStatus", status);
    }
    
}