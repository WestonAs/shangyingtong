package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @File: WhiteCardInputDAO.java
 *
 * @description: 白卡入库登记操作DAO
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-20
 */
public interface WhiteCardInputDAO extends BaseDAO {
	
	/**
	 * 查找白卡入库登记列表
	 * 
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>branchCode:String 机构代码</li>
	 *            <li>cardType:String 卡类型</li>
	 *            <li>status:String 状态</li>
	 *            <li>startDate:String 起始日期</li>
	 *            <li>endDate:String 结束日期</li>
	 *            <li>cardNo:String 卡号</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findWhiteCardInputPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
}