package gnete.card.dao;

import java.util.List;
import java.util.Map;

import gnete.card.entity.CardNoAssign;

/**
 * @File: CardNoAssignDAO.java
 *
 * @description: 发卡机构卡号分配表操作DAO
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-13
 */
public interface CardNoAssignDAO extends BaseDAO {
	
	/**
	 * 根据给定的参数查询发卡机构卡号分配表列表
	 * 
	 * @param params
	 * 包括:
	 * <ul>
	 * 	<li>branchCode:String 机构代码</li>
	 * 	<li>binNo:String 卡BIN号码</li>
	 * 	<li>strCardNo:Integer 卡起始号</li>
	 * 	<li>endCardNo:Integer 卡结束号</li>
	 * 	<li>useCardNo:Integer 使用到的卡号</li>
	 * 	<li>Status:String 状态</li>
	 * </ul>
	 * @return
	 */
	List<CardNoAssign> findCardNoAssign(Map<String, Object> params);
	
	/**
	 * 查询已使用到的卡号
	 * 
	 * @param params
	 * 包括:
	 * <ul>
	 * 	<li>branchCode:String 机构代码</li>
	 * 	<li>binNo:String 卡BIN号码</li>
	 * </ul>
	 * @return
	 */
	Long findUseCardNo(Map<String, Object> params);
	
	/**
	 * 查询当前卡号记录是否已经被分配
	 * 
	 * @param params
	 * 包括:
	 * <ul>
	 * 	<li>branchCode:String 机构代码</li>
	 * 	<li>binNo:String 卡BIN号码</li>
	 * 	<li>cardNo:Long 输入的卡号</li>
	 * </ul>
	 * @return
	 */
	Long isExistCardNo(Map<String, Object> params);
}