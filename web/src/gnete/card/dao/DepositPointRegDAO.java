package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.DepositPointReg;

/**
 * @File: DepositPointRegDAO.java
 *
 * @description: 积分充值登记簿操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-18 下午07:21:37
 */
public interface DepositPointRegDAO extends BaseDAO {
	
	/**
	 * 充值登记表列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDepositPointRegPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询充值登记表列表
	 * @param params
	 * @return
	 */
	List<DepositPointReg> findDepositPointList(Map<String, Object> params);
	
	/**
	 * 查找积分充值登记审核列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDepositPointCheckPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找积分充值登记审核列表
	 * @param params
	 * @return
	 */
	List<DepositPointReg> findDepositPointCheckList(Map<String, Object> params);
}