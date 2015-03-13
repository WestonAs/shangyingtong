package gnete.card.web.report;

import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.OldReportType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.GenerateReportService;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 发卡机构角色菜单下的报表下载、旧报表下载、累积消费充值月报表下载
 */
public class CardDownloadReportAction extends BaseAction {
	@Autowired
	private ReportPathSaveDAO reportPathSaveDAO;
	@Autowired
	private ReportPathSaveService reportPathSaveService;
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private GenerateReportService generateReportService;
	
	private Paginater page;
	
	private ReportPathSave reportPathSave;
	
	private List<ReportType> reportTypeList;
	private List<OldReportType> oldReportTypeList;

	private List<BranchInfo> cardBranchList;
	
	private String branchName;
	private String startDate;
	private String endDate;

	/* 
	 * 日统计报表列表
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if(reportPathSave == null){
			reportPathSave = new ReportPathSave();
			reportPathSave.setReportDate(SysparamCache.getInstance().getPreWorkDateNotFromCache());//前一工作日
		}

		params.put("reportDate", reportPathSave.getReportDate());
		params.put("merchantNo", reportPathSave.getMerchantNo());
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), this
					.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
		} else if (StringUtils.equals(this.getLoginRoleTypeCode(), RoleType.GROUP.getValue())) {
			cardBranchList = this.branchInfoDAO.findByGroupId(this.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", sortBranchList(cardBranchList));
		} else if (isCardOrCardDeptRoleLogined()) {
			if (reportPathSave == null || reportPathSave.getMerchantNo() == null
					|| reportPathSave.getMerchantNo().trim().equals("")) {
				reportPathSave = reportPathSave == null ? new ReportPathSave() : reportPathSave;
				reportPathSave.setMerchantNo(this.getLoginBranchCode());
				params.put("merchantNo", reportPathSave.getMerchantNo());
			}
			cardBranchList = this.branchInfoDAO.findChildrenList(this.getLoginBranchCode());
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看发卡机构日统计报表");
		}
		// 发卡机构菜单下看到的是发卡机构的日报表类型
		params.put("reportTypes", ReportType.getCardDayReport());
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

		} else if (isFenzhiRoleLogined()
				&& branchInfoDAO.isDirectManagedBy(reportPathSave.getMerchantNo(), this.getLoginBranchCode())) {

		} else if (isCardOrCardDeptRoleLogined()
				&& branchInfoDAO.isSuperBranch(this.getLoginBranchCode(), reportPathSave.getMerchantNo())) {

		} else {
			throw new BizException("没有权限查看指定发卡机构的日报表");
		}
		Assert.isTrue(ReportType.getCardDayReport().contains(ReportType.valueOf(reportPathSave.getReportType())), "报表类型错误");

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
		
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {// 分支机构角色登录时，可查看自己管理的发卡机构的报表
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), this
					.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", sortBranchList(cardBranchList));
			
		} else if (StringUtils.equals(this.getLoginRoleTypeCode(), RoleType.GROUP.getValue())) { // 集团机构角色登录时，可查看自己集团下的发卡机构的报表
			cardBranchList = this.branchInfoDAO.findByGroupId(this.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
			
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构登录时，只能查看自己的报表
			if (reportPathSave == null || reportPathSave.getMerchantNo() == null
					|| reportPathSave.getMerchantNo().trim().equals("")) {
				reportPathSave = reportPathSave == null ? new ReportPathSave() : reportPathSave;
				reportPathSave.setMerchantNo(this.getLoginBranchCode());
				params.put("merchantNo", reportPathSave.getMerchantNo());
			}
			cardBranchList = this.branchInfoDAO.findChildrenList(this.getLoginBranchCode());
			params.put("cardBranchList", cardBranchList);
			
		} else {// 否则，则没有权限访问
			throw new BizException("没有权限查看发卡机构月统计报表");
		}
		// 发卡机构菜单下看到的是发卡机构的月报表类型
		params.put("reportType", ReportType.CARD_MONTH.getValue());
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

		} else if (isFenzhiRoleLogined()
				&& branchInfoDAO.isDirectManagedBy(reportPathSave.getMerchantNo(), this.getLoginBranchCode())) {

		} else if (isCardOrCardDeptRoleLogined()
				&& branchInfoDAO.isSuperBranch(this.getLoginBranchCode(), reportPathSave.getMerchantNo())) {

		} else {
			throw new BizException("没有权限查看指定发卡机构的月报表");
		}
		Assert.equals(ReportType.CARD_MONTH.getValue(), reportPathSave.getReportType(), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(), reportPathSave.getMerchantNo(), reportPathSave.getReportDate());

		String msg = String.format("用户[%s]下载报表文件[%s][%s][%s]成功！", this.getSessionUserCode(),
				reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		this.log(msg, UserLogType.OTHER);
	}

	/**
	 * 发卡机构的txt旧报表文件列表
	 * @return
	 * @throws Exception
	 */
	public String txtOldReportList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
//			params.put("reportDate", reportPathSave.getReportDate());
			params.put("branchCode", reportPathSave.getMerchantNo());
			params.put("reportType", reportPathSave.getReportType());
		}
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		// 中心角色登录时，可查看所有发卡机构的报表
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {// 分支机构角色登录时，可查看自己管理的发卡机构的报表
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), this
					.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
			
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构登录时，只能查看自己的报表
			params.put("branchCode", this.getLoginBranchCode());
		} else if (isMerchantRoleLogined()) { // 商户登录时，只能查看自己的报表
			params.put("branchCode", getSessionUser().getMerchantNo());
			
		} else {
			throw new BizException("没有权限查看txt旧报表");
		}
		oldReportTypeList = OldReportType.getAll();
		this.page = this.generateReportService.getOldReportTxtList(params, this.getPageNumber(), this.getPageSize());
		
		return "txtOldReportList";
	}

	/**
	 * 从FTP服务器上下载txt旧报表文件
	 * @return
	 * @throws Exception
	 */
	public String txtOldReportDownload() throws Exception {
		String merchantNo = this.getFormMapValue("merchantNo");
		String reportDate = this.getFormMapValue("reportDate");
		String reportType = this.getFormMapValue("reportType");
		String folderPath = SysparamCache.getInstance().getOldTxtReportSavePath(); 
		//ftp文件路径，如：/home/carddev/jfk/oldreport/10015810/20140805/10015810merchDetail20140805.txt
		String path = String.format("%s%s/%s/%s%s%s.txt", folderPath, merchantNo, reportDate, merchantNo,
				reportType, reportDate);
		boolean flag = this.generateReportService.downloadTxtOldReportFile(path);
		
		if (flag) {
			String name = path.substring(path.lastIndexOf("/") + 1);
			String msg = "用户[" + this.getSessionUserCode() + "]下载旧报表txt文件[" + name + "]成功！";
			this.log(msg, UserLogType.OTHER);
		}
		
		return null;
	}
	
	/**
	 * 发卡机构的xls旧报表文件列表
	 * @return
	 * @throws Exception
	 */
	public String xlsOldReportList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
