package gnete.card.web.report;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.ReportPathSaveService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.util.DateUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 分支机构角色菜单下的报表下载
 */
public class FenzhiDownloadReportAction extends BaseAction {

	@Autowired
	private ReportPathSaveDAO		reportPathSaveDAO;
	@Autowired
	private ReportPathSaveService	reportPathSaveService;

	@Autowired
	private BranchInfoDAO			branchInfoDAO;

	private Paginater				page;

	private ReportPathSave			reportPathSave;

	private List<ReportType>		reportTypeList;
	private List<BranchInfo>		fenzhiList;

	private boolean					showFenzhi	= false;

	/*
	 * 日统计报表列表
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
			params.put("reportDate", reportPathSave.getReportDate());
			params.put("merchantNo", reportPathSave.getMerchantNo());
		}
		if (isCenterOrCenterDeptRoleLogined()) {
			showFenzhi = true;
			this.fenzhiList = sortBranchList(this.branchInfoDAO.findByType(BranchType.FENZHI.getValue()));

		} else if (isFenzhiRoleLogined()) {
			showFenzhi = false;
			params.put("merchantNo", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查看运营分支机构日统计报表");
		}
		// 运营分支机构下看到的是运营分支机构的日报表类型
		params.put("reportType", ReportType.FENZHI_DAILY.getValue());

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

		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()
				&& branchInfoDAO.isSuperBranch(this.getLoginBranchCode(), reportPathSave.getMerchantNo())) {

		} else {
			throw new BizException("没有权限查看指定运营分支机构的日报表");
		}
		Assert.equals(ReportType.FENZHI_DAILY.getValue(), reportPathSave.getReportType(), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(),
				reportPathSave.getMerchantNo(), reportPathSave.getReportDate());

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

		if (reportPathSave == null) { // 为了运营中心查询效率，增加默认月（上个月份）
			reportPathSave = new ReportPathSave();
			Calendar c = new GregorianCalendar();
			c.add(Calendar.MONTH, -1);
			reportPathSave.setReportDate(DateUtil.formatDate("yyyyMM", c.getTime()));
		}

		if (reportPathSave != null) {
			params.put("reportDate", reportPathSave.getReportDate());
			params.put("merchantNo", reportPathSave.getMerchantNo());
		}

		if (isCenterOrCenterDeptRoleLogined()) {
			showFenzhi = true;
			this.fenzhiList = this.branchInfoDAO.findByType(BranchType.FENZHI.getValue());
			this.fenzhiList = this.sortBranchList(fenzhiList);
		} else if (isFenzhiRoleLogined()) {
			showFenzhi = false;
			params.put("merchantNo", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查看运营分支机构月统计报表");
		}
		// 运营分支机构角色看到的是运营分支机构的月报表类型
		params.put("reportType", ReportType.FENZHI_MONTH.getValue());
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
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()
				&& branchInfoDAO.isSuperBranch(this.getLoginBranchCode(), reportPathSave.getMerchantNo())) {

		} else {
			throw new BizException("没有权限查看指定运营分支机构的月报表");
		}
		Assert.equals(ReportType.FENZHI_MONTH.getValue(), reportPathSave.getReportType(), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(),
				reportPathSave.getMerchantNo(), reportPathSave.getReportDate());

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

	public List<BranchInfo> getFenzhiList() {
		return fenzhiList;
	}

	public void setFenzhiList(List<BranchInfo> fenzhiList) {
		this.fenzhiList = fenzhiList;
	}

	public boolean isShowFenzhi() {
		return showFenzhi;
	}

	public void setShowFenzhi(boolean showFenzhi) {
		this.showFenzhi = showFenzhi;
	}

}
