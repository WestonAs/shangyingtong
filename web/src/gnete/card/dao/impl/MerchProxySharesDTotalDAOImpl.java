package gnete.card.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchProxySharesDTotalDAO;

@Repository
public class MerchProxySharesDTotalDAOImpl extends BaseDAOIbatisImpl implements MerchProxySharesDTotalDAO {

    public String getNamespace() {
        return "MerchProxySharesDTotal";
    }

	public Paginater findMerchProxySharesDTotal(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return queryForPage("findMerchProxySharesDTotal", params, pageNumber, pageSize);
	}

	public BigDecimal getAmounTotal(Map<String, Object> params) {
		return (BigDecimal)this.queryForObject("getAmounTotal", params);
	}
}