//			params.put("reportDate", reportPathSave.getReportDate());
			params.put("branchCode", reportPathSave.getMerchantNo());
			params.put("reportType", reportPathSave.getReportType());
		}
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), this
					.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
		} else if (isCardOrCardDeptRoleLogined()) {
			params.put("branchCode", this.getLoginBranchCode());

		} else if (isMerchantRoleLogined()) {
			params.put("branchCode", getSessionUser().getMerchantNo());

		} else {
			throw new BizException("没有权限查看xls旧报表");
		}
		oldReportTypeList = OldReportType.getAll();
		this.page = this.generateReportService.getOldReportXlsList(params, this.getPageNumber(), this.getPageSize());
		
		return "xlsOldReportList";
	}
	
	/**
	 * 从web服务器上下载xls旧报表文件
	 * @return
	 * @throws Exception
	 */
	public String xlsOldReportDownload() throws Exception {
		String merchantNo = this.getFormMapValue("merchantNo");
		String reportDate = this.getFormMapValue("reportDate");
		String reportType = this.getFormMapValue("reportType");
		String folderPath = SysparamCache.getInstance().getOldReportSavePath();
		//本地文件路径，如：/data/shangYingTong/oldReportFile/activeCard/20140703/12855810activeCard20140703.xlsx
		String path = String.format("%s/%s/%s/%s%s%s.xlsx", folderPath, reportType, reportDate, merchantNo,
				reportType, reportDate);
		Assert.isTrue(IOUtil.isFileExist(path), "要下载的xls旧报表文件不存在");
		IOUtil.downloadFile(path);
		path = path.replace("\\", "/");
		String name = path.substring(path.lastIndexOf("/") + 1);
		String msg = "用户[" + this.getSessionUserCode() + "]下载xls旧报表文件[" + name + "]成功！";
		this.log(msg, UserLogType.OTHER);
		return null;
	}
	
	/**
	 * 发卡机构“累积消费充值余额报表”文件列表
	 * @return
	 * @throws Exception
	 */
	public String consmChargeBalReportList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (reportPathSave != null) {
			params.put("reportDate", reportPathSave.getReportDate());
			params.put("branchCode", reportPathSave.getMerchantNo());
		}

		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), this
					.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
			
		} else if (isCardOrCardDeptRoleLogined()) {
			
			params.put("branchCode", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查看“累积消费充值余额报表”.");
		}
		
		this.page = this.generateReportService.getConsmChargeBalReportList(params, this.getPageNumber(), this.getPageSize());
		return "consmChargeBalReportList";
	}

	/**
	 * 从web服务器上下载“累积消费充值余额报表”文件列表
	 * @return
	 * @throws Exception
	 */
	public String consmChargeBalReportDownload() throws Exception {
		String merchantNo = this.getFormMapValue("merchantNo");
		String reportDate = this.getFormMapValue("reportDate");
		String folderPath = SysparamCache.getInstance().getReportFolderPath();
		//文件路径，如：/data/shangYingTong/reportFile/consmChargeBalReport/10036410_20130905.txt
		String path = String.format("%s/consmChargeBalReport/%s_%s.txt", folderPath, merchantNo, reportDate);

		Assert.isTrue(IOUtil.isFileExist(path), "要下载的报表文件不存在!");
		IOUtil.downloadFile(path);
		path = path.replace("\\", "/");
		String name = path.substring(path.lastIndexOf("/") + 1);
		String msg = "用户[" + this.getSessionUserCode() + "]下载报表文件[" + name + "]成功！";
		this.log(msg, UserLogType.OTHER);
		return null;
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
	
	
	public List<OldReportType> getOldReportTypeList() {
		return oldReportTypeList;
	}

	public void setOldReportTypeList(List<OldReportType> oldReportTypeList) {
		this.oldReportTypeList = oldReportTypeList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
