package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.DrawDef;

/**
 * @File: DrawDefDAO.java
 * 
 * @description: 抽奖活动定义表管理DAO
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public interface DrawDefDAO extends BaseDAO {

	/**
	 * 查找制卡申请记录列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>issId:String 发行机构编号</li>
	 *            <li>drawId:String 抽奖活动ID</li>
	 *            <li>drawName:String 抽奖活动名称</li>
	 *            <li>drawType:String 抽奖方式</li>
	 *            <li>transType:String 交易类型</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDrawDefPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据发行机构代码查询制卡登记列表
	 * @param issId
	 * @return
	 */
	List<DrawDef> findByIssId(String issId);
}