package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.WsClientIpLimitDtl;

/**
 * @description: ws接口访问控制配置DAO
 */
public interface WsClientIpLimitDtlDAO extends BaseDAO {
	/**
	 * 列表页
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);

	WsClientIpLimitDtl findByPk(String branchCode, String ip);

	void delete(String branchCode, String ip);


}