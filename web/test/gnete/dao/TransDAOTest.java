package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.TransDAO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class TransDAOTest extends BaseTest {

	@Autowired
	private TransDAO transDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		transDAO = (TransDAO) this.getBean("transDAOImpl");
	}

	public void testSelect() throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("settStartDate", "20100830");
		params.put("settEndDate", "20100830");
		transDAO.findTrans(params, 1, 20);
	}
}
