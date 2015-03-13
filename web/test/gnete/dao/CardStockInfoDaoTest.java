package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.CardStockInfoDAO;

import java.util.HashMap;
import java.util.Map;

public class CardStockInfoDaoTest extends BaseTest {

private CardStockInfoDAO cardStockInfoDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cardStockInfoDAO = (CardStockInfoDAO) this.getBean("cardStockInfoDAOImpl");
	}
	
	public void testGetCouldReceiveThisTimeFromSell(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSubclass", "YLXFCK");
		params.put("cardStatus", "10");
		params.put("appOrgId", "10006020");
		cardStockInfoDAO.getCouldReceiveThisTimeFromSell(params);
	}

//	public void testInserBatch(){
//		long t1 = System.currentTimeMillis();
//		System.out.println("batch Start:" + t1);
//		List<CardStockInfo> list = new ArrayList<CardStockInfo>();
//		for (long i = 1; i <= 10000; i++) {
//			CardStockInfo info = new CardStockInfo();
//			info.setCardId(StringUtils.leftPad(Long.toString(i), 9, "0"));
//			info.setMakeId("appId123");
//			info.setAppDate(DateUtil.formatDate("yyyyMMdd"));
//			list.add(info);
//		}
//		cardStockInfoDAO.insertBatch(list);
//		System.out.println("batch End:" + System.currentTimeMillis());
//		System.out.println("batch End:" + (System.currentTimeMillis() - t1));
//	}
//	
//	public void testUpdateBatch(){
//		long t1 = new Date().getTime();
//		System.out.println("Update batch Start:" + t1);
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		for (int i = 1; i <= 10000; i++) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("cardStatus", "33");
//			params.put("appOrgId", "999999");
//			params.put("appDate", "20101215");
//			params.put("cardId", StringUtils.leftPad(Long.toString(i), 9, "0"));
//			list.add(params);
//		}
//		cardStockInfoDAO.updateStockBatch(list);
//		System.out.println("Update batch End:" + (new Date().getTime() - t1));
//	}
	
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
