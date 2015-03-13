package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface LogoConfigDAO extends BaseDAO {
	
	/**
	 * logo配置列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findLogoConfigPage(Map<String, Object> params, int pageNumber, int pageSize);
}