package gnete.card.web.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;
import gnete.card.service.ReportResourceService;
import gnete.card.web.BaseAction;


/**
 * 营运代理报表数据来源
 * @author aps-lib
 *
 */
public class TermResourceAction extends BaseAction {
	
	@Autowired
	private ReportResourceService reportResourceService;
	
	private String feeMonth;
	private String agentCode;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginBranchCode", this.getLoginBranchCode());
		params.put("feeMonth", feeMonth);
		params.put("proxyId", agentCode);
		
		ServletContext servletContext = request.getSession().getServletContext();
		File file = WebUtils.getTempDir(servletContext);
		this.reportResourceService.getAgentFeeShareExcel(params, file.getAbsolutePath());
		
		//String msg = LogUtils.r("生成营运代理{0}的[{1}]数据源成功", agent, "运营代理商分润汇总报表");
		
		return null;
	}

	public String getFeeMonth() {
		return feeMonth;
	}

	public void setFeeMonth(String feeMonth) {
		this.feeMonth = feeMonth;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

}
