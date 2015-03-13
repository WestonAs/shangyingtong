package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchInfoRegDAO;
import gnete.card.entity.MerchInfoReg;

@Repository
public class MerchInfoRegDAOImpl extends BaseDAOIbatisImpl implements MerchInfoRegDAO {

    public String getNamespace() {
        return "MerchInfoReg";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<MerchInfoReg> find(Map<String, Object> params) {
    	return this.queryForList("find", params);
    }
}