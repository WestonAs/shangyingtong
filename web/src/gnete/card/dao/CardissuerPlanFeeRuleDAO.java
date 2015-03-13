package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardissuerPlanFeeRule;

import java.util.List;
import java.util.Map;

/**
 * @File: CardissuerPlanFeeRuleDAO.java
 *
 * @description: 单机产品发卡机构费用规则表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-23 下午03:51:09
 */
public interface CardissuerPlanFeeRuleDAO extends BaseDAO {

	/**
	 * 查询单机产品发卡机构费用规则列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询单机产品发卡机构费用规则列表
	 * 
	 * @param params
	 * @return
	 */
	List<CardissuerPlanFeeRule> findList(Map<String, Object> params);
	
	int deleteByBranch(String branchCode);
}