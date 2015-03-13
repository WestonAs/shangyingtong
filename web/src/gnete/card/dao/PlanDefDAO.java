package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.PlanDef;

import java.util.List;
import java.util.Map;

/**
 * @File: PlanDefDAO.java
 *
 * @description: 套餐定义表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-7
 */
public interface PlanDefDAO extends BaseDAO {
	
	/**
	 * 查找套餐信息
	 * 
	 * @param params	查询参数信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找套餐列表
	 * 
	 * @param params
	 * @return
	 */
	List<PlanDef> findList(Map<String, Object> params);
	
	/**
	 * 同一运营分支机构的套餐不能重复套餐名称是否已经存在
	 * @param styleName
	 * @return
	 */
	boolean isExsitModelName(String modelName, String branchCode);
}