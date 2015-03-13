package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface BranchSellRegDAO extends BaseDAO {

	/**
	 * 查询机构调整登记
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
}