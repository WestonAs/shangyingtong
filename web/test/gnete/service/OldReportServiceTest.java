package gnete.service;

import gnete.BaseTest;
import gnete.card.service.OldReportService;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.Map;


public class OldReportServiceTest extends BaseTest {
	
	private OldReportService oldReportService;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		oldReportService = (OldReportService) this.getBean("oldReportService");
	}

	public void testgGnerateFile(){
		try {
			String reportPath = "D:";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("settDate", "20110801");
			params.put("cardIssuer", "10015810");
			this.oldReportService.generateOldActiveCardReport(reportPath, params);
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
	
//	public void testAdd() {
//		try {
//			oldReportService.addOldReportPara(null, null);
//		} catch (BizException e) {
//			e.printStackTrace();
//		}
//	}
}
