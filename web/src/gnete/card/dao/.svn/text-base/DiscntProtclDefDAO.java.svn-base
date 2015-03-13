package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface DiscntProtclDefDAO extends BaseDAO {

	/**
	 * 查询折扣规则定义列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDiscntProtclDefPage(Map<String, Object> params,
			int pageNumber, int pageSize);
	
	/**
	 * 根据折扣子类型，联名机构类型，联名机构号更新规则状态
	 * @param params
	 * @return
	 */
	int updateStatus(Map<String, Object> params);
}