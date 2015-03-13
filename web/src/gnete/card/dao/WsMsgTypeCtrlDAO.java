package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @description: ws接口访问控制配置DAO
 */
public interface WsMsgTypeCtrlDAO extends BaseDAO {
	
	/**
	 * 列表页
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
}