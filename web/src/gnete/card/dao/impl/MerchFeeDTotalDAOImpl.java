package gnete.card.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchFeeDTotalDAO;

@Repository
public class MerchFeeDTotalDAOImpl extends BaseDAOIbatisImpl implements MerchFeeDTotalDAO {

    public String getNamespace() {
        return "MerchFeeDTotal";
    }

	public Paginater findMerchFeeDTotal(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMerchFeeDTotal", params, pageNumber, pageSize);
	}

	public BigDecimal getAmounTotal(Map<String, Object> params) {
		return (BigDecimal) this.queryForObject("getAmounTotal", params);
	}

	public BigDecimal getMerchProxyFeeTotal(Map<String, Object> params) {
		return (BigDecimal) this.queryForObject("getMerchProxyFeeTotal", params);
	}
}