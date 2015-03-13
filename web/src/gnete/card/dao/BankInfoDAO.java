package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @File: BankInfoDAO.java
 *
 * @description: 银行表操作dao
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-7-5
 */
public interface BankInfoDAO extends BaseDAO {
	
	/**
	 * 查找银行信息
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>bankType:String 银行行别</li>
	 * 	<li>province:String 省代码</li>
	 * 	<li>bankName:String 银行名称</li>
	 * 	<li>bankNo:String 银行行号</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
}