package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.MerchInfoRegDAO;
import gnete.card.entity.MerchInfoReg;


public class MerchInfoRegDAOTest extends BaseTest {
	
	private MerchInfoRegDAO merchInfoRegDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		merchInfoRegDAO = (MerchInfoRegDAO) this.getBean("merchInfoRegDAOImpl");
	}

	public void testInsert() {
		MerchInfoReg merchInfoReg = new MerchInfoReg();
		this.merchInfoRegDAO.insert(merchInfoReg);
		
	}
}
