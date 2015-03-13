package gnete.card.service;

import flink.util.Paginater;
import gnete.card.entity.type.report.ManualReportType;
import gnete.etc.BizException;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @File: GenerateReportService.java
 * 
 * @description: 自动生成报表的service处理。日切完成后处理
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-2-28
 */
public interface GenerateReportService {

	/**
	 * 生成日报表，每天的特定时刻生成

	 * @param reportDate 报表日期
	 * @return 
	 * @throws BizException
	 */
	void generateDayReport(String reportDate) throws BizException;
	
	/**
	 * 生成月报表，每个月1日的特定时刻生成
	 * 
	 * @return
	 * @throws BizException
	 */
	void generateMonthReport(String reportMonth) throws BizException;
	
	/**
	 * 手动生成报表
	 * @param type
	 * @param reportDate
	 * @param reportType
	 * @return
	 * @throws BizException
	 * @throws SQLException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	void manualGenerateReport(ManualReportType type, String reportDate, String report) throws BizException, SQLException, InterruptedException, ExecutionException;
	
	
	/**
	 * 自动生成旧报表，每天的特定时刻生成
	 * @param reportDate
	 * @throws BizException
	 */
	void generateOldDayReport(String reportDate) throws BizException;

	/**
	 * 取得日报表文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getReportList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;

	/**
	 * 取得月报表文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getMonthReportList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;

	/**
	 * 取得余额报表文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getBalanceReportList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	/**
	 * 取得对账文件列表
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getAccountCheckFileList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	/**
	 * 从FTP上下载对账文件
	 * @param fullPath
	 * @return
	 * @throws BizException
	 */
	boolean downloadAccountCheckFile(String fullPath) throws BizException;
	
	/**
	 * 取得旧报表txt文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getOldReportTxtList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	/**
	 * 从FTP上下载旧报表txt文件
	 * @param fullPath
	 * @return
	 * @throws BizException
	 */
	boolean downloadTxtOldReportFile(String fullPath) throws BizException;
	
	/**
	 * 取得旧报表xls文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getOldReportXlsList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	boolean generateConsmChargeBalReport(String reportSavePath) throws BizException;
	
	/**
	 * 取得"累积消费充值余额报表"
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getConsmChargeBalReportList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	/**
	 * 从FTP上下载"累积消费充值余额报表"文件
	 * @param fullPath
	 * @return
	 * @throws BizException
	 */
	boolean downloadConsmChargeBalReportFile(String fullPath) throws BizException;
}
