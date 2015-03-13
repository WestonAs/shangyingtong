package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardissuerPlan;

import java.util.List;
import java.util.Map;

/**
 * @File: CardissuerPlanDAO.java
 *
 * @description: 发卡机构套餐关系表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-7
 */
public interface CardissuerPlanDAO extends BaseDAO {
	
	/**
	 * 查找发卡机构套餐关联表
	 * 
	 * @param params	查询参数信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找发卡机构套餐关联列表
	 * 
	 * @param params
	 * @return
	 */
	List<CardissuerPlan> findList(Map<String, Object> params);
}