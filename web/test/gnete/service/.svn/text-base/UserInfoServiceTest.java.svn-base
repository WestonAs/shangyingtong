package gnete.service;

import gnete.BaseTest;
import gnete.card.service.UserService;
import gnete.etc.BizException;


public class UserInfoServiceTest extends BaseTest {
	
	private UserService userService;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userService = (UserService) this.getBean("userService");
	}

	public void testRegister(){
		try {
			this.userService.addUser(null, null);
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
	
}
