package gnete.card.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchTransDSetDAO;

@Repository
public class MerchTransDSetDAOImpl extends BaseDAOIbatisImpl implements MerchTransDSetDAO {

    public String getNamespace() {
        return "MerchTransDSet";
    }
    
    public Paginater findMerchTrans(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findMerchTrans", params, pageNumber, pageSize);
    }
    
    public Paginater findCardSale(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCardSale", params, pageNumber, pageSize);
    }

	public Paginater findMerchTransDSet(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMerchTransDSet", params, pageNumber, pageSize);
	}
	
	public Paginater findSaleDepositTransDSet(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findSaleDepositTransDSet", params, pageNumber, pageSize);
	}

	public Object findByPkWithName(Map<String, Object> params) {
		return this.queryForObject("findByPkWithName", params);
	}

	public BigDecimal getAmounTotal(Map<String, Object> params) {
		return (BigDecimal) this.queryForObject("getAmounTotal", params);
	}

	public BigDecimal getSaleDepositAmounTotal(Map<String, Object> params) {
		return (BigDecimal) this.queryForObject("getSaleDepositAmounTotal", params);
	}
}