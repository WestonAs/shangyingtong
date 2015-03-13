package gnete.card.web.report;

import flink.util.Paginater;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.ReportPathSaveService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.util.DateUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: MerchantDownloadReportAction.java
 *
 * @description: 商户菜单下的报表下载
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
public class MerchantDownloadReportAction extends BaseAction {
	
	@Autowired
	private ReportPathSaveDAO reportPathSaveDAO;
	@Autowired
	private ReportPathSaveService reportPathSaveService;
	
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	private Paginater page;
	
	private ReportPathSave reportPathSave;
	
	private List<ReportType> reportTypeList;
	private List<MerchInfo> merchList;
	
	private String merchName;

	/* 
	 * 日统计报表列表
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave == null) {
			reportPathSave = new ReportPathSave();
			reportPathSave.setReportDate(SysparamCache.getInstance().getPreWorkDateNotFromCache());
		}
		params.put("reportDate", reportPathSave.getReportDate());
		params.put("merchantNo", reportPathSave.getMerchantNo());
		
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心或部门登录

		} else if (isFenzhiRoleLogined()) { // 分支机构登录时，可以看到自己管理的商户的报表
			this.merchList = this.merchInfoDAO.findByManage(this.getLoginBranchCode());
			if (CollectionUtils.isEmpty(merchList)) {
				merchList.add(new MerchInfo());
			}
			params.put("merchList", sortMerchList(merchList));

		} else if (isMerchantRoleLogined()) {// 商户登录时，可以看到自己的报表
			params.put("merchantNo", this.getLoginBranchCode());

		} else {
			throw new BizException("没有权限查看商户日统计报表");
		}
		
		// 商户菜单下看到的是商户的日报表类型
		params.put("reportTypes", ReportType.getMerchDayReport());
		this.page = this.reportPathSaveDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "dayList";
	}
	
	/**
	 * 下载日统计报表
	 * @return
	 * @throws Exception
	 */
	public void dayReportDownload() throws Exception {
		if (isCenterOrCenterDeptRoleLogined()) { 

		} else if (isFenzhiRoleLogined() && this.merchInfoDAO.isDirectManagedBy(reportPathSave.getMerchantNo(), this.getLoginBranchCode())) { 

		} else if (isMerchantRoleLogined() && this.getLoginBranchCode().equals(reportPathSave.getMerchantNo())) {

		} else {
			throw new BizException("没有权限查看指定商户的日报表");
		}
		Assert.isTrue(ReportType.getMerchDayReport().contains(ReportType.valueOf(reportPathSave.getReportType())), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(), reportPathSave.getMerchantNo(), reportPathSave.getReportDate());

		String msg = String.format("用户[%s]下载报表文件[%s][%s][%s]成功！", this.getSessionUserCode(),
				reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		this.log(msg, UserLogType.OTHER);
	}
	
	/**
	 * 运营中心的月报表文件列表
	 * @return
	 * @throws Exception
	 */
	public String monthList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if(reportPathSave == null){
			reportPathSave = new ReportPathSave();
			Calendar c = new GregorianCalendar();
			c.add(Calendar.MONTH, -1);
			reportPathSave.setReportDate(DateUtil.formatDate("yyyyMM", c.getTime()));
		}
		params.put("reportDate", reportPathSave.getReportDate());
		params.put("merchantNo", reportPathSave.getMerchantNo());
		
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心登录，可以看到所有的商户的报表

		} else if (isFenzhiRoleLogined()) {// 分支机构登录时，可以看到自己管理的商户的报表
			this.merchList = this.merchInfoDAO.findByManage(this.getLoginBranchCode());
			if (CollectionUtils.isEmpty(merchList)) {
				merchList.add(new MerchInfo());
			}
			params.put("merchList", sortMerchList(merchList));

		} else if (isMerchantRoleLogined()) {// 商户登录时，可以看到自己的报表
			params.put("merchantNo", this.getLoginBranchCode());

		} else {
			throw new BizException("没有权限查看商户月统计报表");
		}
		
		// 商户菜单下看到的是商户的月报表类型
		params.put("reportType", ReportType.MERCH_MONTH.getValue());
		this.page = this.reportPathSaveDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "monthList";
	}
	
	/**
	 * 下载月报表文件
	 * @return
	 * @throws Exception
	 */
	public void monthReportDownload() throws Exception {
		if (isCenterOrCenterDeptRoleLogined()) { 

		} else if (isFenzhiRoleLogined() && this.merchInfoDAO.isDirectManagedBy(reportPathSave.getMerchantNo(), this.getLoginBranchCode())) { 

		} else if (isMerchantRoleLogined() && this.getLoginBranchCode().equals(reportPathSave.getMerchantNo())) {

		} else {
			throw new BizException("没有权限查看指定商户的月报表");
		}
		Assert.equals(ReportType.MERCH_MONTH.getValue(), reportPathSave.getReportType(), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(), reportPathSave.getMerchantNo(), reportPathSave.getReportDate());

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

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

}
