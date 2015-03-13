package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardStockInfo;

import java.util.List;
import java.util.Map;

/**
 * @File: CardStockInfoDAO.java
 * 
 * @description: 卡库存信息表DAO管理类
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-25
 */
public interface CardStockInfoDAO extends BaseDAO {

	/**
	 * 根据条件查询卡库存信息表的分页数据
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>cardId:String 卡号ID</li>
	 *            <li>makeId:String 卡批次</li>
	 *            <li>cardIssuer:String 发卡机构</li>
	 *            <li>appOrgId:String 领卡机构</li>
	 *            <li>cardStatus:String 卡状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardStockInfoPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据条件查询卡库存信息表的分页数据
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>cardId:String 卡号ID</li>
	 *            <li>makeId:String 卡批次</li>
	 *            <li>cardIssuer:String 发卡机构</li>
	 *            <li>appOrgId:String 领卡机构</li>
	 *            <li>cardStatus:String 卡状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<CardStockInfo> findCardStockInfoList(Map<String, Object> params);

	/**
	 * 根据卡号获得卡库存信息对象
	 * 
	 * @param cardId
	 *            卡号ID
	 * @return
	 */
	CardStockInfo findCardStockInfoByCardId(String cardId);

	/**
	 * 找到指定机构、卡种已领卡未售出的第一张卡号
	 * @param appOrgId 领入机构
	 * @param cardType 卡种
	 * @return
	 */
	String findFirstCardToSold(String appOrgId,String cardType);
	
	/**
	 * 查询符合条件的卡数量
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>strNo:String 起始卡号</li>
	 *            <li>endNo:String 结束卡号</li>
	 *            <li>cardStatus:String 卡状态</li>
	 *            </ul>
	 * @return
	 */
	Long findEligible(Map<String, Object> params);

	/**
	 * 得到某一卡子类型的某一状态的最小卡号
	 * 
	 * @param params
	 *            参数包括：
	 *            <ul>
	 *            <li>cardSubclass:String 卡子类型</li>
	 *            <li>cardStatus:String 卡状态</li>
	 *            <li>appOrgId:String 卡的领入机构号</li>
	 *            </ul>
	 * @return
	 */
	String getStrNo(Map<String, Object> params);

	/**
	 * 根据卡子类型得到可领出的卡总数量
	 * 
	 * @param params
	 *            <ul>
	 *            <li>cardSubclass:String 卡子类型</li>
	 *            <li>cardStatus:String 卡状态</li>
	 *            </ul>
	 * @return
	 */
	Long getCouldReceive(Map<String, Object> params);
	
	/**
	 * 根据卡子类型得到本次可领出的卡数量
	 * @param params
	 * @return
	 */
	Long getCouldReceiveThisTime(Map<String, Object> params);
	
	/**
	 * 根据卡子类型和售卡代理号得到本次可从售卡代理那里领出的卡数量
	 * @param params
	 * @return
	 */
	Long getCouldReceiveThisTimeFromSell(Map<String, Object> params);

	/**
	 * 查询起始卡号与结束卡号之间的卡是否存在已经入库了的卡
	 * 
	 * @param params
	 * @return
	 */
	boolean isInStock(Map<String, Object> params);

	/**
	 * 更新卡库存信息表的状态
	 */
	void updateStatus(List<String> cardIds, String status);
	/**
	 * 更新卡库存信息表的状态
	 */
	void updateStatus(String cardId, String status);

	/**
	 * 批量更新卡库存信息表
	 * 
	 * @param params
	 * @return
	 */
	void updateStockBatch(List<Map<String, Object>> params);

	/**
	 * 批量更新卡库存信息表根据起始卡号，结束卡号来更新
	 * 
	 * @param params
	 * @return
	 */
	int updateStockBatch(Map<String, Object> params);
	
	/**
	 * 根据卡号删除记录，卡号是唯一索引
	 * @param cardId
	 * @return
	 */
	boolean deleteByCardId(String cardId);
	
	/**
	 *  根据给定的卡号范围，查找可领取的卡的数量
	 * @param strCardId 起始卡号
	 * @param endCardId 结束卡号
	 * @param status 发卡机构领自己的卡时是“卡在库”，其他情况是“已领卡”
	 * @param cardSector 卡的领出机构，此参数可为空
	 * @return
	 */
	long getInStockNum(String strCardId, String endCardId, String status, String cardSector);
	
	/**
	 * 根据卡类型，卡状态，起始卡号和领出机构号（已领卡状态时），得到本次领卡不连续的最小卡号
	 * @param cardSubclass 卡类型
	 * @param cardStatus 卡库存状态
	 * @param strNo 起始卡号
	 * @param cardSector 领出机构号（已领卡状态时）此参数可为空
	 * @return
	 */@Deprecated
	String getCantReceiveCardId(String cardSubClass, String cardStatus, String strNo, String cardSector);
	 
	/**
	 * 根据卡类型，起始卡号，得到本次领卡不连续的最小卡号(库存状态为在库的卡)
	 * 
	 * @param cardSubclass 卡类型
	 * @param strNo 起始卡号
	 * @return
	 */
	String getCantReceiveCardIdInStock(String cardSubClass, String strNo);
	
	/**
	 * 根据卡类型，起始卡号和领卡机构号（即卡当前所在的机构，卡的领出机构号），得到本次领卡不连续的最小卡号(库存状态为已领卡的卡)
	 * 
	 * @param cardSubclass 卡类型
	 * @param strNo 起始卡号
	 * @param appOrgId 领卡机构号
	 * @return
	 */
	String getCantReceiveCardIdReceived(String cardSubClass, String strNo, String appOrgId);
	
	/**
	 * 根据制卡批次删除卡库存信息(即制卡申请表中的MakeId)
	 * @param makeId
	 * @return
	 */
	int deleteByMakeId(String makeId);
}