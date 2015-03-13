package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.DepositPointBatReg;

import java.util.List;
import java.util.Map;

/**
 * @File: DepositPointBatRegDAO.java
 *
 * @description: 积分充值批量登记明细表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-18 下午07:21:02
 */
public interface DepositPointBatRegDAO extends BaseDAO {
	
	/**
	 * 查找批量充值登记明细列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDepositPointBatPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询批量积分充值登记簿列表
	 * 
	 * @param params
	 * @return
	 */
	List<DepositPointBatReg> findDepositPointBatList(Map<String, Object> params);
	
	/**
	 * 根据充值批次号，批量更新状态
	 * @param params
	 */
	int updateStatusByDepositBatchId(Map<String, Object> params);
}