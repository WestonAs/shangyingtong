package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepositCouponBatRegDAO;
import gnete.card.entity.DepositCouponBatReg;

@Repository
public class DepositCouponBatRegDAOImpl extends BaseDAOIbatisImpl implements DepositCouponBatRegDAO {

    public String getNamespace() {
        return "DepositCouponBatReg";
    }
    
    public Paginater findDepositCouponBatPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findDepositCouponBatList", params, pageNumber, pageSize);
    }
    
    public List<DepositCouponBatReg> findDepositCouponBatList(
    		Map<String, Object> params) {
    	return this.queryForList("findDepositCouponBatList", params);
    }
    
    public int updateStatusByDepositBatchId(Map<String, Object> params) {
    	return this.update("updateStatusByDepositBatchId", params);
    }
    
}