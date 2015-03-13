package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepositPointRegDAO;
import gnete.card.entity.DepositPointReg;

@Repository
public class DepositPointRegDAOImpl extends BaseDAOIbatisImpl implements DepositPointRegDAO {

    public String getNamespace() {
        return "DepositPointReg";
    }
    
    public Paginater findDepositPointRegPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findDepositPointRegPage", params, pageNumber, pageSize);
    }
    
    public List<DepositPointReg> findDepositPointList(Map<String, Object> params) {
    	return this.queryForList("findDepositPointRegPage", params);
    }
    
    public Paginater findDepositPointCheckPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findDepositPointCheckPage", params, pageNumber, pageSize);
    }
    
    public List<DepositPointReg> findDepositPointCheckList(
    		Map<String, Object> params) {
    	return this.queryForList("findDepositPointCheckPage", params);
    }
}