package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.IcCardActive;

import java.util.Map;

/**
 * @File: IcCardActiveDAO.java
 *
 * @description: IC卡激活登记薄
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-21
 */
public interface IcCardActiveDAO extends BaseDAO {
	
	/**
	 * 查询列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据随机数查找IC卡激活登记对象
	 * @param randomSessionid
	 * @return
	 */
	IcCardActive findByRandomSessionid(String randomSessionid);
}