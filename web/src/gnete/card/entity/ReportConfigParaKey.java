package gnete.card.entity;

import gnete.card.entity.type.IssType;
import gnete.card.entity.type.report.ReportType;

public class ReportConfigParaKey {
    private String insId;

    private String insType;

    private String reportType;

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getInsType() {
        return insType;
    }

    public void setInsType(String insType) {
        this.insType = insType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getInsTypeName() {
    	return IssType.ALL.get(this.insType) == null ? "" : IssType.valueOf(this.insType).getName();
    }

    public String getReportTypeName() {
    	return ReportType.ALL.get(this.reportType) == null ? "" : ReportType.valueOf(this.reportType).getName();
    }
}