package gnete.card.entity;

import java.util.Date;

public class ReportPathSave extends ReportPathSaveKey {
    
	private String	genDate;	// 报表生成日期

	private String	reportName;	// 报表名称

	private String	filePath;	// 报表文件目录（包含报表名称）

	private Date	insertTime; 

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
    
}