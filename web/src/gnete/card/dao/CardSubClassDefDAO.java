package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardSubClassDef;

import java.util.List;
import java.util.Map;

/**
 * @File: CardSubClassDefDAO.java
 *
 * @description: 卡子类管理DAO
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-3
 */
public interface CardSubClassDefDAO extends BaseDAO {

	
	/**
	 * 查找卡子类型列表
	 * 
	 * @param params 参数
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardSubClassDef(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 查询子类型号是否存在
	 * 
	 * @param cardSubClass 卡子类型号
	 * @return
	 */
	boolean isExistCardSubClass(String cardSubClass);
	
	/**
	 * 查询某机构下的卡子类型
	 * @param cardIssuer 机构代码
	 * @return
	 */
	List<CardSubClassDef> findCardSubClassDefByBranNo(String cardIssuer);
	
	/**
	 * 查询指定条件的卡子类型列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>cardIssuer:String 机构代码</li>
	 *            <li>cardClass:String 卡类型</li>
	 *            <li>mustExpirDate:String 绝对失效日期</li>
	 *            </ul>
	 * @return
	 */
	List<CardSubClassDef> findCardSubClass(Map<String, Object> params);
	
	/**
	 * 卡子类型名是否已经存在 
	 * @param cardSubClassName
	 * @return
	 */
	boolean isExsitCardSubClassName(String cardSubClassName);
}