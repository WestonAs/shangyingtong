package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.PromtScope;

/**
 * @File: PromtScopeDAO.java
 * 
 * @description: 促销活动范围表DAO类
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public interface PromtScopeDAO extends BaseDAO {

	/**
	 * 查询促销活动范围列表 参数包括
	 * <ul>
	 * <li>promtId:String 促销活动ID</li>
	 * <li>scopeType:String 范围类型</li>
	 * <li>merNo:String 商户号</li>
	 * <li>cardSubclass:String 卡子类型</li>
	 * </ul>
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPromtScope(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据促销活动ID查询促销活动范围列表
	 * @param promtId
	 * @return
	 */
	List<PromtScope> findByPromtId(String promtId);

	/**
	 * 根据促销活动id删除促销活动范围表里的记录
	 * 
	 * @param promtId
	 * @return
	 */
	boolean deleteByPromtId(String promtId);
}