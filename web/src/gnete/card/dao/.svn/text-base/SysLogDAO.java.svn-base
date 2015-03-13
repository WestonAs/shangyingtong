package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.SysLog;

public interface SysLogDAO extends BaseDAO {

	/**
	 * 查询系统日志
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findLog(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查询清算日志
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSettLog(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查询清算日志明细
	 * @param id
	 * @return
	 */
	SysLog findByPkFromJFLink(Long id);

	/**
	 * 更新清算日志
	 * @param int
	 * @return
	 */
	int updateJFLink(SysLog sysLog);
}