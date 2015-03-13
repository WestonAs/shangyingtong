package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface BranchSharesDAO extends BaseDAO {
	/**
	 * 查询运营中心与分支机构分润参数
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findBranchShares(Map<String, Object> params, int pageNumber,
    		int pageSize);
}