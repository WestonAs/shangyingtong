package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardBin;

import java.util.List;
import java.util.Map;

/**
 * @File: CardBinDAO.java
 *
 * @description: 卡BIN管理DAO类
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-12
 */
public interface CardBinDAO extends BaseDAO {
	
	/**
	 * 当前发卡机构所属的卡BIN列表
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>binNo:String 卡BIN号码</li>
	 * 	<li>binName:String 卡BIN名称</li>
	 * 	<li>cardIssuer:String 发卡机构</li>
	 * 	<li>cardType:String 卡类型</li>
	 * 	<li>status:String 卡BIN状态</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardBin(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据查询参数查询卡BIN记录 
	 * 
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>binNo:String 卡BIN号码</li>
	 * 	<li>binName:String 卡BIN名称</li>
	 * 	<li>cardIssuer:String 机构代码</li>
	 * 	<li>cardType:String 卡类型</li>
	 * 	<li>state:String 卡BIN状态</li>
	 * </ul>
	 * @return
	 */
	List<CardBin> findCardBin(Map<String, Object> params);
	
	/**
	 * 查询卡BIN列表
	 * @param cardIssuer 发卡机构
	 * @param cardType 卡种
	 * @param state 卡BIN状态
	 * @return 如果cardIssuer或cardType为空，则直接返回null；
	 */
	List<CardBin> findCardBin(String cardIssuer, String cardType, String state);

	/**
	 * 根据查询参数查询卡BIN记录 
	 * 
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>cardIssuer:String 机构代码</li>
	 * </ul>
	 * @return
	 */
	List<CardBin> findCardSubclass(Map<String, Object> params);
}