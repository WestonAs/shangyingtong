package gnete.card.service.impl;

import flink.exception.ExcelExportException;
import flink.exception.PdfExportException;
import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonNameDirFilesCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.CommonHelper;
import flink.util.DateUtil;
import flink.util.FileHelper;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.util.ftp.FtpFile;
import flink.util.ftp.FtpUtil;
import gnete.card.dao.BalanceReportSetDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.OldReportParaDAO;
import gnete.card.dao.ReportConfigParaDAO;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.AccountCheckFile;
import gnete.card.entity.BalanceReportSet;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusiReport;
import gnete.card.entity.CardInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.OldReportPara;
import gnete.card.entity.ReportConfigPara;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;
import gnete.card.entity.type.report.ManualReportType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.GenerateReportService;
import gnete.card.service.LogService;
import gnete.card.service.OldReportService;
import gnete.card.service.TxtService;
import gnete.card.service.mgr.BranchBizConfigCache;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.tag.NameTag;
import gnete.card.util.RunqianExportUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.ReportTemplateConstants;
import gnete.etc.Symbol;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprisedt.net.ftp.FTPException;
import com.runqian.report4.usermodel.Context;

@Service("generateReportService")
public class GenerateReportServiceImpl implements GenerateReportService, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(GenerateReportServiceImpl.class);

	@Autowired
	private OldReportService oldReportService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private ReportPathSaveDAO reportPathSaveDAO;
	@Autowired
	private BalanceReportSetDAO balanceReportSetDAO;
	@Autowired
	private OldReportParaDAO oldReportParaDAO;
	@Autowired
	private ReportConfigParaDAO reportConfigParaDAO;
	@Autowired
	private TxtService txtService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private LogService logService;
	
	private static final String PATH_SEP = File.separator;
	private static final String[] prefix = new String[] {};
	private static final String[] suffix = new String[] { ".txt" };
	
	private static final String EXCEL_SUFFIX = ".xlsx";
	private static final String PDF_SUFFIX = ".pdf";
	
//	private ITaskCall<List<String>> generateReportTask = new TaskCallHelper<List<String>>(6);
	
