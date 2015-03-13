package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.DepositCouponReg;

/**
 * @File: DepositCouponRegDAO.java
 *
 * @description: 赠券赠送登记簿操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-18 下午07:21:37
 */
public interface DepositCouponRegDAO extends BaseDAO {
	
	/**
	 * 赠送登记表列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDepositCouponRegPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询赠送登记表列表
	 * @param params
	 * @return
	 */
	List<DepositCouponReg> findDepositCouponList(Map<String, Object> params);
	
	/**
	 * 查找赠券赠送登记审核列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findDepositCouponCheckPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找赠券赠送登记审核列表
	 * @param params
	 * @return
	 */
	List<DepositCouponReg> findDepositCouponCheckList(Map<String, Object> params);
}