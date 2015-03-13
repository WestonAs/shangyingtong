package gnete.card.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SaleProxyRtnDTotalDAO;

@Repository
public class SaleProxyRtnDTotalDAOImpl extends BaseDAOIbatisImpl implements SaleProxyRtnDTotalDAO {

    public String getNamespace() {
        return "SaleProxyRtnDTotal";
    }

	public Paginater findSaleProxyRtnDTotal(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findSaleProxyRtnDTotal", params, pageNumber, pageSize);
	}

	public BigDecimal getAmounTotal(Map<String, Object> params) {
		return (BigDecimal)this.queryForObject("getAmounTotal", params);
	}

    
    
	
}