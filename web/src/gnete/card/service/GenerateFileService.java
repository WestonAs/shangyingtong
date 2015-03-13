package gnete.card.service;

import gnete.etc.BizException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @File: GenerateFileService.java
 * 
 * @description: 生成Excell文件等
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-12-13
 */
public interface GenerateFileService {
	/**
	 * 导出卡档案信息的Excel文件
	 * @param response
	 * @param params
	 * @throws BizException
	 */
	void generateCardFileExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;

	/**
	 * 导出卡帐户信息的Excel文件
	 * @param response
	 * @param params
	 * @throws BizException
	 */
	void generateCardAcctExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;

	/**
	 * 导出 历史交易信息的Excel文件
	 * @param response
	 * @param params
	 * @throws BizException
	 */
	void generateHisTransExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;
	
	/**
	 * 导出 历史交易信息的Csv文件
	 * @throws IOException 
	 */
	void generateHisTransCsv(HttpServletResponse response, Map<String, Object> params) throws BizException, IOException;
	
	/**
	 * 导出 当日交易明细Csv文件
	 * @throws IOException 
	 */
	void generateTransCsv(HttpServletResponse response, Map<String, Object> params) throws BizException, IOException;
	
	/**
	 * 导出 历史风险交易信息.xls
	 * @param response
	 * @param params
	 * @throws BizException
	 */
	void generateHisRiskTransExcel(HttpServletResponse response, Map<String, Object> params, boolean isGenerateExcelTableTitle) throws BizException;
	
	/**
	 * 导出电子现金消费历史交易信息的Excel文件
	 * @param response
	 * @param params
	 * @throws BizException
	 */
	void generateEcashHisTransExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;
	
	/**
	 * 导出商户集群的历史交易明细的Excel文件
	 * @param response
	 * @param params
	 * @param isGenerateExcelTableTitle
	 * @throws BizException
	 */
	void generateMerchClusterHisTransExcel(HttpServletResponse response, Map<String, Object> params, boolean isGenerateExcelTableTitle) throws BizException;
	
	/**
	 * 导出商户集群的历史交易汇总的Excel文件
	 */
	void generateMerchClusterHisTransSummaryExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;
	
	/**
	 * 导出风险卡地点监控信息的Excel文件
	 * @Date 2013-3-20下午5:18:45
	 * @return void
	 */
	void generateCardAreaRiskTransExcel(HttpServletResponse response, Map<String, Object> params, boolean isGenerateExcelTableTitle) throws BizException;
	
	/**
	 * 导出商户错误码监控信息的Excel文件
	 * @Date 2013-3-20下午5:18:45
	 * @return void
	 */
	void generateMerchRespCodeTransExcel(HttpServletResponse response, Map<String, Object> params, boolean isGenerateExcelTableTitle) throws BizException;

	
}
