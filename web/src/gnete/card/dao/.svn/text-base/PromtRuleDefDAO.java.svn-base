package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.PromtRuleDef;

/**
 * @File: PromtRuleDefDAO.java
 * 
 * @description: 促销规则定义表DAO类
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public interface PromtRuleDefDAO extends BaseDAO {

	/**
	 * 查询促销规则定义列表
	 * 
	 * @param params
	 *            参数包括
	 *            <ul>
	 *            <li>promtRuleId:String 促销规则ID</li>
	 *            <li>amtType:String 金额类型</li>
	 *            <li>promtRuleType:String 促销规则类型</li>
	 *            <li>ruleStatus:String 规则状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPromtRuleDef(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 审核促销规则定义
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPromtRuleDefCheck(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 根据促销活动ID查询促销规则定义列表
	 * 
	 * @param promtId
	 * @return
	 */
	List<PromtRuleDef> findByPromtId(String promtId);

	/**
	 * 根据促销活动Id删除促销规则定义表
	 * 
	 * @param promtId
	 * @return
	 */
	boolean deleteByPromtId(String promtId);
}