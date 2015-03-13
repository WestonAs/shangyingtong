package gnete.dao;

import java.util.HashMap;
import java.util.Map;

import gnete.BaseTest;
import gnete.card.dao.MerchTransDSetDAO;

/**
 * @File: TestMerchTransDSetDAO.java
 *
 * @description: MerchTransDSetDAO单元测试
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class TestMerchTransDSetDAO extends BaseTest {

	private MerchTransDSetDAO merchTransDSetDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		merchTransDSetDAO = (MerchTransDSetDAO) this.getBean("merchTransDSetDAOImpl");
	}

	public void testFindByPk(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("recvCode", "208000000000118");
		params.put("payCode", "10015510");
		params.put("setDate", "20101104");
		params.put("transType", "1");
		params.put("curCode", "CNY");
		this.merchTransDSetDAO.findByPk(params);
	}
	
	public void testFindCardSale() {
		Map<String, Object> params = new HashMap<String, Object>();
		this.merchTransDSetDAO.findCardSale(params, 0, 20);
	}
	
	public void testFindMerchTrans() {
		Map<String, Object> params = new HashMap<String, Object>();
		this.merchTransDSetDAO.findMerchTrans(params, 0, 20);
	}
	
	public void testFindMerchTransDSet() {
		Map<String, Object> params = new HashMap<String, Object>();
		this.merchTransDSetDAO.findMerchTransDSet(params, 0, 20);
	}
	
	public void testFindByPkWithName(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("recvCode", "208000000000118");
		params.put("payCode", "10015510");
		params.put("setDate", "20101104");
		params.put("transType", "1");
		params.put("curCode", "CNY");
		this.merchTransDSetDAO.findByPkWithName(params);
	}
	
	public void testGetAmounTotal() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payName", "%%");
		params.put("transType", "1");
		this.merchTransDSetDAO.getAmounTotal(params);
	}
}
