package gnete.card.web.report;

import flink.util.Paginater;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.ReportPathSaveService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CenterDownloadReportAction.java
 * 
 * @description: 运营中心角色菜单下的报表下载
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-20
 */
public class CenterDownloadReportAction extends BaseAction {

	@Autowired
	private ReportPathSaveDAO		reportPathSaveDAO;
	@Autowired
	private ReportPathSaveService	reportPathSaveService;

	private Paginater				page;

	private ReportPathSave			reportPathSave;

	private List<ReportType>		reportTypeList;

	/*
	 * 日统计报表列表
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
			params.put("reportDate", reportPathSave.getReportDate());
		}
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限查看运营中心日统计报表");
		}
		// 中心菜单下看到的是中心的日报表类型
		params.put("reportType", ReportType.CENTER_DAILY.getValue());
		this.page = this.reportPathSaveDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "dayList";
	}

	/**
	 * 下载日统计报表
	 * 
	 * @return
	 * @throws Exception
	 */
	public void dayReportDownload() throws Exception {
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限查看运营中心日统计报表");
		}
		Assert.equals(ReportType.CENTER_DAILY.getValue(), reportPathSave.getReportType(), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		
		String msg = String.format("用户[%s]下载报表文件[%s][%s][%s]成功！", this.getSessionUserCode(),
				reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		this.log(msg, UserLogType.OTHER);
	}

	/**
	 * 运营中心的月报表文件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String monthList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
			params.put("reportDate", reportPathSave.getReportDate());
		}
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限查看运营中心月统计报表");
		}
		// 中心菜单下看到的是中心的日报表类型
		params.put("reportType", ReportType.CENTER_MONTH.getValue());
		this.page = this.reportPathSaveDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "monthList";
	}

	/**
	 * 下载月报表文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public void monthReportDownload() throws Exception {
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限查看运营中心日统计报表");
		}
		Assert.equals(ReportType.CENTER_MONTH.getValue(), reportPathSave.getReportType(), "报表类型错误");
		
		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		
		String msg = String.format("用户[%s]下载报表文件[%s][%s][%s]成功！", this.getSessionUserCode(),
				reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		this.log(msg, UserLogType.OTHER);
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public ReportPathSave getReportPathSave() {
		return reportPathSave;
	}

	public void setReportPathSave(ReportPathSave reportPathSave) {
		this.reportPathSave = reportPathSave;
	}

	public List<ReportType> getReportTypeList() {
		return reportTypeList;
	}

	public void setReportTypeList(List<ReportType> reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

}
