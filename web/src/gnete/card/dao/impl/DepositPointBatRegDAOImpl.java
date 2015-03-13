package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepositPointBatRegDAO;
import gnete.card.entity.DepositPointBatReg;

@Repository
public class DepositPointBatRegDAOImpl extends BaseDAOIbatisImpl implements DepositPointBatRegDAO {

    public String getNamespace() {
        return "DepositPointBatReg";
    }
    
    public Paginater findDepositPointBatPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findDepositPointBatList", params, pageNumber, pageSize);
    }
    
    public List<DepositPointBatReg> findDepositPointBatList(
    		Map<String, Object> params) {
    	return this.queryForList("findDepositPointBatList", params);
    }
    
    public int updateStatusByDepositBatchId(Map<String, Object> params) {
    	return this.update("updateStatusByDepositBatchId", params);
    }
    
}