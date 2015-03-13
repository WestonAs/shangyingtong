package gnete.dao;

import org.springframework.beans.factory.annotation.Autowired;

import gnete.BaseTest;
import gnete.card.dao.CardBinRegDAO;

public class CardBinRegTest extends BaseTest {

	@Autowired
	private CardBinRegDAO cardBinRegDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cardBinRegDAO = (CardBinRegDAO) this.getBean("cardBinRegDAOImpl");
	}

	public void testSelect() throws Exception {
		cardBinRegDAO.findByPkWithLock(142L);
	}
}
