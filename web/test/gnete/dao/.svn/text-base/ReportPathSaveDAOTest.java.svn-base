package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.ReportPathSave;

public class ReportPathSaveDAOTest extends BaseTest {
	
	private ReportPathSaveDAO reportPathSaveDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reportPathSaveDAO = (ReportPathSaveDAO) this.getBean("reportPathSaveDAOImpl");
	}
	
	public void testInsert() {
		ReportPathSave pathSave = new ReportPathSave();
		pathSave.setMerchantNo("00000000");
		pathSave.setReportDate("20110731");
		pathSave.setReportType("dayReport/center");
		pathSave.setGenDate("20110622");
		pathSave.setReportName("dayReport_center_00000000_20110731.xls");
		pathSave.setFilePath("/home/card_dev/reportFile/dayReport/center/20110731/dayReport_center_00000000_20110731.xls");
		
		reportPathSaveDAO.insert(pathSave);
	}

}
