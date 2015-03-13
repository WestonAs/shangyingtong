package gnete.card.web.report;

import flink.util.DateUtil;
import flink.util.LogUtils;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ManualReportType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.GenerateReportService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

/**
 * @File: ManualGenerateReportsAction.java
 *
 * @description: 手动生成报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-23
 */
public class ManualGenerateReportsAction extends BaseAction {
	
	@Autowired
	private GenerateReportService generateReportService;
	private static final String MANUAL_GENERATING_REPORT_KEY_PREFIX = "MANUAL_GENERATING_REPORT_KEY_PREFIX_";
	
	private String reportDate; // 要生成的报表时间
	private String reportType;  // 要生成的报表类型，是日报表还是月报表
	private String report;        //要生成的特定报表, (运营中心,发卡机构,商户...)
	
	private String starDate;
	private String endDate;
	
	private List<ManualReportType> reportTypeList;
	private List<ReportType> dayReportTypeList;
	private List<ReportType> monthReportTypeList;

	@Override
	public String execute() throws Exception {
		Assert.isTrue(isCenterRoleLogined(), "只有运营中心能够手动生成报表。");
		
		this.reportTypeList = ManualReportType.getAll();
		this.dayReportTypeList = ReportType.getDayReportType();
		this.monthReportTypeList = ReportType.getMonthReportType();
		
		String preWorkDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		this.setEndDate(preWorkDate);
		Date maxDate = DateUtil.formatDate(preWorkDate, "yyyyMMdd");
		Date minDate = DateUtils.addYears(maxDate, -1);
		this.setStarDate(new SimpleDateFormat("yyyyMMdd").format(minDate));
		return "generate";
	}
	
	/**
	 * 手动生成报表
	 * @throws Exception
	 */
	public String generate() throws Exception {
		Assert.isTrue(isCenterRoleLogined(), "只有运营中心能够手动生成报表。");
		
		if (getApplicationContext().get(buildManualGeneratingReportKey(reportDate, report)) != null) {
			String TipMsg = "后台正在生成报表数据，请耐心等待！ 信息："
					+ getApplicationContext().get(buildManualGeneratingReportKey(reportDate, report))
							.toString();
			logger.info(TipMsg);
			throw new BizException(TipMsg);
		}

		String msg = LogUtils.r("用户[{0}]手动生成[{1}]{2}的任务已经执行，请在30分钟后去报表下载页面下载报表。操作时间 [{3}]", this
				.getSessionUserCode(), reportDate, ReportType.valueOf(report).getName(), DateUtil
				.formatDate("yyyy-MM-dd HH:mm:ss"));
		getApplicationContext().put(buildManualGeneratingReportKey(reportDate, report), msg);
		Runnable reportGenRunnable = new Runnable() {
			Map<String, Object> application = ActionContext.getContext().getApplication();
			@Override
			public void run() {
				try {
					generateReportService.manualGenerateReport(ManualReportType.valueOf(reportType),
							reportDate, report);
				} catch (Exception e) {
					logger.warn("手动生成报表失败", e);
				} finally {
					application.remove(buildManualGeneratingReportKey(reportDate, report));
				}
			}
		};
		new Thread(reportGenRunnable).start();

		this.addActionMessage("/pages/report/showGenerate.do", msg);
		this.log(msg, UserLogType.OTHER);
		return SUCCESS;
	}
	
	public String showGenerateOld() throws Exception {
		Assert.isTrue(isCenterRoleLogined(), "只有运营中心能够手动生成报表。");
		
		String preWorkDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		this.setEndDate(preWorkDate);
		Date maxDate = DateUtil.formatDate(preWorkDate, "yyyyMMdd");
		Date minDate = DateUtils.addYears(maxDate, -1);
		this.setStarDate(new SimpleDateFormat("yyyyMMdd").format(minDate));
		return "generateOld";
	}
	
	/**
	 * 手动生成旧报表
	 * @return
	 * @throws Exception
	 */
	public String generateOld() throws Exception {
		Assert.isTrue(isCenterRoleLogined(), "只有运营中心能够手动生成报表。");
		
		Assert.notEmpty(reportDate, "要生成的报表统计日期不能为空");
		this.generateReportService.generateOldDayReport(reportDate);
		String msg = LogUtils.r("用户[{0}]手动生成[{1}]旧日报表成功", this.getSessionUserCode(), reportDate);
		this.addActionMessage("/pages/report/showGenerateOld.do", msg);
		this.log(msg, UserLogType.OTHER);
		return SUCCESS;
	}

	/** ActionContext.getContext().getApplication() */
	private Map<String, Object> getApplicationContext(){
		return ActionContext.getContext().getApplication();
	}
	
	private String buildManualGeneratingReportKey(String reportDate, String report) {
		return MANUAL_GENERATING_REPORT_KEY_PREFIX + reportDate + report;
	}
	
	// ------------------------------- getter and setter followed------------------------
	
	public List<ManualReportType> getReportTypeList() {
		return reportTypeList;
	}

	public void setReportTypeList(List<ManualReportType> reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

	public List<ReportType> getDayReportTypeList() {
		return dayReportTypeList;
	}

	public void setDayReportTypeList(List<ReportType> dayReportTypeList) {
		this.dayReportTypeList = dayReportTypeList;
	}

	public List<ReportType> getMonthReportTypeList() {
		return monthReportTypeList;
	}

	public void setMonthReportTypeList(List<ReportType> monthReportTypeList) {
		this.monthReportTypeList = monthReportTypeList;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getStarDate() {
		return starDate;
	}

	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
