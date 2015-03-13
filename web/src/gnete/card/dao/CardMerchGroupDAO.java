package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardMerchGroup;

/**
 * @File: CardMerchGroupDAO.java
 * 
 * @description: 发卡机构的商户组表管理DAO
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-3
 */
public interface CardMerchGroupDAO extends BaseDAO {

	/**
	 * 发卡机构的商户组列表显示
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>groupId:String 商户组代码</li>
	 *            <li>groupName:String 商户组名</li>
	 *            <li>feeType:String 手续费收取方式</li>
	 *            <li>status:String 状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardMerchGroup(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 发卡机构的商户组列表显示
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>groupId:String 商户组代码</li>
	 *            <li>groupName:String 商户组名</li>
	 *            <li>feeType:String 手续费收取方式</li>
	 *            <li>status:String 状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<CardMerchGroup> findCardMerchGroupList(Map<String, Object> params);
}