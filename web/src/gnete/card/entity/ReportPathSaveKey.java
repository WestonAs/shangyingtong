package gnete.card.entity;

import gnete.card.entity.type.report.ReportType;

public class ReportPathSaveKey {
	private String	reportType; // 报表类型

	private String	merchantNo; // 商户或机构号

	private String	reportDate; // 报表统计日期（月份）

	public ReportPathSaveKey(String reportType, String merchantNo, String reportDate) {
		super();
		this.reportType = reportType;
		this.merchantNo = merchantNo;
		this.reportDate = reportDate;
	}

	public ReportPathSaveKey() {
		super();
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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

	/**
	 * 取得报表类型名
	 * 
	 * @return
	 */
	public String getReportTypeName() {
		return ReportType.ALL.get(this.reportType) == null ? "" : ReportType.valueOf(this.reportType)
				.getName();
	}
}