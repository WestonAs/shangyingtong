package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.BranchBizConfig;

/**
 * @description: ws接口访问控制配置DAO
 */
public interface BranchBizConfigDAO extends BaseDAO {
	/**
	 * 列表页
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);

	BranchBizConfig findByPk(String branchCode, String configType);

	int delete(String branchCode, String configType);


}