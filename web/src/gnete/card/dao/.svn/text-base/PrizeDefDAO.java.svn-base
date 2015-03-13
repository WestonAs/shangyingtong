package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.PrizeDef;

/**
 * @File: PrizeDefDAO.java
 * 
 * @description: 奖项定义处理DAO
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public interface PrizeDefDAO extends BaseDAO {

	/**
	 * 查找奖项定义记录列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>prizeName:String 奖项名称</li>
	 *            <li>jinstType:String 联名机构类型</li>
	 *            <li>jinstId:String 联名机构编号</li>
	 *            <li>awdType:String 奖品类型</li>
	 *            <li>classId:String 子类型ID</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPrizeDefPage(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 抽奖活动ID查询奖项定义列表
	 * 
	 * @param drawId
	 * @return
	 */
	List<PrizeDef> findByDrawId(String drawId);
}