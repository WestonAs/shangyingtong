package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CostRecord;

import java.util.List;
import java.util.Map;

/**
 * @File: CostRecordDAO.java
 *
 * @description: 单机产品费用记录表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-7
 */
public interface CostRecordDAO extends BaseDAO {
	
	/**
	 * 查找单机产品费用记录信息
	 * 
	 * @param params	查询参数信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找单机产品费用列表
	 * 
	 * @param params
	 * @return
	 */
	List<CostRecord> findList(Map<String, Object> params);
	
	/**
	 * @param param
	 * @return
	 */
	Map findClear2PayPlanMap(String param, String keyProperty);
	
	/**
	 * @param param
	 * @return
	 */
	Map findClear2PayMakeCardMap(String param, String keyProperty);
	
	/**
	 * 批量更新划账文件路径字段
	 * @param costRecordList
	 * @return
	 */
	boolean updateCostRmaFileBatch(List<CostRecord> costRecordList);
}