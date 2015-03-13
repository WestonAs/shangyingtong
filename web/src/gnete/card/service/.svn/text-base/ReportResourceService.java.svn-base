package gnete.card.service;

import gnete.etc.BizException;

import java.util.Map;

/**
 * @File: ReportGenerateProcService.java
 *
 * @description: 报表产生过程业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-7-21
 */
public interface ReportResourceService {
	

	/**
	 * 生成平台运营手续费交易数据源
	 * @param params
	 * @throws BizException
	 */
	void getCenterOperateFeeExcel(Map<String, Object> params, String tempPath) throws BizException;
	
	/**
	 * 生成平台运营手续费会员数
	 * @param params
	 * @throws BizException
	 */
	void getCenterOperateFeeMembTxt(Map<String, Object> params, String tempPath) throws BizException;
	
	/**
	 * 生成平台运营手续费折扣卡会员数
	 * @param params
	 * @throws BizException
	 */
	void getCenterOperateFeeDiscntMembTxt(Map<String, Object> params, String tempPath) throws BizException;

	/**
	 * 生成运营代理商分润月统计报表交易数据源
	 * @param params
	 * @throws BizException
	 */
	void getAgentFeeShareExcel(Map<String, Object> params, String tempPath) throws BizException;
}
