package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.Trans;

public interface TransDAO extends BaseDAO {
	
	Paginater findTrans(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 从清算数据库中查询历史交易明细
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findHisTrans(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	Paginater findHisRiskTrans(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	Paginater findTransJFLINK(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	Paginater findMerchTrans(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	Trans findByPkWithCheck(Map<String, Object> params);

	/**
	 * 从清算数据库中查询交易
	 * @param params
	 * @return
	 */
	Trans findByPkFromJFLINK(Map<String, Object> params);
	
	/**
	 * 查询过期卡受理商户明细
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findExpireCardTransReport(Map<String, Object> params);

	/**
	 * 查询过期卡交易终端小计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findExpireTransTermrlSumReport(Map<String, Object> params);

	/**
	 * 查询过期卡交易总计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findExpireTransSumReport(Map<String, Object> params);

	/**
	 * 查询过期卡交易总计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findActiveCardReport(Map<String, Object> params);

	/**
	 * 查询受理商户明细
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findMerchDetailReport(Map<String, Object> params);

	/**
	 * 查询终端小计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findTerminalSubTotalReport(Map<String, Object> params);

	/**
	 * 查询商户明细总计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findMerchDetailSumReport(Map<String, Object> params);
	
	/**
	 * 根据条件查历史交易信息
	 * @param params
	 * @return
	 */
	List<Trans> findHisTransList(Map<String, Object> params);
	
	List<Trans> findHisRiskTransList(Map<String, Object> params);
	
	/**
	 * 根据条件查询监控信息
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	 Paginater findTransMonitor(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 获取最新交易时间
	 * @return
	 */
	String getLastTransDate(Map<String, Object> params) ;
}