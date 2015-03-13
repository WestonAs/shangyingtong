package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.PointBalDAO;

import java.util.HashMap;
import java.util.Map;

public class PointBalDaoTest extends BaseTest {

private PointBalDAO pointBalDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		pointBalDAO = (PointBalDAO) this.getBean("pointBalDAOImpl");
	}

	public void testGetList(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acctId", "12345");
		params.put("jinstId", "1213");
		this.pointBalDAO.getPointBalList(params);
	}
	
//	public void testInsert(){
//		System.out.println("sigle Start:" + new Date().getTime());
//		CardStockInfo info = new CardStockInfo();
//		for (int i = 0; i < 10000; i++) {
//			info.setCardId("123");
//			info.setAppId("appId");
//			info.setAppDate(DateUtil.formatDate("yyyyMMdd"));
//			cardStockInfoDAO.insert(info);
//		}
//		System.out.println("sigle End:" + new Date().getTime());
//	}
}
