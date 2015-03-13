package gnete.card.dao;

import gnete.etc.BizException;

import java.util.List;
import java.util.Map;

/**
 * 报表数据来源
 * @author aps-lib
 *
 */
public interface ReportResourceDAO extends BaseDAO {
	/**
	 * 平台运营手续费收入明细报表-预付资金
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailPreFund(Map<String, Object> params) throws BizException;
	
	/**
	 * 平台运营手续费收入明细报表-次卡可用次数
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailAccuTimeAvail(Map<String, Object> params) throws BizException;
	
	/**
	 * 平台运营手续费收入明细报表-通用积分资金
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailUniPointFund(Map<String, Object> params) throws BizException;
	
	/**
	 * 平台运营手续费收入明细报表-赠券资金
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailCouponFund(Map<String, Object> params) throws BizException;
	
	/**
	 * 平台运营手续费收入明细报表-专用积分交易笔数
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailSpePointTransNum(Map<String, Object> params) throws BizException;
	
	/**
	 * 平台运营手续费收入明细报表-会员数
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailMembNum(Map<String, Object> params) throws BizException;

	/**
	 * 平台运营手续费收入明细报表-折扣卡会员数
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>branchCode: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findOptDetailDisCountMembNum(Map<String, Object> params) throws BizException;

	/**
	 * 运营代理商分润月统计报表-发卡机构运营手续费月统计表
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>proxyId: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findAgentFeeShareOpt(Map<String, Object> params) throws BizException;

	/**
	 * 运营代理商分润月统计报表-消费交易
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>proxyId: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findAgentFeeShareConsum(Map<String, Object> params) throws BizException;
	
	/**
	 * 运营代理商分润月统计报表-积分交易
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>proxyId: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findAgentFeeSharePoint(Map<String, Object> params) throws BizException;
	
	/**
	 * 运营代理商分润月统计报表-次卡交易
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>proxyId: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findAgentFeeShareAccu(Map<String, Object> params) throws BizException;
	
	/**
	 * 运营代理商分润月统计报表-积分兑换礼品
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>proxyId: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findAgentFeeSharePointExcGift(Map<String, Object> params) throws BizException;
	
	/**
	 * 运营代理商分润月统计报表-售卡充值
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>feeMonth: String 清算月份</li>
	 * 	<li>proxyId: String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	List<Map<String, Object>> findAgentFeeShareSaleCardRecharge(Map<String, Object> params) throws BizException;
	
}
