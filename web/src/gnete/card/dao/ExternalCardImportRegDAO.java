package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @File: ExternalCardImportRegDAO.java
 *
 * @description: 外部卡导入登记薄处理DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-4-14
 */
public interface ExternalCardImportRegDAO extends BaseDAO {
	
	/**
	 * 外部卡导入登记列表
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize);
}