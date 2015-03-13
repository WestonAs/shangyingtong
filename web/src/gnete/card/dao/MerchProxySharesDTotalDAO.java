package gnete.card.dao;

import java.math.BigDecimal;
import java.util.Map;

import flink.util.Paginater;

public interface MerchProxySharesDTotalDAO extends BaseDAO {
	
	public Paginater findMerchProxySharesDTotal(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	public BigDecimal getAmounTotal(Map<String, Object> params); 
}