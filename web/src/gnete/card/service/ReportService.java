package gnete.card.service;

import gnete.card.entity.ReportConfigPara;
import gnete.card.entity.ReportConfigParaKey;
import gnete.etc.BizException;

/**
 * @File: reportService.java
 * 
 * @description: 报表service
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-10-14
 */
public interface ReportService {

	/**
	 * 新增报表参数配置定义
	 * @param reportConfigPara
	 * @param insCodes
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean addReportConfigPara(ReportConfigPara reportConfigPara, String[] insCodes,
			String userId) throws BizException;

	/**
	 * 修改报表参数配置定义
	 * @param reportConfigPara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyReportConfigPara(ReportConfigPara reportConfigPara, String userId) throws BizException;

	/**
	 * 删除报表参数配置定义
	 * @param key
	 * @return
	 * @throws BizException
	 */
	boolean deleteReportConfigPara(ReportConfigParaKey key) throws BizException;

}
