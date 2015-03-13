package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.IcEcashDepositReg;

/**
 * @File: IcEcashDepositRegDAO.java
 *
 * @description: IC卡电子现金充值登记薄DAO处理类
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-13
 */
public interface IcEcashDepositRegDAO extends BaseDAO {
	
	/**
	 * 查询列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据随机数查找IC卡电子现金充值对象
	 * @param randomSessionid
	 * @return
	 */
	IcEcashDepositReg findByRandomSessionid(String randomSessionId);
}