//	private List<IDayReportGenerate> dayReportGenereateList = new ArrayList<IDayReportGenerate>();
	private Map<String,IReportGenerate> dayReportGenereateMap = new HashMap<String,IReportGenerate>();
	private Map<String,IReportGenerate> monthReportGenereateMap = new HashMap<String,IReportGenerate>();
	
	private Collection<IReportGenerate> getDayGenerateList() {
		return this.dayReportGenereateMap.values();
	}
	
	private Collection<IReportGenerate> getMonthGenerateList() {
		return this.monthReportGenereateMap.values();
	}

	public void afterPropertiesSet() throws Exception {
		//--------------------------日报表-------------------------
		// 生成运营中心日报表
		dayReportGenereateMap.put(ReportType.CENTER_DAILY.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateAllCenterDayReport(preWorkDate, reportSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成运营中心日报表";
			}
		});

		// 生成运营机构日报表
		dayReportGenereateMap.put(ReportType.FENZHI_DAILY.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateAllFenzhiDayReport(preWorkDate, reportSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成运营机构日报表";
			}
		});

		// 生成发卡机构日报表
		dayReportGenereateMap.put(ReportType.CARD_DAILY.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateAllCardDayReport(preWorkDate, reportSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成发卡机构日报表";
			}
		});

		// 生成商户日报表
		dayReportGenereateMap.put(ReportType.MERCH_DAILY.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateAllMerchDayReport(preWorkDate, reportSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成商户日报表";
			}
		});

		// 生成售卡代理日报表
		dayReportGenereateMap.put(ReportType.PROXY_DAILY.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateAllProxyDayReport(preWorkDate, reportSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成售卡代理日报表";
			}
		});

		// 生成余额日报表
		dayReportGenereateMap.put(ReportType.BALANCE.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateBalanceReport(preWorkDate, reportSavePath, templatePath);
			}
			public String getReportDecribe() {
				return "生成余额日报表";
			}
		});

		// 生成累积消费充值余额报表
		dayReportGenereateMap.put(ReportType.CONSM_CHARGE_BAL.getValue(), new IReportGenerate() {
			public void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException {
				generateConsmChargeBalReport(reportSavePath);
			}
			public String getReportDecribe() {
				return "生成累积消费充值余额报表";
			}
		});
		
		//---------------------------月报表-----------------------------
		// 生成运营中心月报表
		monthReportGenereateMap.put(ReportType.CENTER_MONTH.getValue(), new IReportGenerate() {
			public void generateReport(String reportMonth, String reportFileSavePath, String templatePath, String currDate) throws BizException {
				generateAllCenterMonthReport(reportMonth, reportFileSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成运营中心月报表";
			}
		});
		
		// 生成运营分支机构月报表
		monthReportGenereateMap.put(ReportType.FENZHI_MONTH.getValue(), new IReportGenerate() {
			public void generateReport(String reportMonth, String reportFileSavePath, String templatePath, String currDate) throws BizException {
				generateAllFenzhiMonthReport(reportMonth, reportFileSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成运营分支机构月报表";
			}
		});
		
		// 生成发卡机构月报表
		monthReportGenereateMap.put(ReportType.CARD_MONTH.getValue(), new IReportGenerate() {
			public void generateReport(String reportMonth, String reportFileSavePath, String templatePath, String currDate) throws BizException {
				generateAllCardMonthReport(reportMonth, reportFileSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成发卡机构月报表";
			}
		});
		
		// 生成商户月报表
		monthReportGenereateMap.put(ReportType.MERCH_MONTH.getValue(), new IReportGenerate() {
			public void generateReport(String reportMonth, String reportFileSavePath, String templatePath, String currDate) throws BizException {
				generateAllMerchMonthReport(reportMonth, reportFileSavePath, templatePath, currDate);
			}
			public String getReportDecribe() {
				return "生成商户月报表";
			}
		});
		
	}

	public void destroy() throws Exception {
		this.dayReportGenereateMap.clear();		
		this.monthReportGenereateMap.clear();		
	}
	
	/**
	  * 生成日报表（含运营中心、运营机构、发卡机构、商户等等）【多线程】；
	  */
	public void generateDayReport(final String preWorkDate) throws BizException {
		final String templatePath = getReportTemplatePath(); // 报表模板保存路径
		final String reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径
		final String currDate = DateUtil.formatDate("yyyyMMdd"); // 当前日期
		logger.debug("报表模板保存路径：{}；生成的报表文件保存路径：{}", templatePath, reportFileSavePath);
		
//		FIXME 测试用，只执行商户日报表
//		for (final IReportGenerate reportGenerate : Arrays.asList(dayReportGenereateMap.get(ReportType.MERCH_DAILY.getValue()))) {
		for(final IReportGenerate reportGenerate : getDayGenerateList()) {
			
			// 采用多线程处理，每种类型的报表都起一个线程去执行
			new Thread(new Runnable() {
				public void run() {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					try {
						reportGenerate.generateReport(preWorkDate, reportFileSavePath, templatePath, currDate);
						
					} catch (BizException e) {
						String msg = String.format("[%s][%s]发生异常：[%s]", preWorkDate,
								reportGenerate.getReportDecribe(), e.getMessage());
						logger.error(msg, e);
						insertSysLog("", "", msg, SysLogType.WARN);
					} finally {
						stopWatch.stop();
						String msg = String.format("[%s][%s]消耗时间[%s]", preWorkDate, reportGenerate.getReportDecribe(), stopWatch);
						logger.info(msg);
						insertSysLog("", "", msg, SysLogType.INFO);
					}
				}
			}).start();
		}
		return;
	}

	/**
	  * 生成月报表（含运营中心、运营机构、发卡机构、商户等等）；
	  */
	public void generateMonthReport(final String reportMonth) throws BizException {
		final String templatePath = getReportTemplatePath(); // 报表模板保存路径
		final String reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径
		final String currDate = DateUtil.formatDate("yyyyMMdd");
		
		for(final IReportGenerate reportGenerate : getMonthGenerateList()) {
			// 采用多线程处理，每种类型的报表都起一个线程去执行
			new Thread(new Runnable() {
				public void run() {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					try {
						reportGenerate.generateReport(reportMonth, reportFileSavePath, templatePath, currDate);
					} catch (BizException e) {
						String msg = String.format("[%s][%s]发生异常：[%s]", reportMonth,
								reportGenerate.getReportDecribe(), e.getMessage());
						logger.error(msg, e);
						insertSysLog("", "", msg, SysLogType.WARN);
					} finally {
						stopWatch.stop();
						String msg = String.format("[%s][%s]消耗时间[%s]", reportMonth, reportGenerate.getReportDecribe(), stopWatch);
						logger.info(msg);
						insertSysLog("", "", msg, SysLogType.INFO);
					}
				}
			}).start();
		}
		return ;
	}
	
	/**
	 * 手工生成报表
	 * @throws SQLException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void manualGenerateReport(ManualReportType type, String reportDate, String reportType) throws BizException, SQLException, InterruptedException, ExecutionException {
		Assert.notEmpty(reportDate, "要生成的报表统计日期（月份）不能为空");
		Assert.notNull(type, "要生成的报表类型不能为空");
		
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportDate", reportDate);
		if(ReportType.CARD_DAILY.getValue().equals(reportType)){//机构除了excel,还生成3个pdf文件
			params.put("reportTypes", ReportType.getCardDayReport());
		}else if(ReportType.MERCH_DAILY.getValue().equals(reportType)){//商户除了excel,还生成1个pdf文件
			params.put("reportTypes", ReportType.getMerchDayReport());
		}else{
		    params.put("reportType", reportType);
		}
		
		// 使用子线程删除，并等待结果返回。防止线程池生成日报表插入数据库时死锁！
		Executors.newSingleThreadScheduledExecutor().submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return reportPathSaveDAO.deleteByMap(params);// 先删除报表文件保存路径表里的记录;
			}
		}).get();
		
		String templatePath = null;
		String reportFileSavePath = null;
		String currDate = null;
		if(CommonHelper.isNotEmpty(reportType)){
			templatePath = getReportTemplatePath(); // 报表模板保存路径
			reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径
			currDate = DateUtil.formatDate("yyyyMMdd");
			logger.debug("报表模板保存路径：{}；生成的报表文件保存路径：{}" , templatePath, reportFileSavePath);
		}
		long startTime = System.currentTimeMillis();
		logger.info("手动生成报表开始.");
		if (ManualReportType.DAILY.equals(type)) {
			if(null != dayReportGenereateMap.get(reportType)){//指定类型的日报表生成
				IReportGenerate reportGenerate = dayReportGenereateMap.get(reportType);
				reportGenerate.generateReport(reportDate, reportFileSavePath, templatePath, currDate);
			}else{
				this.generateDayReport(reportDate);
			}
		} else if (ManualReportType.MONTH.equals(type)) {
			if(null != monthReportGenereateMap.get(reportType)){//指定类型的月报表生成
				IReportGenerate reportGenerate = monthReportGenereateMap.get(reportType);
				reportGenerate.generateReport(reportDate, reportFileSavePath, templatePath, currDate);
			}else{
				this.generateMonthReport(reportDate);
			}
		}
		long spendTime = System.currentTimeMillis()-startTime; //毫秒
		String msg = String.format("手动生成报表[%s][%s]结束,消耗时间[%s]", reportDate, reportType,
				gnete.util.DateUtil.formatDuring(spendTime));
		logger.info(msg);
		insertSysLog("", "", msg, SysLogType.INFO);
		return;
	}
	
	public void generateOldDayReport(String reportDate) throws BizException {
		String reportPath = SysparamCache.getInstance().getOldReportSavePath(); //生成的报表文件保存路径
		
		List<OldReportPara> oldReportList = oldReportParaDAO.findByIssType(IssType.CARD);
		
		if (CollectionUtils.isEmpty(oldReportList)) {
			String msg = "没有配置需要生成的旧系统的报表文件的发卡机构";
			logger.info(msg);
			throw new BizException(msg);
		}
		
		for (OldReportPara oldReportPara : oldReportList) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardIssuer", oldReportPara.getIssCode());
			params.put("settDate", reportDate);
			
			try {
				// 生成过期商户明细报表 
				oldReportService.generateOldExpireCardReport(reportPath, params);
				
				// 生成卡激活报表 
				oldReportService.generateOldActiveCardReport(reportPath, params);
				
				//生成商户明细报表
				oldReportService.generateOldMerchDetailReport(reportPath, params);
			} catch (BizException e) {
				String msg = "生成发卡机构旧报表失败。" + e.getMessage();
				logger.error(msg, e);
				this.insertSysLog(oldReportPara.getIssCode(), "", msg, SysLogType.WARN);
				continue;
			}
		}
		
	}

	public Paginater getReportList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException {
		List<BusiReport> list = new ArrayList<BusiReport>();
		
		String reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径
//		String reportFileSavePath = "D:/reportFile/";

		String reportDate = (String) params.get("reportDate");
		String reportType = (String) params.get("reportType");
		String merchantNo = (String) params.get("merchantNo");

		String reportFolder = reportFileSavePath + PATH_SEP + "dayReport";
		String excelName = "";
		List<String> fileList = new LinkedList<String>();

		// 查出符合条件的所有报表文件列表
		fileList = IOUtil.getFileList(fileList, reportFolder);

		if (StringUtils.isNotBlank(reportDate)
				|| StringUtils.isNotBlank(reportType)
				|| StringUtils.isNotBlank(merchantNo)) {
			fileList = getListResult(fileList, reportDate, reportType, merchantNo);
		}

		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		}
		
		for (String reportFilePath : fileList) {
			BusiReport busiReport = new BusiReport();
			excelName = reportFilePath.substring(reportFilePath.lastIndexOf(PATH_SEP) + 1, reportFilePath.length());
			String[] names = excelName.split("\\_");
			if (names.length != 4) {
				logger.debug("报表文件名字为：{}", excelName);
				throw new BizException("报表文件名字错误");
			}
			merchantNo = names[2];
			reportDate = names[3].split("\\.")[0];

			busiReport.setReportType(names[1]);
			busiReport.setReportDate(reportDate);
			busiReport.setMerchantNo(merchantNo);
			busiReport.setFilePath(reportFilePath.replace("\\", "\\\\"));
			busiReport.setReportName(excelName);

			list.add(busiReport);
		}

		Paginater paginater = new Paginater(list.size(), pageNumber, pageSize);
		List<BusiReport> content = new ArrayList<BusiReport>();
		for (int i = (pageNumber - 1) * pageSize; i < list.size() && i < pageNumber * pageSize; i++) {
			BusiReport report = list.get(i);
			content.add(report);
		}
		paginater.setData(content);
		return paginater;
	}

	public Paginater getMonthReportList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		List<BusiReport> list = new ArrayList<BusiReport>();
		String reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径

		String reportMonth = (String) params.get("reportMonth");
		String merchantNo = (String) params.get("merchantNo");

		String reportFolder = reportFileSavePath + PATH_SEP + "monthReport"; // 月报表保存路径（只按发卡机构统计）
		String excelName = "";
		List<String> fileList = new LinkedList<String>();
		
		// 查出符合条件的所有报表文件列表
		fileList = IOUtil.getFileList(fileList, reportFolder);
		
		if (StringUtils.isNotBlank(reportMonth)
				|| StringUtils.isNotBlank(merchantNo)) {
			fileList = getListResult(fileList, reportMonth, merchantNo);
		}
		
		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		}
		
		for (String reportFilePath : fileList) {
			BusiReport busiReport = new BusiReport();
			excelName = reportFilePath.substring(reportFilePath.lastIndexOf(PATH_SEP) + 1, reportFilePath.length());
			String[] names = excelName.split("\\_");
			if (names.length != 4) {
				logger.debug("报表文件名字为：{}", excelName);
				throw new BizException("报表文件名字错误");
			}
			merchantNo = names[2];
			reportMonth = names[3].split("\\.")[0];

			busiReport.setReportType(names[1]);
			busiReport.setReportDate(reportMonth);
			busiReport.setMerchantNo(merchantNo);
			busiReport.setFilePath(reportFilePath.replace("\\", "\\\\"));
			busiReport.setReportName(excelName);

			list.add(busiReport);
		}

		Paginater paginater = new Paginater(list.size(), pageNumber, pageSize);
		List<BusiReport> content = new ArrayList<BusiReport>();
		for (int i = (pageNumber - 1) * pageSize; i < list.size() && i < pageNumber * pageSize; i++) {
			BusiReport report = list.get(i);
			content.add(report);
		}
		paginater.setData(content);
		return paginater;
	}

	public Paginater getBalanceReportList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		List<BusiReport> list = new ArrayList<BusiReport>();
		String reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径
		logger.debug("生成的报表文件保存路径:[{}]", reportFileSavePath);
		
		String reportDate = (String) params.get("reportDate");
		String merchantNo = (String) params.get("merchantNo");

		String reportFolder = reportFileSavePath + PATH_SEP + "balanceReport"; // 余额报表保存路径（只按发卡机构统计）
		String excelName = "";
		List<String> fileList = new LinkedList<String>();
		
		// 查出符合条件的所有报表文件列表
		fileList = IOUtil.getFileList(fileList, reportFolder);
		
		if (StringUtils.isNotBlank(reportDate)
				|| StringUtils.isNotBlank(merchantNo)) {
			fileList = getListResult(fileList, reportDate, merchantNo);
		}

		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		}
		
		for (String reportFilePath : fileList) {
			BusiReport busiReport = new BusiReport();
			excelName = reportFilePath.substring(reportFilePath.lastIndexOf(PATH_SEP) + 1, reportFilePath.length());
			String[] names = excelName.split("\\_");
			if (names.length != 3) {
				throw new BizException("报表文件名字错误");
			}
			merchantNo = names[1];
			reportDate = names[2].split("\\.")[0];

			busiReport.setReportType(names[1]);
			busiReport.setReportDate(reportDate);
			busiReport.setMerchantNo(merchantNo);
			busiReport.setFilePath(reportFilePath.replace("\\", "\\\\"));
			busiReport.setReportName(excelName);

			list.add(busiReport);
		}
		
		list = sortReportDate(list);
//		// 按日期做降序排列
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				BusiReport busiReport1 = (BusiReport) o1;
//				BusiReport busiReport2 = (BusiReport) o2;
//				
//				return busiReport2.getReportDate().compareTo(busiReport1.getReportDate());
//			}
//		});

		Paginater paginater = new Paginater(list.size(), pageNumber, pageSize);
		List<BusiReport> content = new ArrayList<BusiReport>();
		for (int i = (pageNumber - 1) * pageSize; i < list.size() && i < pageNumber * pageSize; i++) {
			BusiReport report = list.get(i);
			content.add(report);
		}
		paginater.setData(content);
		return paginater;
	}
	
	private List<BusiReport> sortReportDate(List<BusiReport> list) {
		// 按日期做降序排列
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				BusiReport busiReport1 = (BusiReport) o1;
				BusiReport busiReport2 = (BusiReport) o2;
				
				return busiReport2.getReportDate().compareTo(busiReport1.getReportDate());
			}
		});
		
		return list;
	}
	
	public Paginater getAccountCheckFileList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		String ftpRootPath = mgr.getAccountCheckFileSavePath(); // 对账文件保存目录
		String ftpPath = ftpRootPath;
		
		// 取页面传过来的参数
		String branchNo = (String) params.get("branchCode");
		String checkDate = (String) params.get("checkDate");
		if (StringUtils.isNotBlank(branchNo)) {
			ftpPath = ftpPath + "/" + branchNo;
			if (StringUtils.isNotBlank(checkDate)) {
				ftpPath = ftpPath + "/" + checkDate;
			}
		}

		List<AccountCheckFile> list = new ArrayList<AccountCheckFile>();
		
		List<FtpFile> fileList = null;
		try {
			FtpUtil ftpList = new FtpUtil(ftpServer, user, pwd, 21);
			ftpList.connect();
			
			String home = ftpList.getClient().getRemoteDirectory();
			String path = ftpPath.substring(ftpPath.indexOf(home) + home.length());
			fileList = ftpList.getFileList(path, fileList, home);
			
			ftpList.disconnect();

			for (FtpFile file : fileList) {
				if (!file.isDirectory()) {
					String fullPath = home + file.getFilePath();
					// 路径为：对账文件保存根路径/发卡机构编号/对账日期/对账文件名.txt
					String[] arr = fullPath.substring(fullPath.indexOf(ftpRootPath) + ftpRootPath.length() + 1).split("/");
					
					Assert.isTrue(arr.length ==3, "对账文件不存在或路径错误！文件全路径为：[" + fullPath + "]");
					
//					fullPath = fullPath.substring(0, fullPath.indexOf(file.getFileName()) - 1 );
					
//					BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(arr[0]);
					String branchName = NameTag.getBranchName(arr[0]);
					Assert.notEmpty(branchName, "发卡机构[" + arr[0] + "]不存在");

					AccountCheckFile checkFile = new AccountCheckFile();

					checkFile.setBranchCode(arr[0]);
					checkFile.setBranchName(branchName);
					
					checkFile.setCheckDate(arr[1]);
					checkFile.setFileName(file.getFileName());
					checkFile.setFilePath(fullPath.replace("\\", "\\\\"));
					
					list.add(checkFile);
				}
			}
		} catch (FTPException e) {
			throw new BizException("获取FTP服务器上对账文件列表时发生FTPException:" + e.getMessage());
		} catch (IOException e) {
			throw new BizException("获取FTP服务器上对账文件列表时发生IOException:" + e.getMessage());
		} catch (ParseException e) {
			throw new BizException("获取FTP服务器上对账文件列表时发生ParseException:" + e.getMessage());
		}
		
		if (CollectionUtils.isEmpty(list)) {
			return Paginater.EMPTY;
		}
		
		Paginater paginater = new Paginater(list.size(), pageNumber, pageSize);
		List<AccountCheckFile> content = new ArrayList<AccountCheckFile>();
		for (int i = (pageNumber - 1) * pageSize; i < list.size() && i < pageNumber * pageSize; i++) {
			AccountCheckFile checkFile = list.get(i);
			content.add(checkFile);
		}
		paginater.setData(content);
		
		return paginater;
	}
	
	public boolean downloadAccountCheckFile(String fullPath)
			throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		
		String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1);
		String ftpPath = fullPath.substring(0, fullPath.indexOf(fileName) - 1);
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);
		
		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不对账文件[" + fileName + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取对账文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.error(msg);
			throw new BizException(msg);
		}
		return result;
	}
	
	/**
	 * 生成所有的运营中心日报表
	 */
	private void generateAllCenterDayReport(String preWorkDate, String reportSavePath, String templatePath,
			String currDate) throws BizException {
		
		String reportFolder = reportSavePath + "/dayReport/center/" + preWorkDate;
		List<BranchInfo> centerList = branchInfoDAO.findByType(BranchType.CENTER.getValue());
		for (BranchInfo center : centerList) {
			buildCenterDayReport(center, preWorkDate, reportFolder, templatePath, currDate);
		}
		return;
	}
	
	/**
	 * 生成运营中心日报表
	 * @param center 运营中心机构信息
	 * @param preWorkDate  前一工作日的报表
	 * @param reportFolder 运营中心日报表文件的保存路径
	 * @param templatePath 报表模板保存路径
	 * @param currDate 当前日期
	 * @throws BizException
	 */
	private void buildCenterDayReport(BranchInfo center, String preWorkDate,
			String reportFolder, String templatePath, String currDate) throws BizException {
		
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "dayReport_center_" + center.getBranchCode() + "_" + preWorkDate + EXCEL_SUFFIX;
		String excelFileName = reportFolder + PATH_SEP + excelName;

		// 运营分支机构交易汇总日统计报表
		Context context1 = new Context();
		context1.setParamValue("currDate", currDate);
		context1.setParamValue("feeDate", preWorkDate);
		contextList.add(context1);
		raqFileList.add(templatePath + ReportTemplateConstants.FENZHI_TRANS_DSUM);
		excelNameList.add("运营机构交易汇总日统计报表");
		
		// 运营机构计费子交易日统计报表
		Context context2 = new Context();
		context2.setParamValue("currDate", currDate);
		context2.setParamValue("feeDate", preWorkDate);
		contextList.add(context2);
		raqFileList.add(templatePath + ReportTemplateConstants.FENZHI_SUBTRANS_DSUM);
		excelNameList.add("运营机构计费子交易日统计报表");
		
		OutputStream outputStream = null;

		try {
			outputStream = FileHelper.getFileOutputStream(reportFolder, excelName, true);
			
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, outputStream);
//			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			addReportPathSave(center.getBranchCode(), preWorkDate, ReportType.CENTER_DAILY, excelName, excelFileName);
			logger.info("生成运营中心[{}({})]日统计报表成功", center.getBranchName(), center.getBranchCode());
		} catch (Exception e) {
			String msg = "生成运营中心[" + center.getBranchName() + "(" + center.getBranchCode() + ")]日报表失败，原因：" + e.getMessage();
			logger.error(msg, e);
			this.insertSysLog(center.getBranchCode(), "", msg, SysLogType.WARN);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		return;
	}
	
	/**
	 * 生成所有的分支机构日报表
	 */
	private void generateAllFenzhiDayReport(final String preWorkDate, String reportSavePath,
			final String templatePath, final String currDate) throws BizException {

		List<BranchInfo> fenzhiList = branchInfoDAO.findByType(BranchType.FENZHI.getValue());
		final String reportFolder = reportSavePath + "/dayReport/fenzhi/" + preWorkDate;
		for (BranchInfo branchInfo : fenzhiList) {
			buildFenzhiDayReport(branchInfo, preWorkDate, reportFolder, templatePath, currDate);
		}
		return;
	}
	
	/**
	 * @param branchInfo 分支机构号
	 * @param preWorkDate 前一工作日的报表
	 * @param reportFolder 分支机构日报表文件的保存路径
	 * @param templatePath  报表模板保存路径
	 * @param currDate 当前日期
	 * @return 生成失败的机构号
	 * @throws BizException
	 */
	private void buildFenzhiDayReport(BranchInfo branchInfo, String preWorkDate,
			String reportFolder, String templatePath, String currDate) throws BizException {
	
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "dayReport_fenzhi_" + branchInfo.getBranchCode() + "_" + preWorkDate + EXCEL_SUFFIX;
		String excelFileName = reportFolder + PATH_SEP + excelName;

		// 发卡机构交易汇总统计报表
		Context context1 = new Context();
		context1.setParamValue("feeDate", preWorkDate);
		context1.setParamValue("chlCode", branchInfo.getBranchCode());
		context1.setParamValue("chlName", branchInfo.getBranchName());
		context1.setParamValue("currDate", currDate);
		contextList.add(context1);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_TRANS_DSUM);
		excelNameList.add("发卡机构交易汇总统计报表");

		// 发卡机构交易明细统计报表
		Context context2 = new Context();
		context2.setParamValue("feeDate", preWorkDate);
		context2.setParamValue("chlCode", branchInfo.getBranchCode());
		context2.setParamValue("chlName", branchInfo.getBranchName());
		context2.setParamValue("currDate", currDate);
		contextList.add(context2);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_TRANS_DDETAIL);
		excelNameList.add("发卡机构交易明细统计报表");

		// 发卡机构售卡汇总统计报表
		Context context3 = new Context();
		context3.setParamValue("feeDate", preWorkDate);
		context3.setParamValue("chlCode", branchInfo.getBranchCode());
		context3.setParamValue("chlName", branchInfo.getBranchName());
		context3.setParamValue("currDate", currDate);
		contextList.add(context3);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_SELL_DSUM);
		excelNameList.add("发卡机构售卡汇总统计报表");

		// 发卡机构售卡明细统计报表
		Context context4 = new Context();
		context4.setParamValue("feeDate", preWorkDate);
		context4.setParamValue("chlCode", branchInfo.getBranchCode());
		context4.setParamValue("chlName", branchInfo.getBranchName());
		context4.setParamValue("currDate", currDate);
		contextList.add(context4);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_SELL_DDETAIL);
		excelNameList.add("发卡机构售卡明细统计报表");

		// 发卡机构充值汇总统计报表
		Context context5 = new Context();
		context5.setParamValue("feeDate", preWorkDate);
		context5.setParamValue("chlCode", branchInfo.getBranchCode());
		context5.setParamValue("chlName", branchInfo.getBranchName());
		context5.setParamValue("currDate", currDate);
		contextList.add(context5);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_DEPOSIT_DSUM);
		excelNameList.add("发卡机构充值汇总统计报表");

		// 发卡机构充值明细统计报表
		Context context6 = new Context();
		context6.setParamValue("feeDate", preWorkDate);
		context6.setParamValue("chlCode", branchInfo.getBranchCode());
		context6.setParamValue("chlName", branchInfo.getBranchName());
		context6.setParamValue("currDate", currDate);
		contextList.add(context6);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_DEPOSIT_DDETAIL);
		excelNameList.add("发卡机构充值明细统计报表");

		// 发卡机构计费子交易汇总统计报表
		Context context7 = new Context();
		context7.setParamValue("feeDate", preWorkDate);
		context7.setParamValue("chlCode", branchInfo.getBranchCode());
		context7.setParamValue("chlName", branchInfo.getBranchName());
		context7.setParamValue("currDate", currDate);
		contextList.add(context7);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_SUBTRANS_DSUM);
		excelNameList.add("发卡机构计费子交易汇总统计报表");
		OutputStream out = null;
		try {
			out = FileHelper.getFileOutputStream(reportFolder, excelName, true);
			
			if(RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, out)){
				// 记录报表文件保存路径信息
				this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.FENZHI_DAILY, excelName, excelFileName);
				logger.info("生成运营分支机构[{}({})]日统计报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode());
			}			
		} catch (Exception e) {
			String msg = LogUtils.r("生成分支机构[{0}({1})]日报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			// 记录系统日志
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
			// 退出当前方法
			return;
		} finally {
			IOUtils.closeQuietly(out);
		}
		
		return;
	}

	/**
	 * 生成所有的发卡机构日报表【多线程生成】
	 * 
	 */
	private void generateAllCardDayReport(final String preWorkDate,
			final String reportSavePath, final String templatePath, final String currDate) throws BizException {

		final String reportFolder = reportSavePath + "/dayReport/cardBranch/" + preWorkDate;
		List<BranchInfo> cardBranchList = branchInfoDAO.findByType(BranchType.CARD.getValue());
		
		// 线程池
		int threadPoolSize = 2;
		ExecutorService	threadPool	= Executors.newFixedThreadPool(threadPoolSize);
		for (final BranchInfo branchInfo: cardBranchList) {
			//启动线程
			threadPool.execute(new Runnable() {
				public void run() {
					buildCardDayReport(branchInfo, preWorkDate, reportFolder, templatePath, currDate);
				}
			});
		}
		shutdownAndWait(threadPool);
		return;
	}

	/**
	 * @param branchInfo 发卡机构
	 * @param preWorkDate  前一工作日的报表
	 * @param reportFolder 发卡机构报表文件保存目录
	 * @param templatePath  报表模板保存路径
	 * @param currDate 报表日期
	 * @return 生成失败的机构号
	 * @throws BizException
	 */
	private void buildCardDayReport(BranchInfo branchInfo, String preWorkDate, String reportFolder,
			String templatePath, String currDate) {
		
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "dayReport_cardBranch_" + branchInfo.getBranchCode() + "_" + preWorkDate + EXCEL_SUFFIX;
		String excelFileName = reportFolder + PATH_SEP + excelName;
		
		// 商户交易汇总统计报表
		Context context = new Context();
		context.setParamValue("feeDate", preWorkDate);
		context.setParamValue("branchCode", branchInfo.getBranchCode());
		context.setParamValue("branchName", branchInfo.getBranchName());
		context.setParamValue("currDate", currDate);
		contextList.add(context);
		raqFileList.add(templatePath + ReportTemplateConstants.MERCH_TRANS_DSUM);
		excelNameList.add("商户交易汇总统计报表");

		// 商户交易明细统计报表
		Context context0 = new Context();
		context0.setParamValue("preWorkDate", preWorkDate);
		context0.setParamValue("cardBrach", branchInfo.getBranchCode());
		context0.setParamValue("cardBrachName", branchInfo.getBranchName());
		context0.setParamValue("currDate", currDate);
		contextList.add(context0);
		raqFileList.add(templatePath + ReportTemplateConstants.MERCH_TRANS_DDETAIL);
		excelNameList.add("商户交易明细统计报表");

		// 售卡明细报表（按发卡机构统计）
		if (!BranchBizConfigCache.isDisableDayReportSellCardDetailSheet(branchInfo.getBranchCode())) { // 非禁止
			Context context1 = new Context();
			context1.setParamValue("cardBrach", branchInfo.getBranchCode());
			context1.setParamValue("cardBrachName", branchInfo.getBranchName());
			context1.setParamValue("preWorkDate", preWorkDate);
			contextList.add(context1);
			raqFileList.add(templatePath + ReportTemplateConstants.SELL_CARD_DETAIL);
			excelNameList.add("售卡明细报表");
		}

		// 充值明细报表（按发卡机构统计）
		Context context2 = new Context();
		context2.setParamValue("cardBrach", branchInfo.getBranchCode());
		context2.setParamValue("cardBrachName", branchInfo.getBranchName());
		context2.setParamValue("preWorkDate", preWorkDate);
		contextList.add(context2);
		raqFileList.add(templatePath + ReportTemplateConstants.DEPOSIT_DETAIL);
		excelNameList.add("充值明细报表");

		// 售卡充值日结算明细报表（按发卡机构统计）
		Context context3 = new Context();
		context3.setParamValue("recvCode", branchInfo.getBranchCode());
		context3.setParamValue("recvName", branchInfo.getBranchName());
		context3.setParamValue("setDate", preWorkDate);
		contextList.add(context3);
		raqFileList.add(templatePath + ReportTemplateConstants.SELL_DEPOSIT_DETAIL);
		excelNameList.add("售卡充值日结算汇总报表（收款）");

		// 售卡充值日结算明细报表（按发卡机构统计）
		Context context4 = new Context();
		context4.setParamValue("payCode", branchInfo.getBranchCode());
		context4.setParamValue("payName", branchInfo.getBranchName());
		context4.setParamValue("setDate", preWorkDate);
		contextList.add(context4);
		raqFileList.add(templatePath + ReportTemplateConstants.SELL_DEPOSIT_PAY_DETAIL);
		excelNameList.add("售卡充值日结算汇总报表（付款）");

		/*
		// 交易明细表（按发卡机构统计）与商户交易明细统计报表 重复，现在去掉
		Context context4 = new Context();
		context4.setParamValue("cardBrach", branchInfo.getBranchCode());
		context4.setParamValue("cardBrachName", branchInfo.getBranchName());
		context4.setParamValue("preWorkDate", preWorkDate);
		contextList.add(context4);
		raqFileList.add(templatePath + ReportTemplateConstants.TRANS_DETAIL_CARD);
		excelNameList.add("交易明细表（按发卡机构统计）");
		*/

		// 交易清算日结算（按发卡机构统计）
		Context context5 = new Context();
		context5.setParamValue("payCode", branchInfo.getBranchCode());
		context5.setParamValue("payName", branchInfo.getBranchName());
		context5.setParamValue("setDate", preWorkDate);
		contextList.add(context5);
		raqFileList.add(templatePath + ReportTemplateConstants.TRANS_DSET_CARD);
		excelNameList.add("交易清算日结算（按发卡机构统计）");

		// 卡库存异动报表（按发卡机构统计）
		Context context6 = new Context();
		context6.setParamValue("preWorkDate", preWorkDate);
		context6.setParamValue("cardBrach", branchInfo.getBranchCode());
		context6.setParamValue("cardBrachName", branchInfo.getBranchName());
		contextList.add(context6);
		raqFileList.add(templatePath + ReportTemplateConstants.STOCK_CHG_CARD);
		excelNameList.add("卡库存异动报表（按发卡机构统计）");

		// 卡状态异动报表（激活卡）
		Context context7 = new Context();
		context7.setParamValue("preWorkDate", preWorkDate);
		context7.setParamValue("cardBrach", branchInfo.getBranchCode());
		context7.setParamValue("cardBrachName", branchInfo.getBranchName());
		contextList.add(context7);
		raqFileList.add(templatePath + ReportTemplateConstants.CARD_STATUS_CHG);
		excelNameList.add("卡状态异动报表");

		try {
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.CARD_DAILY, excelName, excelFileName);
			logger.info("生成发卡机构[{}({})]日统计报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode());
		} catch (Exception e) {
			String msg = LogUtils.r("生成发卡机构[{0}({1})]日报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
		}
		return;
	}
	
	/**
	 * 生成发卡机构PDF格式的日报表
	 * 
	 * @param branchInfo 发卡机构
	 * @param preWorkDate  前一工作日的报表
	 * @param reportFolder 发卡机构报表文件保存目录
	 * @param templatePath  报表模板保存路径
	 * @param currDate 报表日期
	 * @return 生成成功的机构信息 集合
	 * @throws BizException
	 */
	private List<String[]> _generateCardDayPdfReport(BranchInfo branchInfo, String preWorkDate,
			String reportFolder, String templatePath, String currDate) throws BizException {
		List<String[]> reportPathSaveList = new ArrayList<String[]>();
		
		// 商户交易明细统计报表
		try {
			String reportFolder1 = reportFolder + "/" + ReportType.CARD_TRANS_PDF_DAILY.getValue() + "/" + preWorkDate;
			
			String exportFileName = "dayReport_card_pdf_transDetail" + branchInfo.getBranchCode() + "_" + preWorkDate + PDF_SUFFIX;
			String exportFileFullName = reportFolder1 + PATH_SEP + exportFileName;
			String raqPath = templatePath + ReportTemplateConstants.MERCH_TRANS_DDETAIL;
			
			// 商户交易明细统计报表
			Context context = new Context();
			context.setParamValue("preWorkDate", preWorkDate);
			context.setParamValue("cardBrach", branchInfo.getBranchCode());
			context.setParamValue("cardBrachName", branchInfo.getBranchName());
			context.setParamValue("currDate", currDate);
			
			RunqianExportUtil.exportPdf(context, raqPath, reportFolder1, exportFileFullName);
			
			reportPathSaveList.add(new String[] {preWorkDate, ReportType.CARD_TRANS_PDF_DAILY.getValue(), exportFileName, exportFileFullName});
//			this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.CARD_TRANS_PDF_DAILY, exportFileName, exportFileFullName);
			logger.info("生成发卡机构[{}({})]PDF格式的交易明细日统计报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode());
		} catch (PdfExportException e) {
			String msg = LogUtils.r("生成发卡机构[{0}({1})]PDF格式的交易明细日统计报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
		}
		
		// 售卡明细报表（按发卡机构统计）
		try {
			String reportFolder2 = reportFolder + "/" + ReportType.CARD_SELLCARD_PDF_DAILY.getValue() + "/" + preWorkDate;
			
			String exportFileName = "dayReport_card_pdf_sellCardDetail_" + branchInfo.getBranchCode() + "_" + preWorkDate + PDF_SUFFIX;
			String exportFileFullName = reportFolder2 + PATH_SEP + exportFileName;
			String raqPath = templatePath + ReportTemplateConstants.SELL_CARD_DETAIL;
			
			// 售卡明细报表（按发卡机构统计）
			Context context = new Context();
			context.setParamValue("cardBrach", branchInfo.getBranchCode());
			context.setParamValue("cardBrachName", branchInfo.getBranchName());
			context.setParamValue("preWorkDate", preWorkDate);
			
			RunqianExportUtil.exportPdf(context, raqPath, reportFolder2, exportFileFullName);
			
			reportPathSaveList.add(new String[] {preWorkDate, ReportType.CARD_SELLCARD_PDF_DAILY.getValue(), exportFileName, exportFileFullName});
//			this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.CARD_SELLCARD_PDF_DAILY, exportFileName, exportFileFullName);
			logger.info("生成发卡机构[{}({})]PDF格式的售卡明细日统计报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode() );
		} catch (PdfExportException e) {
			String msg = LogUtils.r("生成发卡机构[{0}({1})]PDF格式的售卡明细日统计报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
		}

		// 充值明细报表（按发卡机构统计）
		try {
			String reportFolder3 = reportFolder + "/" + ReportType.CARD_DEPOSIT_PDF_DAILY.getValue() + "/" + preWorkDate;
			
			String exportFileName = "dayReport_card_pdf_depositDetail_" + branchInfo.getBranchCode() + "_" + preWorkDate + PDF_SUFFIX;
			String exportFileFullName = reportFolder3 + PATH_SEP + exportFileName;
			String raqPath = templatePath + ReportTemplateConstants.DEPOSIT_DETAIL;
			
			// 充值明细报表（按发卡机构统计）
			Context context = new Context();
			context.setParamValue("cardBrach", branchInfo.getBranchCode());
			context.setParamValue("cardBrachName", branchInfo.getBranchName());
			context.setParamValue("preWorkDate", preWorkDate);
			
			RunqianExportUtil.exportPdf(context, raqPath, reportFolder3, exportFileFullName);
			
			reportPathSaveList.add(new String[] {preWorkDate, ReportType.CARD_DEPOSIT_PDF_DAILY.getValue(), exportFileName, exportFileFullName});
//			this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.CARD_DEPOSIT_PDF_DAILY, exportFileName, exportFileFullName);
			logger.info("生成发卡机构[{}({})]PDF格式的充值明细日统计报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode());
		} catch (PdfExportException e) {
			String msg = LogUtils.r("生成发卡机构[{0}({1})]PDF格式的充值明细日统计报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
		}
		
		return reportPathSaveList;
	}

	/**
	 * 生成余额报表
	 */
	private void generateBalanceReport(final String preWorkDate, final String reportSavePath,
			final String templatePath) {
		
		List<BranchInfo> cardBranchList = this.branchInfoDAO.findBalanceBranch();
		final String reportFolder = reportSavePath + PATH_SEP + "balanceReport";
		// 线程池
		int threadPoolSize = 2;
		ExecutorService	threadPool	= Executors.newFixedThreadPool(threadPoolSize);
		for (final BranchInfo branchInfo : cardBranchList) {
			//启动线程
			threadPool.execute(new Runnable() {
				public void run() {
					buildBalanceReport(branchInfo, preWorkDate, reportFolder, templatePath);
				}
			});
		}
		shutdownAndWait(threadPool);
		return;
	}

	private void buildBalanceReport(BranchInfo branchInfo, String preWorkDate, String reportFolder,
			String templatePath) {
		BalanceReportSet reportSet = (BalanceReportSet) this.balanceReportSetDAO.findByPk(branchInfo.getBranchCode());
		if (reportSet == null) { // 查看该发卡机构有没有设置余额报表生成规则，没有则跳出循环
			return;
		}
		try {
			Assert.notEmpty(reportSet.getGenerateDate(), "发卡机构["+ branchInfo.getBranchCode() + "]余额报表生成规则的报表生成日期没指定");
			// 检查是否符合报表生成条件
			if (!checkBalanceReportSet(reportSet.getGenerateDate(), preWorkDate)) {
				return;
			}
			List<Context> contextList = new ArrayList<Context>();
			List<String> raqFileList = new ArrayList<String>();
			List<String> excelNameList = new ArrayList<String>();

			String excelName = "balanceReport_" + branchInfo.getBranchCode() + "_" + preWorkDate + EXCEL_SUFFIX;
			String excelFileName = reportFolder + File.separator + excelName;

			// 不为N表示需要生成明细报表。否则则不生成明细报表，只生成汇总报表
			if (!Symbol.NO.equals(reportSet.getNeedDetailFlag())) {
				// 余额报表（按发卡机构统计）
				Context context1 = new Context();
				context1.setParamValue("preWorkDate", preWorkDate);
				context1.setParamValue("cardBrach", branchInfo.getBranchCode());
				context1.setParamValue("cardBrachName", branchInfo.getBranchName());
				contextList.add(context1);
				raqFileList.add(templatePath + ReportTemplateConstants.CARD_BALANCE_AMT);
				excelNameList.add("余额明细报表");
			}
			// 卡余额汇总报表
			Context context2 = new Context();
			context2.setParamValue("preWorkDate", preWorkDate);
			context2.setParamValue("cardBrach", branchInfo.getBranchCode());
			context2.setParamValue("cardBrachName", branchInfo.getBranchName());
			contextList.add(context2);
			raqFileList.add(templatePath + ReportTemplateConstants.CARD_BALANCE_SUM);
			excelNameList.add("卡余额汇总报表");

			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
//				this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.BALANCE, excelName, excelFileName);
			logger.info("生成发卡机构[{}({})]余额报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode());
		} catch (Exception e) {
			String msg = LogUtils.r("生成发卡机构[{0}({1})]余额报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
		}
		return;
	}
		
	/**
	 * <p>
	 * 检查某一发卡机构的余额报表生成规则是否满足报表生成条件：
	 * <li>设置日期是否与系统当前工作日相同</li>
	 * <br>
	 * </p>
	 * 
	 * @param generateDate
	 *            指定生成日期
	 * @param preWorkDate
	 *            系统前一工作日
	 * @return
	 * @throws BizException
	 */
	private boolean checkBalanceReportSet(String generateDate,
			String preWorkDate) throws BizException {
		boolean isSatisfy = false; //是否满足余额报表生成条件

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		Date preWork = null;
		
		try {
			preWork = fmt.parse(preWorkDate);
		} catch (ParseException e) {
			throw new BizException("系统工作日期的格式错误");
		}

		if (DayOfMonthType.EVERY_DAY.getValue().equals(generateDate)) { //设置为每天
			isSatisfy = true;
		} else if (DayOfMonthType.EOM.getValue().equals(generateDate))  {//设置为月底
			String eomDay = DateUtil.getLastDay(preWork);
			isSatisfy = StringUtils.equals(eomDay, preWorkDate);
			
		} else { //generateDay与系统前一工作日的day相同（即清算日day）
			calendar.setTime(preWork);
			//calendar.add(Calendar.DAY_OF_MONTH, 1); // 得到系统当前工作日
			isSatisfy = (NumberUtils.toInt(generateDate) == calendar.get(Calendar.DAY_OF_MONTH));
		}

		return isSatisfy;
	}
	
	/**
	 * 生成所有的商户日报表【多线程】
	 */
	private void generateAllMerchDayReport(final String preWorkDate,
			final String reportSavePath, final String templatePath, final String currDate) throws BizException {
		
		List<MerchInfo> merchList = merchInfoDAO.findAll();
		final String reportFolder = reportSavePath + "/dayReport/merch/" + preWorkDate; // 商户日报表保存路径
		
		// 生成Excel报表的线程池
		int threadPoolSize = 3;
		ExecutorService	threadPool	= Executors.newFixedThreadPool(threadPoolSize);
		for (final MerchInfo merchInfo : merchList) {
			//启动线程
			threadPool.execute(new Runnable() {
				public void run() {
					buildMerchDayReport(merchInfo, preWorkDate, reportFolder, templatePath, currDate);
				}
			});
		}
		
		shutdownAndWait(threadPool);
		return;
	}

	/** shutdown线程池；检查线程池是否终止，没有终止则等待并循环检查 */
	private void shutdownAndWait(ExecutorService threadPool) {
		threadPool.shutdown();
		while(!threadPool.isTerminated()){
			try {
				Thread.sleep(10000);//睡眠10秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成商户日报表
	 */
	private void buildMerchDayReport(MerchInfo merchInfo, String preWorkDate, String reportFolder,
			String templatePath, String currDate) {
			
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "dayReport_merch_" + merchInfo.getMerchId() + "_" + preWorkDate + EXCEL_SUFFIX;
		String excelFileName = reportFolder + PATH_SEP + excelName;
		
		// 商户交易汇总日统计报表
		Context context = new Context();
		context.setParamValue("merchId", merchInfo.getMerchId());
		context.setParamValue("feeDate", preWorkDate);
		context.setParamValue("merchName", merchInfo.getMerchName());
		context.setParamValue("currDate", currDate);
		contextList.add(context);
		raqFileList.add(templatePath + ReportTemplateConstants.MERCH_TRANS_DSUM_MERCH);
		excelNameList.add("商户交易汇总日统计报表");
		
		// 商户交易明细日统计报表
		Context context0 = new Context();
		context0.setParamValue("merchId", merchInfo.getMerchId());
		context0.setParamValue("merchName", merchInfo.getMerchName());
		context0.setParamValue("preWorkDate", preWorkDate);
		contextList.add(context0);
		raqFileList.add(templatePath + ReportTemplateConstants.MERCH_TRANS_DDETAIL_MERCH);
		excelNameList.add("商户交易明细日统计报表");

		// 交易明细报表（按商户统计）
		Context context1 = new Context();
		context1.setParamValue("preWorkDate", preWorkDate);
		context1.setParamValue("merchId", merchInfo.getMerchId());
		context1.setParamValue("merchName", merchInfo.getMerchName());
		contextList.add(context1);
		raqFileList.add(templatePath + ReportTemplateConstants.TRANS_DETAIL_MERCH);
		excelNameList.add("交易明细报表（按商户统计）");

		// 交易清算日结算（按商户统计）
		Context context2 = new Context();
		context2.setParamValue("setDate", preWorkDate);
		context2.setParamValue("recvCode", merchInfo.getMerchId());
		context2.setParamValue("recvName", merchInfo.getMerchName());
		contextList.add(context2);
		raqFileList.add(templatePath + ReportTemplateConstants.TRANS_DSET_MERCH);
		excelNameList.add("交易清算日结算（按商户统计）");

		try {
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			this.addReportPathSave(merchInfo.getMerchId(), preWorkDate, ReportType.MERCH_DAILY, excelName, excelFileName);
			logger.info("生成商户[{}({})]日统计报表成功", merchInfo.getMerchName(), merchInfo.getMerchId());
		} catch (Exception e) {
			String msg = String.format("生成商户[%s(%s)]日统计报表失败。原因，%s", merchInfo.getMerchName(), merchInfo.getMerchId(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog("", merchInfo.getMerchId(), msg, SysLogType.WARN);
		}
		return;
	}
	
	/**
	 * 生成PDF格式的商户交易明细日统计报表
	 * 
	 * @param merchInfo 商户信心
	 * @param preWorkDate  前一工作日的报表
	 * @param reportFolder 发卡机构报表文件保存目录
	 * @param templatePath  报表模板保存路径
	 * @param currDate 报表日期
	 * @return 生成失败的商户号
	 * @throws BizException
	 */
	private String generateMerchDayPdfReport(MerchInfo merchInfo, String preWorkDate,
			String reportFolder, String templatePath, String currDate) throws BizException {
		// 商户交易明细统计报表
		try {
			reportFolder = reportFolder + "/" + ReportType.MERCH_TRANS_PDF_DAILY.getValue() + "/" + preWorkDate;
			
			String exportFileName = "dayReport_merch_pdf_transDetail" + merchInfo.getMerchId() + "_" + preWorkDate + PDF_SUFFIX;
			String exportFileFullName = reportFolder + PATH_SEP + exportFileName;
			String raqPath = templatePath + ReportTemplateConstants.TRANS_DETAIL_MERCH;
			
			// 商户交易明细统计报表
			Context context = new Context();
			context.setParamValue("preWorkDate", preWorkDate);
			context.setParamValue("merchId", merchInfo.getMerchId());
			context.setParamValue("merchName", merchInfo.getMerchName());
			
			RunqianExportUtil.exportPdf(context, raqPath, reportFolder, exportFileFullName);
//			this.addReportPathSave(merchInfo.getMerchId(), preWorkDate, ReportType.CARD_TRANS_PDF_DAILY, exportFileName, exportFileFullName);
			logger.info("生成商户[{}({})]PDF格式的交易明细日统计报表成功", merchInfo.getMerchName(), merchInfo.getMerchId());
			
		} catch (PdfExportException e) {
			String msg = LogUtils.r("生成商户[{0}({1})]PDF格式的交易明细日统计报表失败，原因：{2}", merchInfo.getMerchName(),
					merchInfo.getMerchId(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog("", merchInfo.getMerchId(), msg, SysLogType.WARN);
			return merchInfo.getMerchId();
		}
		
		return StringUtils.EMPTY;
	}

	
	/**
	 * 生成所有的售卡代理日报表
	 * 
	 * @param preWorkDate
	 *            前一工作日的报表
	 * @param reportSavePath
	 *            报表文件保存路径
	 * @param templatePath
	 *            报表模板保存路径
	 * @throws BizException
	 */
	private void generateAllProxyDayReport(final String preWorkDate,
			String reportSavePath, final String templatePath, final String currDate) throws BizException {

		List<BranchInfo> list = branchInfoDAO.findByType(BranchType.CARD_SELL.getValue());
		String reportFolder = reportSavePath + "/dayReport/saleProxy/" + preWorkDate; // 售卡代理日报表保存路径
		for (BranchInfo branchInfo : list) {
			buildProxyDayReport(branchInfo, preWorkDate, reportFolder, templatePath, currDate);
		}
		return;
	}

	/**
	 * 生成售卡代理日报表
	 * @param branchInfo 售卡代理
	 * @param preWorkDate  前一工作日的报表
	 * @param reportFolder 售卡代理报表文件保存目录
	 * @param templatePath  报表模板保存路径
	 * @param currDate 报表日期
	 * @return 生成失败的机构号
	 * @throws BizException
	 */
	private void buildProxyDayReport(BranchInfo branchInfo, String preWorkDate, String reportFolder,
			String templatePath, String currDate) {
		
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "dayReport_saleProxy_" + branchInfo.getBranchCode() + "_" + preWorkDate + EXCEL_SUFFIX;
		String excelFileName = reportFolder + File.separator + excelName;

		// 卡库存异动报表（按售卡代理统计）
		Context context1 = new Context();
		context1.setParamValue("preWorkDate", preWorkDate);
		context1.setParamValue("cardBrach", branchInfo.getBranchCode());
		context1.setParamValue("cardBrachName", branchInfo.getBranchName());
		contextList.add(context1);
		raqFileList.add(templatePath + ReportTemplateConstants.STOCK_CHG_PROXY);
		excelNameList.add("卡库存异动报表（按售卡代理统计）");

		try {
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			this.addReportPathSave(branchInfo.getBranchCode(), preWorkDate, ReportType.PROXY_DAILY, excelName, excelFileName);
			logger.info("生成售卡代理[{}({})]日统计报表成功", branchInfo.getBranchName(), branchInfo.getBranchCode());
		} catch (Exception e) {
			String msg = LogUtils.r("生成售卡代理[{0}({1})]日统计报表失败，原因：{2}", 
					branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
		}
		return;
	}
	
	/**
	 * 生成所有的运营中心月报表
	 */
	private void generateAllCenterMonthReport(String preWorkDate, String reportSavePath, String templatePath,
			String currDate) {
		List<BranchInfo> centerList = branchInfoDAO.findByType(BranchType.CENTER.getValue());
		BranchInfo center = centerList.get(0);
		
		String reportFolder = reportSavePath + "/monthReport/center/" + preWorkDate;
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "monthReport_center_" + center.getBranchCode() + "_" + preWorkDate + EXCEL_SUFFIX;
		String excelFileName = reportFolder + PATH_SEP + excelName;

		// 运营分支机构交易汇总日统计报表
		Context context1 = new Context();
		context1.setParamValue("setMonth", preWorkDate);
		context1.setParamValue("currDate", currDate);
		contextList.add(context1);
		raqFileList.add(templatePath + ReportTemplateConstants.FENZHI_TRANS_MSUM);
		excelNameList.add("运营分支机构交易汇总月统计报表");
		
		// 运营分支机构计费子交易汇总月统计报表
		Context context2 = new Context();
		context2.setParamValue("setMonth", preWorkDate);
		context2.setParamValue("currDate", currDate);
		contextList.add(context2);
		raqFileList.add(templatePath + ReportTemplateConstants.FENZHI_SUBTRANS_MSUM);
		excelNameList.add("运营分支机构计费子交易汇总月统计报表");

		try {
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			this.addReportPathSave(center.getBranchCode(), preWorkDate, ReportType.CENTER_MONTH, excelName, excelFileName);
			logger.info("生成运营中心[{}({})]月统计报表成功", center.getBranchName(), center.getBranchCode());
		} catch (Exception e) {
			String msg = LogUtils.r("生成运营中心[{0}({1})]月报表失败，原因：{2}", 
					center.getBranchName(), center.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(center.getBranchCode(), "", msg, SysLogType.WARN);
		}
		return;
	}
	
	/**
	 * 生成所有的运营分支机构月报表
	 * 
	 * @param preWorkDate
	 *            统计月份
	 * @param reportSavePath
	 *            报表文件保存路径
	 * @param templatePath
	 *            报表模板保存路径
	 * @param currDate
	 * 			  当前日期
	 * @return
	 * @throws BizException
	 */
	private void generateAllFenzhiMonthReport(String preWorkMonth,
			String reportSavePath, String templatePath, String currDate) throws BizException {

		List<BranchInfo> fenzhiList = branchInfoDAO.findByType(BranchType.FENZHI.getValue());
		String reportFolder = reportSavePath + "/monthReport/fenzhi/" + preWorkMonth;

		for (BranchInfo branchInfo : fenzhiList) {
			List<Context> contextList = new ArrayList<Context>();
			List<String> raqFileList = new ArrayList<String>();
			List<String> excelNameList = new ArrayList<String>();

			String excelName = "monthReport_fenzhi_" + branchInfo.getBranchCode() + "_" + preWorkMonth + EXCEL_SUFFIX;
			String excelFileName = reportFolder + PATH_SEP + excelName;

			// 发卡机构交易汇总月总统计报表
			Context context1 = new Context();
			context1.setParamValue("feeMonth", preWorkMonth);
			context1.setParamValue("chlCode", branchInfo.getBranchCode());
			context1.setParamValue("currDate", currDate);
			contextList.add(context1);
			raqFileList.add(templatePath + ReportTemplateConstants.CARD_TRANS_MSUM);
			excelNameList.add("发卡机构交易汇总月总统计报表");

			// 发卡机构售卡汇总月统计报表
			Context context2 = new Context();
			context2.setParamValue("feeMonth", preWorkMonth);
			context2.setParamValue("chlCode", branchInfo.getBranchCode());
			context2.setParamValue("currDate", currDate);
			contextList.add(context2);
			raqFileList.add(templatePath + ReportTemplateConstants.CARD_SELL_MSUM);
			excelNameList.add("发卡机构售卡汇总月统计报表");

			// 发卡机构充值汇总月统计报表
			Context context3 = new Context();
			context3.setParamValue("feeMonth", preWorkMonth);
			context3.setParamValue("chlCode", branchInfo.getBranchCode());
			context3.setParamValue("currDate", currDate);
			contextList.add(context3);
			raqFileList.add(templatePath + ReportTemplateConstants.CARD_DEPOSIT_MSUM);
			excelNameList.add("发卡机构充值汇总月统计报表");
			
			// 发卡机构计费子交易汇总月总统计报表
			Context context4 = new Context();
			context4.setParamValue("feeMonth", preWorkMonth);
			context4.setParamValue("chlCode", branchInfo.getBranchCode());
			context4.setParamValue("currDate", currDate);
			contextList.add(context4);
			raqFileList.add(templatePath + ReportTemplateConstants.CARD_SUBTRANS_MSUM);
			excelNameList.add("发卡机构计费子交易汇总月总统计报表");

			try {
				RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
				this.addReportPathSave(branchInfo.getBranchCode(), preWorkMonth, ReportType.FENZHI_MONTH, excelName, excelFileName);
				logger.info("生成运营分支机构[{}({})]月统计报表成功", branchInfo.getBranchName(),  branchInfo.getBranchCode());
			} catch (ExcelExportException e) {
				String msg = LogUtils.r("生成运营机构[{0}({1})]月报表失败，原因：{2}", 
						branchInfo.getBranchName(), branchInfo.getBranchCode(), e.getMessage());
				logger.error(msg, e);
				this.insertSysLog(branchInfo.getBranchCode(), "", msg, SysLogType.WARN);
				// 跳出当前循环
				continue;
			}
		}
		return;
	}

	/**
	 * 生成所有的发卡机构的月统计报表
	 * 
	 * @param preWorkMonth
	 *            要统计的月份，格式yyyyMM
	 * @param reportSavePath
	 *            生成的报表文件保存路径
	 * @param templatePath
	 *            报表的模板文件保存路径
	 * @return
	 * @throws BizException
	 */
	private void generateAllCardMonthReport(final String preWorkMonth, final String reportSavePath,
			final String templatePath, final String currDate) throws BizException {
		// 取得所有发卡机构的列表
		List<BranchInfo> cardBranchList = branchInfoDAO.findByType(BranchType.CARD.getValue());
		final String reportFolder = reportSavePath + "/monthReport/card/" + preWorkMonth; // 月报表保存路径（只按发卡机构统计）
		
		// 线程池
		int threadPoolSize = 2;
		ExecutorService	threadPool	= Executors.newFixedThreadPool(threadPoolSize);
		for (final BranchInfo branchInfo : cardBranchList) {
			// 启动线程
			threadPool.execute(new Runnable() {
				public void run() {
					buildCardMonthReport(branchInfo, preWorkMonth, reportFolder, templatePath, currDate);
				}
			});
		}
		
		shutdownAndWait(threadPool);
		return;
	}

	private void buildCardMonthReport(BranchInfo info, String preWorkMonth, String reportFolder,
			String templatePath, String currDate) {
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();
		String excelName = "monthReport_card_" + info.getBranchCode() + "_" + preWorkMonth + EXCEL_SUFFIX;
		String excelFileName = reportFolder + File.separator + excelName;
		
		// 商户交易汇总月统计报表
		Context context0 = new Context();
		context0.setParamValue("feeMonth", preWorkMonth);
		context0.setParamValue("branchCode", info.getBranchCode());
		context0.setParamValue("branchName", info.getBranchName());
		context0.setParamValue("currDate", currDate);
		contextList.add(context0);
		raqFileList.add(templatePath + ReportTemplateConstants.MERCH_TRANS_MSUM);
		excelNameList.add("商户交易汇总月统计报表");

		// 售卡充值月统计汇总报表
		Context context1 = new Context();
		context1.setParamValue("setMonth", preWorkMonth);
		context1.setParamValue("recvCode", info.getBranchCode());
		context1.setParamValue("recvName", info.getBranchName());
		contextList.add(context1);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_SELL_DEPOSIT_CARD);
		excelNameList.add("售卡充值月统计汇总报表（收款）");

		// 售卡充值月统计汇总报表
		Context context7 = new Context();
		context7.setParamValue("setMonth", preWorkMonth);
		context7.setParamValue("payCode", info.getBranchCode());
		context7.setParamValue("payName", info.getBranchName());
		contextList.add(context7);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_SELL_DEPOSIT_CARD_PAY);
		excelNameList.add("售卡充值月统计汇总报表（付款）");

		// 交易清算月统计汇总报表[发卡机构将交易资金清算给商户]
		Context context2 = new Context();
		context2.setParamValue("setMonth", preWorkMonth);
		context2.setParamValue("payCode", info.getBranchCode());
		context2.setParamValue("payName", info.getBranchName());
		contextList.add(context2);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_TRANS_DSET_CARD);
		excelNameList.add("交易清算月统计汇总报表");

		// 商户手续费(从商户收);商户手续费月统计汇总报表[发卡机构从商户那收手续费]
		Context context3 = new Context();
		context3.setParamValue("feeMonth", preWorkMonth);
		context3.setParamValue("branchCode", info.getBranchCode());
		context3.setParamValue("branchName", info.getBranchName());
		contextList.add(context3);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_MERCH_FEE_CARD);
		excelNameList.add("商户手续费月统计汇总报表");

		// 上缴的运营手续费;发卡机构运营手续费月统计汇总报表[发卡机构付钱给运营中心]
		Context context4 = new Context();
		context4.setParamValue("feeMonth", preWorkMonth);
		context4.setParamValue("branchCode", info.getBranchCode());
		context4.setParamValue("branchName", info.getBranchName());
		contextList.add(context4);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_OPERATE_FEE_CARD);
		excelNameList.add("发卡机构运营手续费月统计汇总报表");

		// 返利给售卡代理的；售卡代理返利月统计汇总报表[发卡机构付钱给售卡代理]
		Context context5 = new Context();
		context5.setParamValue("feeMonth", preWorkMonth);
		context5.setParamValue("branchCode", info.getBranchCode());
		context5.setParamValue("branchName", info.getBranchName());
		contextList.add(context5);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_PROXY_RETURN_CARD);
		excelNameList.add("售卡代理返利月统计汇总报表");

		// 商户代理分润月统计汇总报表[发卡机构付钱给商户代理]
		Context context6 = new Context();
		context6.setParamValue("feeMonth", preWorkMonth);
		context6.setParamValue("branchCode", info.getBranchCode());
		context6.setParamValue("branchName", info.getBranchName());
		contextList.add(context6);
		raqFileList.add(templatePath + ReportTemplateConstants.MONTH_PROXY_SHARES_CARD);
		excelNameList.add("商户代理分润月统计汇总报表");

		try {
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			this.addReportPathSave(info.getBranchCode(), preWorkMonth, ReportType.CARD_MONTH, excelName, excelFileName);
			logger.info("生成发卡机构[{}({})]月统计报表成功", info.getBranchName(), info.getBranchCode());
		} catch (Exception e) {
			String msg = LogUtils.r("生成发卡机构[{0}({1})]月报表失败，原因：{2}", info.getBranchName(),
					info.getBranchCode(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog(info.getBranchCode(), "", msg, SysLogType.WARN);
		}
		return;
	}
	

	/**
	 * 生成所有的商户月报表
	 */
	private void generateAllMerchMonthReport(final String feeMonth,
			String reportSavePath, final String templatePath,final String currDate) throws BizException {
		List<MerchInfo> merchList = merchInfoDAO.findAll();
		final String reportFolder = reportSavePath + "/monthReport/merch/" + feeMonth; // 商户日报表保存路径
		
		// 线程池
		int threadPoolSize = 2;
		ExecutorService	threadPool	= Executors.newFixedThreadPool(threadPoolSize);
		for (final MerchInfo merchInfo : merchList) {
			// 启动线程
			threadPool.execute(new Runnable() {
				public void run() {
					buildMerchMonthReport(merchInfo, feeMonth, reportFolder, templatePath, currDate);
				}
			});
		}
		
		shutdownAndWait(threadPool);
		return;
	}

	private void buildMerchMonthReport(MerchInfo merchInfo, String feeMonth, String reportFolder,
			String templatePath, String currDate) {
		List<Context> contextList = new ArrayList<Context>();
		List<String> raqFileList = new ArrayList<String>();
		List<String> excelNameList = new ArrayList<String>();

		String excelName = "monthReport_merch_" + merchInfo.getMerchId() + "_" + feeMonth + EXCEL_SUFFIX;
		String excelFileName = reportFolder + PATH_SEP + excelName;

		// 商户交易汇总月统计报表
		Context context = new Context();
		context.setParamValue("feeMonth", feeMonth);
		context.setParamValue("merchId", merchInfo.getMerchId());
		context.setParamValue("merchName", merchInfo.getMerchName());
		context.setParamValue("currDate", currDate);
		contextList.add(context);
		raqFileList.add(templatePath + ReportTemplateConstants.MERCH_TRANS_MSUM_MERCH);
		excelNameList.add("商户交易汇总月统计报表");

		try {
			RunqianExportUtil.exportExcel2007(contextList, raqFileList, excelNameList, reportFolder, excelFileName);
			this.addReportPathSave(merchInfo.getMerchId(), feeMonth, ReportType.MERCH_MONTH, excelName,	excelFileName);
			logger.info("生成商户[{}({})]月统计报表成功", merchInfo.getMerchName(), merchInfo.getMerchId());
		} catch (Exception e) {
			String msg = LogUtils.r("生成商户[{0}({1})]月报表失败，原因：{2}", merchInfo.getMerchName(), merchInfo.getMerchId(), e.getMessage());
			logger.error(msg, e);
			this.insertSysLog("", merchInfo.getMerchId(), msg, SysLogType.WARN);
		}
		return;
	}

	/**
	 * 取得报表模板类所在目录
	 * 
	 * @return
	 */
	private String getReportTemplatePath() {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.substring(0, path.indexOf("WEB-INF")) + "reportTemplate";
		try {
			String tmpPath =  java.net.URLDecoder.decode(path,"utf-8");//解码路径中可能出现的 中文和空格
			path = tmpPath;
		} catch (UnsupportedEncodingException e) {
			logger.info("路径解码出错:{},原因:{}", path, e.getMessage());
		} 
		return path;
	}

	/**
	 * 根据查询条件返回新的list
	 * 
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<String> getListResult(List<String> oldList, String... params) {
		if (CollectionUtils.isEmpty(oldList)) {
			return null;
		}
		if (ArrayUtils.isEmpty(params)) {
			return oldList;
		}
		List<String> list = new ArrayList<String>();
		for (String path : oldList) {
			String name = path.substring(path.lastIndexOf(PATH_SEP) + 1,
					path.length());
			if (StringUtils.isBlank(name)) {
				continue;
			}
			name = name.split("\\.")[0];
			String[] names = name.split("\\_");
			if (ArrayUtils.isEmpty(names)) {
				continue;
			}
			List<String> nameList = Arrays.asList(names);
			List<String> paramsList = Arrays.asList(params);
			paramsList = this.removeNull(paramsList);
			if (nameList.containsAll(paramsList)) {
				list.add(path);
			}
		}
		return list;
	}
	
	/**
	 * 对txt、xls旧报表文件过滤
	 * 根据查询条件返回新的list
	 * 
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<String> getFileListResult(List<String> oldList, String... params) {
		
		if (ArrayUtils.isEmpty(params)) {
			return oldList;
		}
		
		List<String> list = new ArrayList<String>();
		boolean flag;
		
		for (String path : oldList) {
			flag = true;
			path = path.replace("\\", "/");
			String name = path.substring(path.lastIndexOf("/") + 1, path.length());
			name = name.split("\\.")[0]; // 去掉后缀
			
			if (StringUtils.isBlank(name)) {
				continue;
			}
			
			String branchCode = params[0];
			if(StringUtils.isNotEmpty(branchCode)){
				flag = name.startsWith(branchCode) ? true : false;
			}
			
			String reportType = params[1];
			if(flag && StringUtils.isNotEmpty(reportType)){
				flag = name.contains(reportType) ? true : false;
			}
			
			String reportDate = params[2];
			if(flag && StringUtils.isNotEmpty(reportDate)){
				flag = name.endsWith(reportDate) ? true : false;
			}
			
			if(flag){
				list.add(path);
			}
			
		}
		return list;
	}
	
	/**
	 * 对txt、xls旧报表文件过滤
	 * 根据查询条件返回新的list
	 * 
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<String> getTxtOldFileListResult(List<String> oldList, String... params) {
		
		if (ArrayUtils.isEmpty(params)) {
			return oldList;
		}
		
		List<String> list = new ArrayList<String>();
		boolean flag;
		
		for (String path : oldList) {
			flag = true;
			path = path.replace("\\", "/");
			String name = path.substring(path.lastIndexOf("/") + 1, path.length());
			name = name.split("\\.")[0]; // 去掉后缀
			
			String tmpBranchCode = null;
			String tmpReportType = null;
			String tmpReportDate = null;
			Matcher matcher = Pattern.compile("([0-9]+)([a-zA-z]+)([0-9]+)").matcher(name);
			if(matcher.find()){
				tmpBranchCode = matcher.group(1);
				tmpReportType =matcher.group(2);
				tmpReportDate =matcher.group(3);
			}else{
				logger.warn("文件名格式不对:"+name);
				continue;
			}
			if (StringUtils.isBlank(name)) {
				continue;
			}
			
			String branchCode = params[0];
			if(StringUtils.isNotEmpty(branchCode)){
				flag = tmpBranchCode.startsWith(branchCode) ? true : false;
			}
			
			String reportType = params[1];
			if(flag && StringUtils.isNotEmpty(reportType)){
				flag = tmpReportType.contains(reportType) ? true : false;
			}
			
			String startDate = params[2];
			String endDate = params[3];
			if(StringUtils.isNotEmpty(startDate)){
				if(!StringUtils.isNotEmpty(endDate)){
					endDate = startDate;
				}
			}else {
                if(StringUtils.isNotEmpty(endDate)){
                	startDate = endDate; 
				}
			}
			if(flag && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
				flag = ( tmpReportDate.compareTo(startDate) >= 0 &&  tmpReportDate.compareTo(endDate) <= 0) ? true : false;
			}
			
//			String reportDate = params[2];
//			if(flag && StringUtils.isNotEmpty(reportDate)){
//				flag = name.endsWith(reportDate) ? true : false;
//			}
			
			if(flag){
				list.add(path);
			}
			
		}
		return list;
	}

	/**
	 * 把文件路径列表转化为BusiReport列表
	 * 
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<BusiReport> getBusiReportList(List<String> fileList) {
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		
		for (String path : fileList) {
			path = path.replace("\\", "/");
			BusiReport busiReport = new BusiReport();

			busiReport.setFilePath(path);
			
			//String name = path.substring(path.lastIndexOf(PATH_SEP) + 1, path.length());
			String name = StringUtils.substringAfterLast(path, "/");
			busiReport.setReportName(name);
			name = name.split("\\.")[0]; // 去掉后缀
			String branchCode = null;
			String reportType = null;
			String reportDate = null;
			Matcher matcher = Pattern.compile("([0-9]+)([a-zA-z]+)([0-9]+)").matcher(name);
			if(matcher.find()){
				branchCode = matcher.group(1);
				reportType =matcher.group(2);
				reportDate =matcher.group(3);
			}else{
				logger.warn("文件名格式不对:"+name);
				//保留这种处理方式
				branchCode = name.substring(0, 8);//商户号为15位
				reportType = name.substring(8, name.length()-8);
				reportDate = name.substring(name.length()-8, name.length());
			}
			
			busiReport.setMerchantNo(branchCode);
			busiReport.setReportType(reportType);
			busiReport.setReportDate(reportDate);
			
			busiReportList.add(busiReport);
		}
		
		return busiReportList;
	}

	private List<String> removeNull(List<String> oldList) {
		if (CollectionUtils.isEmpty(oldList)) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String value : oldList) {
			if (StringUtils.isNotBlank(value)) {
				list.add(value);
			}
		}
		return list;
	}

	/*public Paginater getOldReportTxtList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		
		ParaMgr mgr = ParaMgr.getInstance();
		String ftpServer = mgr.getFtpServerRemoteIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		
		//生成的旧报表txt文件保存路径
		String reportFileSavePath = mgr.getOldTxtReportSavePath(); 

		String reportDate = (String) params.get("reportDate");
		String reportType = (String) params.get("reportType");
		String branchCode = (String) params.get("branchCode");
		//List<BranchInfo> branchList = (List<BranchInfo>) params.get("cardBranchList");
		
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		List<String> filterFileList = new ArrayList<String>();
		
		List<FtpFile> fileList = null;
		try {
			FtpUtil ftpUtil = new FtpUtil(ftpServer, user, pwd, 21);
			ftpUtil.connect();
			
			String[] filePathList = reportFileSavePath.split("/");
			
			String home = "";
			for(int i = 0; i < filePathList.length-1; i++){
				home += "/".concat(filePathList[i]);
			}
			home = home.substring(1);
			//String home = reportFileSavePath.substring(0, 13);
			//String path = reportFileSavePath.substring(13,23);
			String path = "/".concat(filePathList[filePathList.length-1]);
			fileList = ftpUtil.getFileList(path, fileList, home);
			
			ftpUtil.disconnect();
			
			// 过滤目录
			for (FtpFile file : fileList) {
				if (!file.isDirectory()) {
					filterFileList.add(file.getFilePath());
				}
			}
			
			// 过滤文件列表，筛选出符合条件（branchCode, reportType, reportDate）的文件列表
			filterFileList = getFileListResult(filterFileList, branchCode, reportType, reportDate);
			
			// 把文件列表转化为BusiReport列表
			busiReportList = getBusiReportList(filterFileList);
		
		} catch (FTPException e) {
			throw new BizException("获取FTP服务器上旧报表txt文件列表时发生FTPException:" + e.getMessage());
		} catch (IOException e) {
			throw new BizException("获取FTP服务器上旧报表txt文件列表时发生IOException:" + e.getMessage());
		} catch (ParseException e) {
			throw new BizException("获取FTP服务器上旧报表txt文件列表时发生ParseException:" + e.getMessage());
		}
		
		Paginater paginater = new Paginater(fileList.size(), pageNumber, pageSize);
		List<BusiReport> content = new ArrayList<BusiReport>();
		
		if (CollectionUtils.isEmpty(busiReportList)) {
			return Paginater.EMPTY;
		}
		
		for (int i = (pageNumber - 1) * pageSize; i < busiReportList.size() && i < pageNumber * pageSize; i++) {
			BusiReport report = busiReportList.get(i);
			content.add(report);
		}
		
		paginater.setData(content);
		return paginater;

	}*/
	
	public Paginater getOldReportTxtList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		
		//生成的旧报表txt文件保存路径
		String reportFileSavePath = mgr.getOldTxtReportSavePath(); 

//		String reportDate = (String) params.get("reportDate");
		String startDate = (String) params.get("startDate");
		String endDate = (String) params.get("endDate");
		String reportType = (String) params.get("reportType");
		String branchCode = (String) params.get("branchCode");
		//List<BranchInfo> branchList = (List<BranchInfo>) params.get("cardBranchList");
		
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		List<String> filterFileList = new ArrayList<String>();
			
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);

		CommonNameDirFilesCallBackImpl callBack = new CommonNameDirFilesCallBackImpl(reportFileSavePath, prefix, suffix);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);
		logger.debug("ftp查询txt旧报表文件列表结果[{}]", flag);
		List<String> fileList = new ArrayList<String>();
		if (flag) {
			fileList = callBack.getNameFilesRefer();
		}
		
		// 过滤文件列表，筛选出符合条件（branchCode, reportType, reportDate）的文件列表
		filterFileList = getTxtOldFileListResult(fileList, branchCode, reportType, startDate, endDate);
		
		// 把文件列表转化为BusiReport列表
		busiReportList = getBusiReportList(filterFileList);
		
		busiReportList = getOrderedList(busiReportList);//排序

		return CommonHelper.getListPage(busiReportList, pageNumber, pageSize);

	}
	
	/**
	 * 测试用的
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("1:"+ Thread.currentThread().getContextClassLoader().getResource(""));

		System.out.println("2:"+ GenerateReportServiceImpl.class.getClassLoader().getResource("").getPath());

		System.out.println("3:" + ClassLoader.getSystemResource(""));
		System.out.println("4:" + GenerateReportServiceImpl.class.getResource(""));
		System.out.println("5:" + GenerateReportServiceImpl.class.getResource("/"));
		// Class文件所在路径
		System.out.println("6:" + new File("/").getAbsolutePath());
		System.out.println("7:" + System.getProperty("user.dir"));
		String path = GenerateReportServiceImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.replace("\\", "/");
		path = path.substring(0, path.indexOf("WEB-INF")) + "reportTemplate";
		System.out.println("8:" + path);

		String preWorkDate = "201103";
		GenerateReportServiceImpl a = new GenerateReportServiceImpl();
		System.out.println(a.checkBalanceReportSet("03", preWorkDate));
	}

	public boolean downloadTxtOldReportFile(String fullPath)
			throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		
		String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1);
		String ftpPath = fullPath.substring(0, fullPath.indexOf(fileName) - 1);
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);
		
		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不到旧报表txt文件[" + fileName + "]";
			logger.error(msg);
			throw new BizException(msg);
		}
		
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取旧报表txt文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.error(msg, ex);
			throw new BizException(msg);
		}
		return result;
	}

	public Paginater getOldReportXlsList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		List<BusiReport> list = new ArrayList<BusiReport>();
		String reportFileSavePath = SysparamCache.getInstance().getOldReportSavePath(); //生成的旧报表xls文件保存路径
		//String reportFileSavePath = "D:/oldReportFile"; 
		
//		String reportDate = (String) params.get("reportDate");
		String startDate = (String) params.get("startDate");
		String endDate = (String) params.get("endDate");
		String branchCode = (String) params.get("branchCode");
		String reportType = (String) params.get("reportType");

		List<String> fileList = new LinkedList<String>();
		
		// 查出符合条件的所有旧报表文件列表
		fileList = IOUtil.getFileList(fileList, reportFileSavePath);
		
		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		}

//		if (StringUtils.isNotBlank(reportDate)
//				|| StringUtils.isNotBlank(branchCode)
//				|| StringUtils.isNotBlank(reportType)) {
//			fileList = getFileListResult(fileList, branchCode, reportType, reportDate);
//		}
//		
		// 过滤文件列表，筛选出符合条件（branchCode, reportType, reportDate）的文件列表
		fileList = getTxtOldFileListResult(fileList, branchCode, reportType, startDate, endDate);
		
		list = getBusiReportList(fileList);
		
		list = getOrderedList(list);//排序
		
		Paginater paginater = new Paginater(list.size(), pageNumber, pageSize);
		List<BusiReport> content = new ArrayList<BusiReport>();
		
		for (int i = (pageNumber - 1) * pageSize; i < list.size() && i < pageNumber * pageSize; i++) {
			BusiReport report = list.get(i);
			content.add(report);
		}
		paginater.setData(content);
		return paginater;
	}

	/**
	 * 生成累积消费充值余额报表
	 * 
	 * @param reportSavePath
	 *            报表文件保存路径
	 * @return
	 * @throws BizException
	 */
	public boolean generateConsmChargeBalReport(String reportSavePath) throws BizException {
		
		String reportFolder = reportSavePath + PATH_SEP + "consmChargeBalReport"; //生成的报表文件保存路径
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("issType", IssType.CARD.getValue());
		//params.put("status", CardTypeState.NORMAL.getValue());
		
		List<ReportConfigPara> reportConfigParaList = reportConfigParaDAO.getReportConfigParaList(params);

		File reportDir = FileHelper.getDirectoryFile(reportFolder);
		
		String[] head = new String[]{"发卡机构", "卡类型", "卡号", "售卡日期", "累积储值资金消费金额", "累积充值金额", "子账户总金额"};
		
		// 循环遍历报表配置生成参数，生成配置机构的累积消费充值余额报表
		for (ReportConfigPara reportConfigPara : reportConfigParaList) {
			String fileName = getConsmChargeBalReportName(reportConfigPara.getInsId());
			// 1.2 根据传入的head构造好本地文件
			try {
				File webDirFile = txtService.getWebDirFile(reportDir, fileName);
				params.clear();
				params.put("cardIssuer", reportConfigPara.getInsId());
				List<CardInfo> consmChargeBalList = this.cardInfoDAO.getConsmChargeBalDataList(params);
				this.txtService.geneteConsmChargeBalReportTxt(consmChargeBalList, webDirFile, head);
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return true;
	}
	
	/**
	 * 
	  * <p>累积消费充值余额报表的文件名称</p>
	  * @return  
	  * @version: 2011-5-19 下午06:45:41
	  * @See:
	 */
	private String getConsmChargeBalReportName(String branchCode) {
		return new StringBuilder().append(branchCode).append("_").append(DateUtil.getCurrentDate()).toString().concat(suffix[0]);
	}
	
	/**
	 *  将报表生成的路径信息记录到数据库中
	 * @param merchantNo 商户或机构号
	 * @param reportDate 报表日期
	 * @param reportType 报表类型
	 * @param excelName 报表名称
	 * @param excelFileName 报表文件目录（包含报表名称）
	 * @throws BizException
	 */
	private void addReportPathSave(Map reportPathSaveMap) {

		if (MapUtils.isEmpty(reportPathSaveMap)) {
			return;
		}
		
//		FlinkAppEvent appEvent = new ReportPathSaveAppEvent(this, FlinkOperateEnum.ADD, reportPathSaveMap);
//		
//		// 发布报表保存路径的事件
//		new FlinkAppEventPublisher(appEvent).publishFlinkEvent();
		
		StopWatch stopWatch = new StopWatch();
		try {
			List<ReportPathSave> list = new ArrayList<ReportPathSave>(reportPathSaveMap.size());
			
			for (Iterator iterator = reportPathSaveMap.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, List<String[]>> entry = (Map.Entry<String, List<String[]>>) iterator.next();
				
				String merchantNo = entry.getKey();
				List<String[]> reportSaveParamList = entry.getValue();
	//			String[] reportSaveParams = entry.getValue();
				
				for (String[] reportSaveParams : reportSaveParamList) {
					ReportPathSave pathSave = new ReportPathSave();
					
					pathSave.setMerchantNo(merchantNo);
					pathSave.setReportDate(reportSaveParams[0]);
					pathSave.setReportType(reportSaveParams[1]);
					pathSave.setGenDate(DateUtil.formatDate("yyyyMMdd"));
					pathSave.setReportName(reportSaveParams[2]);
					pathSave.setFilePath(reportSaveParams[3]);
					
					list.add(pathSave);
				}
			}
		
			stopWatch.start();
			logger.info("批量插入报表的文件的保存路径的条数为[{}]", list.size());
			this.reportPathSaveDAO.insertBatch(list);
			logger.info("批量插入报表的文件的保存路径数据成功");
		} catch (Exception e) {
			logger.error("批量插入插入报表的文件的保存路径信息表发生异常，原因：", e);
		} finally {
			stopWatch.stop();
			logger.info("批量插入报表的文件的保存路径消耗时间[{}]", stopWatch);
		}
	}
	
	/**
	 *  将报表生成的路径信息记录到数据库中
	 * @param merchantNo 商户或机构号
	 * @param reportDate 报表日期
	 * @param reportType 报表类型
	 * @param excelName 报表名称
	 * @param excelFileName 报表文件目录（包含报表名称）
	 * @throws BizException
	 */
	private void addReportPathSave(String merchantNo, String reportDate, ReportType reportType, 
			String excelName, String excelFileName) throws BizException {
		ReportPathSave pathSave = new ReportPathSave();
		
		pathSave.setMerchantNo(merchantNo);
		pathSave.setReportDate(reportDate);
		pathSave.setReportType(reportType.getValue());
		pathSave.setGenDate(DateUtil.formatDate("yyyyMMdd"));
		pathSave.setReportName(excelName);
		pathSave.setFilePath(excelFileName);
		
		try {
			this.reportPathSaveDAO.insert(pathSave);
		} catch (Exception e) {
			logger.error("插入报表的文件的保存路径信息表发生异常。", e);
			throw new BizException(e);
		}
	}
	
	/**
	 *  插入警告清算系统日志
	 * @param branchNo 机构号
	 * @param merchantNo 商户号
	 * @param msg 日志信息
	 */
	private void insertSysLog(String branchNo, String merchantNo, String msg, SysLogType sysLogType) {
		// 插入清算系统日志
		logService.insertSysLog(branchNo, merchantNo, msg, sysLogType, SysLogClass.INFO);// SysLogClass.INFO 程序错
	}
	
	public Paginater getConsmChargeBalReportList(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		List<BusiReport> list = new ArrayList<BusiReport>();
		String reportFileSavePath = SysparamCache.getInstance().getReportFolderPath(); //生成的报表文件保存路径
		//String reportFileSavePath = "D:/oldReportFile"; 
		
		String reportDate = (String) params.get("reportDate");
		String branchCode = (String) params.get("branchCode");
		String reportType = "consmChargeBalReport";

		List<String> fileList = new LinkedList<String>();
		
		reportFileSavePath = reportFileSavePath + PATH_SEP + reportType;
		
		// 查出符合条件的所有报表文件列表
		fileList = IOUtil.getFileList(fileList, reportFileSavePath);
		
		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		}

		if (StringUtils.isNotBlank(reportDate)
				|| StringUtils.isNotBlank(branchCode)) {
			fileList = getFileListResult(fileList, branchCode, "", reportDate);
		}
		
		list = getBusiReportList(fileList);

		return CommonHelper.getListPage(list, pageNumber, pageSize);
	}

	public boolean downloadConsmChargeBalReportFile(String fullPath)
			throws BizException {
		return false;
	}
	
	protected static List getOrderedList(List<BusiReport> all) {
		List<BusiReport> orderedList = new ArrayList();
		orderedList.addAll(all);
		
		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				String date1 = ((BusiReport) o1).getReportDate();
				String date2 = ((BusiReport) o2).getReportDate();
				if(date1.compareTo(date2) > 0){
					return -1;
				}else if(date1.compareTo(date2) < 0){
					return 1;
				}else{
				    return 0;
				}
			}
		});
		
		return orderedList;
	}

	/** 日报表生成定义接口 */
	private static interface IReportGenerate  {
		
		/**
		 * 生成报表
		 * @param preWorkDate
		 * @param reportSavePath
		 * @param templatePath
		 * @param currDate
		 * @return
		 * @throws BizException
		 */
		void generateReport(String preWorkDate, String reportSavePath, String templatePath, String currDate) throws BizException;
		
		/**
		 * 报表类型描述
		 * @return
		 */
		String getReportDecribe();
	}
}
