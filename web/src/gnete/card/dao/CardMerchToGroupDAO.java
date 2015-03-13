package gnete.card.dao;

import gnete.card.entity.CardMerchToGroup;
import gnete.card.entity.CardMerchToGroupKey;

import java.util.List;
import java.util.Map;

/**
 * @File: CardMerchToGroupDAO.java
 * 
 * @description: 发卡机构的商户组关系表管理DAO
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-3
 */
public interface CardMerchToGroupDAO extends BaseDAO {

	/**
	 * 根据发卡机构和商户组代码查询发卡机构商户组关系列表
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>groupId:String 商户组代码</li>
	 *            <li>branchCode:String 发卡机构</li>
	 *            </ul>
	 * @return
	 */
	List<CardMerchToGroup> findByGroupIdAndBranch(Map<String, Object> params);
	
	/**
	 * 根据发卡机构和商户组代码删除发卡机构商户组关系表
	 * @param key
	 * @return
	 */
	boolean deleteByBranchAndGroupId(CardMerchToGroupKey key);
}