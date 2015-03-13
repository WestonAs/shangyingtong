package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.PromtDef;

import java.util.List;
import java.util.Map;

/**
 * @File: PromtDefDAO.java
 *
 * @description: 促销活动定义表操作的DAO
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-14
 */
public interface PromtDefDAO extends BaseDAO {
	
	/**
	 * 查询促销活动定义列表
	 * 
	 * @param params
	 * 参数包括
	 * <ul>
	 * 	<li>promtId:String 促销活动id</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPromtDef(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找符合条件的促销活动对象列表
	 * 
	 * @param params
	 * @return
	 */
	List<PromtDef> findPromtList(Map<String, Object>params);
}