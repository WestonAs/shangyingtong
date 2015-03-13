package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface MerchProxySharesDAO extends BaseDAO {
	
	/**
	 * 查询
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findMerchProxyShares(Map<String, Object> params, int pageNumber,
    		int pageSize);
}