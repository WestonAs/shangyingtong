package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.UserInfoDAO;


public class UserInfoDAOTest extends BaseTest {
	
	private UserInfoDAO userInfoDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userInfoDAO = (UserInfoDAO) this.getBean("userInfoDAOImpl");
	}

	public void testFindByPk(){
		this.userInfoDAO.findByPk("henry");
	}
}
