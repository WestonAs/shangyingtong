package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.BranchInfoDAO;


public class BranchInfoDAOTest extends BaseTest {
	
	private BranchInfoDAO branchInfoDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		branchInfoDAO = (BranchInfoDAO) this.getBean("branchInfoDAOImpl");
	}

	public void testProxyByProxy(){
		this.branchInfoDAO.findProxyByProxy("10005840");
	}
}
