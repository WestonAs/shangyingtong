package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @File: AppRegDAO.java
 * 
 * @description: 卡入库登记表操作DAO
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-24
 */
public interface AppRegDAO extends BaseDAO {

	/**
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>cardIssuer:String 发卡机构</li>
	 *            <li>appOrgId:String 领卡机构</li>
	 *            <li>flag:String 发生方式</li>
	 *            <li>startDate:String 起始日期</li>
	 *            <li>endDate:String 结束日期</li>
	 *            <li>status:String 状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findAppRegPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 查看卡号是否已经入库或返库
	 包括:
	 *            <ul>
	 *            <li>cardNo:String 卡号</li>
	 *            <li>flag:String 发生方式</li>
	 *            <li>status:String 状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param parmas
	 * @return
	 */
	boolean isInOrWithDrawCardNo(Map<String, Object> parmas);

}