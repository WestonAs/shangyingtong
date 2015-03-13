package gnete.card.dao;

import flink.util.Paginater;

/**
 * @description: 外部卡导入处理结果 DAO
 */
public interface ExternalCardImportResultDAO {

	/**
	 * 外部卡导入登记列表
	 * 
	 * @param impId 外部卡导入薄登记id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Long impId, int pageNumber, int pageSize);
}