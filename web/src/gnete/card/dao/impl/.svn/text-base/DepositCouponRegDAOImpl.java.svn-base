package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepositCouponRegDAO;
import gnete.card.entity.DepositCouponReg;

@Repository
public class DepositCouponRegDAOImpl extends BaseDAOIbatisImpl implements DepositCouponRegDAO {

    public String getNamespace() {
        return "DepositCouponReg";
    }
    
    public Paginater findDepositCouponRegPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findDepositCouponRegPage", params, pageNumber, pageSize);
    }
    
    public List<DepositCouponReg> findDepositCouponList(Map<String, Object> params) {
    	return this.queryForList("findDepositCouponRegPage", params);
    }
    
    public Paginater findDepositCouponCheckPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findDepositCouponCheckPage", params, pageNumber, pageSize);
    }
    
    public List<DepositCouponReg> findDepositCouponCheckList(
    		Map<String, Object> params) {
    	return this.queryForList("findDepositCouponCheckPage", params);
    }
}