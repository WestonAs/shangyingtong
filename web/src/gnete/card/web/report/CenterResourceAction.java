package gnete.card.web.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import flink.util.LogUtils;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.CenterOperateType;
import gnete.card.service.ReportResourceService;
import gnete.card.web.BaseAction;

/**
 * 运营中心报表数据来源
 * @author aps-lib
 *
 */
public class CenterResourceAction extends BaseAction {
	
	@Autowired
	private ReportResourceService reportResourceService;
	
	private String reportSearchType;
	private String reportType;
	private String feeMonth;
	private String clusterBranchCode;

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportSearchType", reportSearchType);
		params.put("reportType", reportType);
		params.put("loginBranchCode", this.getLoginBranchCode());
		params.put("feeMonth", feeMonth);
//		params.put("branchCode", fenzhiCode);
		params.put("branchCode", clusterBranchCode);
		
		this.reportResourceService.getCenterOperateFeeExcel(params, this.getTemp().getAbsolutePath());
		
		String msg = LogUtils.r("生成[{1}]数据源成功", CenterOperateType.valueOf(reportType).getName());
		
		String url = "/pages/report/center/operateFee.jsp";
		this.addActionMessage(url, msg);
		this.log(msg, UserLogType.OTHER);
		
		return null;
	}
	
	public String operateFeeMembSrc() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportType", reportType);
		params.put("loginBranchCode", this.getLoginBranchCode());
		params.put("feeMonth", feeMonth);
//		params.put("branchCode", fenzhiCode);
		params.put("branchCode", clusterBranchCode);
		
		this.reportResourceService.getCenterOperateFeeMembTxt(params, this.getTemp().getAbsolutePath());
		return null;
	}

	public String operateFeeDiscntMembSrc() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportType", reportType);
		params.put("loginBranchCode", this.getLoginBranchCode());
		params.put("feeMonth", feeMonth);
//		params.put("branchCode", fenzhiCode);
		params.put("branchCode", clusterBranchCode);
		
		this.reportResourceService.getCenterOperateFeeDiscntMembTxt(params, this.getTemp().getAbsolutePath());
		return null;
	}
	
	// 返回临时文件夹
	private File getTemp(){
		ServletContext servletContext = request.getSession().getServletContext();
		return WebUtils.getTempDir(servletContext);
	}
	
	public String getReportSearchType() {
		return reportSearchType;
	}

	public void setReportSearchType(String reportSearchType) {
		this.reportSearchType = reportSearchType;
	}
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getFeeMonth() {
		return feeMonth;
	}

	public void setFeeMonth(String feeMonth) {
		this.feeMonth = feeMonth;
	}

	public String getClusterBranchCode() {
		return clusterBranchCode;
	}

	public void setClusterBranchCode(String clusterBranchCode) {
		this.clusterBranchCode = clusterBranchCode;
	}

}
