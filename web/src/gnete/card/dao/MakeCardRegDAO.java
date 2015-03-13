package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MakeCardReg;

/**
 * @File: MakeCardRegDAO.java
 * 
 * @description: 制卡登记管理DAO
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-6
 */
public interface MakeCardRegDAO extends BaseDAO {

	/**
	 * 制卡登记列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>branchCode:String 机构代码</li>
	 *            <li>makeId:String 制卡登记ID</li>
	 *            <li>cardSubtype:String 卡子类型</li>
	 *            <li>makeName:String 制卡名称</li>
	 *            <li>picStatus:String 当前卡样图案状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findMakeCardReg(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 查找制卡登记列表
	 * @param params
	 * @return
	 */
	List<MakeCardReg> findList(Map<String, Object> params);

	/**
	 * 查找某一部门的制卡登记列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>branchCode:String 机构代码</li>
	 *            <li>picStatus:String 当前卡样图案状态</li>
	 *            </ul>
	 * @return
	 */
	List<MakeCardReg> findByBranchCode(Map<String, Object> params);

	/**
	 * 根据卡子类型查询制卡登记对象列表
	 * 
	 * @param cardSubtype
	 * @return
	 */
	List<MakeCardReg> findByCardSubtype(String cardSubtype);

	/**
	 * 卡样名称是否已经存在
	 * 
	 * @param makeName
	 * @return
	 */
	boolean isExsitMakeName(String makeName);
}