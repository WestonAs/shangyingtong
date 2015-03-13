package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardissuerPlanFee;

import java.util.List;
import java.util.Map;

/**
 * @File: CardissuerPlanFeeDAO.java
 *
 * @description: 单机产品发卡机构套餐费用表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: 
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-23 下午03:20:22
 */
public interface CardissuerPlanFeeDAO extends BaseDAO {
	
	/**
	 * 查询单机产品发卡机构套餐费用列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询单机产品发卡机构套餐费用列表
	 * 
	 * @param params
	 * @return
	 */
	List<CardissuerPlanFee> findList(Map<String, Object> params);
}