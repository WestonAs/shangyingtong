package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.DepositCouponBatReg;

import java.util.List;
import java.util.Map;

/**
 * @File: DepositCouponBatRegDAO.java
 *
 * @description: 赠券赠送批量登记明细表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-18 下午07:21:02
 */
public interface DepositCouponBatRegDAO extends BaseDAO {
	
	/**
	 * 查找批量赠送登记明细列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDepositCouponBatPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询批量赠券赠送登记簿列表
	 * 
	 * @param params
	 * @return
	 */
	List<DepositCouponBatReg> findDepositCouponBatList(Map<String, Object> params);
	
	/**
	 * 根据赠送批次号，批量更新状态
	 * @param params
	 */
	int updateStatusByDepositBatchId(Map<String, Object> params);
}