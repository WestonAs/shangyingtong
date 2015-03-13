package gnete.card.service;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.BalanceReportSet;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: BalanceReportSetService.java
 * 
 * @description: 自动生成余额报表设置service
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-28
 */
public interface BalanceReportSetService {

	/**
	 * 查询列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查询明细
	 * 
	 * @param pk
	 *            主键
	 * @return
	 */
	BalanceReportSet findDetail(String pk);

	/**
	 * 新增
	 * 
	 * @param balanceReportSet
	 * @param branches
	 *            发卡机构数组
	 * @param user
	 *            登录用户信息
	 * @return
	 * @throws BizException
	 */
	BalanceReportSet add(BalanceReportSet balanceReportSet, String[] branches,
			UserInfo user) throws BizException;

	/**
	 * 修改初始化
	 * @param cardBranch
	 * @return
	 */
	BalanceReportSet showModify(String cardBranch) throws BizException;
	
	/**
	 * 修改
	 * 
	 * @param balanceReportSet
	 * @param user
	 * @return
	 * @throws BizException
	 */
	boolean modify(BalanceReportSet balanceReportSet, UserInfo user)
			throws BizException;

	/**
	 * 删除
	 * 
	 * @param pk
	 *            主键
	 * @return
	 * @throws BizException
	 */
	boolean delete(String pk) throws BizException;

}
