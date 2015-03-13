package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.BrushSet;

import java.util.Map;

/**
 * @File: BrushSetDAO.java
 *
 * @description: 即刷即中设置表处理DAO
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-19
 */
public interface BrushSetDAO extends BaseDAO {
	
	int deleteByPk(Long id);
	
	int deleteByDrawPrizeNo(Map params);
	
	BrushSet findByDrawPrizeNo(Map params);
	
	int updateByDrawPrizeNo(Map params);
	
	/**
	 * 查询即刷即中设置记录列表
	 * 
	 * @param params
	 * 包括:
	 * <ul>
	 * 	<li>drawId:String 抽奖活动ID</li>
	 * 	<li>awdDate:String 中奖日期</li>
	 * 	<li>awdSeq:String 中奖当天序号</li>
	 * 	<li>prizeNo:String 奖项编号</li>
	 * 	<li>awdTicketNo:String 中奖奖票号</li>
	 * </ul> 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findBrushSet(Map<String, Object> params, int pageNumber, int pageSize);
}