package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.ProcDAO;


public class ProcDAOTest extends BaseTest {
	
	private ProcDAO procDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		procDAO = (ProcDAO) this.getBean("procDAOImpl");
	}

	public void testSpCardDayEnd(){
		this.procDAO.spCardDayEnd("20101003");
	}
}
