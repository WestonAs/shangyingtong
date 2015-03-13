package gnete.card.entity;

import gnete.card.entity.type.ReportType;

/**
 * @File: BusiReport.java
 *
 * @description: 报表文件类
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-14
 */
public class BusiReport {
	
	
	private String reportName; //报表名
	private String reportType;// 报表类型
	private String format;
	private String reportDate; //报表统计日期（月份）
	private String genDate;
	private String filePath; //报表文件目录（包含报表名称的）
	private String merchantName;// 商户、机构名称
	private String merchantNo;// 商户、机构号
	private String size;
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getGenDate() {
		return genDate;
	}
	public void setGenDate(String genDate) {
		this.genDate = genDate;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getReportTypeName() {
		return ReportType.ALL.get(reportType) == null ? "" : ReportType.valueOf(reportType).getName();
	}
	
	public BusiReport(String reportName, String reportType, String format,
			String reportDate, String genDate, String filePath) {
		super();
		this.reportName = reportName;
		this.reportType = reportType;
		this.format = format;
		this.reportDate = reportDate;
		this.genDate = genDate;
		this.filePath = filePath;
	}
	public BusiReport(){
		
	}

}
