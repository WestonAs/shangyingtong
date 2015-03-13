package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardGroup;
import java.util.List;
import java.util.Map;

/**
 * @File: CardGroupDAO.java
 *
 * @description: 发卡机构集团表DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-5-25
 */
public interface CardGroupDAO extends BaseDAO {
	
	/** 
	 * 查找发卡机构集团关系列表
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>branchCode:String 发卡机构号</li>
	 * 	<li>groupId:String 集团号</li>
	 * 	<li>status:String 状态</li>
	 * 	<li>startDate:Date 开始时间</li>
	 * 	<li>endDate:Date 结束时间</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据集团号查找其所属的发卡机构列表
	 * @param groupId
	 * @return
	 */
	List<CardGroup> getBranchList(String groupId);

}