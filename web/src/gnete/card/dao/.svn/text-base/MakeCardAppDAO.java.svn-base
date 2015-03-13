package gnete.card.dao;

import flink.util.Paginater;

import java.util.Map;

/**
 * @File: MakeCardAppDAO.java
 * 
 * @description: 制卡申请DAO管理类
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-10
 */
public interface MakeCardAppDAO extends BaseDAO {

	/**
	 * 查找制卡申请记录列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>branchCode:String 机构代码</li>
	 *            <li>makeId:String 制卡登记ID</li>
	 *            <li>appId:String 制卡申请ID</li>
	 *            <li>cardSubtype:String 卡子类型</li>
	 *            <li>makeName:String 制卡名称</li>
	 *            <li>picStatus:String 当前卡样图案状态</li>
	 *            <li>id:Long[] id</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findMakeCardAppPage(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 制卡申请ID是否已经存在
	 * 
	 * @param params
	 *  包括:
	 *            <ul>
	 *            <li>appId:String 制卡申请ID</li>
	 *            <li>makeId:String 制卡登记ID</li>
	 *            </ul>
	 * @return
	 */
	boolean isExistAppId(Map<String, Object> params);
}