package gnete.dao;

import gnete.BaseTest;
import gnete.card.dao.TransDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

public class TransReoprtTest extends BaseTest {

	private TransDAO transDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		transDAO = (TransDAO) this.getBean("transDAOImpl");
	}

//	public void testSelect() throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("settDate", "20130411");
//		params.put("cardIssuer", "10015810");
//		List<Map<String, Object>> rs = transDAO.findExpireCardTransReport(params);
////		List<Map<String, Object>> rs = transDAO.findExpireTransTermrlSumReport(params);
//		if (CollectionUtils.isNotEmpty(rs)) {
//			System.out.println("进入");
//			for (Map<String, Object> map : rs) {
//				System.out.print("结果：");
//				System.out.print("[" + map.get("merNo") + "]");
//				System.out.print("[" + map.get("merchName") + "]");
//				System.out.print("[" + map.get("settDate") + "]");
//				System.out.print("[" + map.get("per") + "]");
//				System.out.print("[" + map.get("cardId") + "]");
//				System.out.print("[" + map.get("transAmt") + "]");
//				System.out.print("[" + map.get("rcvTime") + "]");
//				System.out.print("[" + map.get("termlId") + "]");
//				System.out.print("[" + map.get("sn") + "]");
//				System.out.print("[" + map.get("retrivlRefNum") + "]");
//				System.out.print("[" + map.get("respCode") + "]");
//				System.out.print("[" + map.get("flag") + "]");
//				System.out.print("[" + map.get("oldTransSn") + "]");
//				System.out.print("[" + map.get("additonInfo") + "]");
//				System.out.print("[" + map.get("expirDate") + "]");
//				System.out.print("[" + map.get("cardIssuer") + "]");
//				System.out.println();
//			}
//		}
//	}
//	
//	public void testFindExpireTransTermrlSumReport() throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("settDate", "20130411");
//		params.put("cardIssuer", "10015810");
//		List<Map<String, Object>> rs = transDAO.findExpireTransTermrlSumReport(params);
//		if (CollectionUtils.isNotEmpty(rs)) {
//			System.out.println("进入");
//			for (Map<String, Object> map : rs) {
//				System.out.print("结果：");
//				System.out.print("[" + map.get("merNo") + "]");
//				System.out.print("[" + map.get("merchName") + "]");
//				System.out.print("[" + map.get("settDate") + "]");
//				System.out.print("[" + map.get("termlId") + "]");
//				System.out.print("[" + map.get("transCnt") + "]");
//				System.out.print("[" + map.get("transAmt") + "]");
//				System.out.print("[" + map.get("refundCnt") + "]");
//				System.out.print("[" + map.get("refundAmt") + "]");
//				System.out.print("[" + map.get("depositCnt") + "]");
//				System.out.print("[" + map.get("depositAmt") + "]");
//				System.out.print("[" + map.get("cardIssuer") + "]");
//				System.out.println();
//			}
//		}
//	}

//	public void testFindExpireTransSumReport() throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("settDate", "20130411");
//		params.put("cardIssuer", "10015810");
//		List<Map<String, Object>> rs = transDAO.findExpireTransSumReport(params);
//		if (CollectionUtils.isNotEmpty(rs)) {
//			System.out.println("进入");
//			for (Map<String, Object> map : rs) {
//				System.out.print("结果：");
//				System.out.print("[" + map.get("merNo") + "]");
//				System.out.print("[" + map.get("merchName") + "]");
//				System.out.print("[" + map.get("settDate") + "]");
//				System.out.print("[" + map.get("transCnt") + "]");
//				System.out.print("[" + map.get("transAmt") + "]");
//				System.out.print("[" + map.get("refundCnt") + "]");
//				System.out.print("[" + map.get("refundAmt") + "]");
//				System.out.print("[" + map.get("depositCnt") + "]");
//				System.out.print("[" + map.get("depositAmt") + "]");
//				System.out.print("[" + map.get("cardIssuer") + "]");
//				System.out.println();
//			}
//		}
//	}
//	public void testFindMerchDetailReport() throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("settDate", "20130411");
//		params.put("cardIssuer", "10015810");
//		List<Map<String, Object>> rs = transDAO.findMerchDetailReport(params);
//		if (CollectionUtils.isNotEmpty(rs)) {
//			System.out.println("进入");
//			for (Map<String, Object> map : rs) {
//				System.out.print("结果：");
//				System.out.print("[" + map.get("merNo") + "]");
//				System.out.print("[" + map.get("merchName") + "]");
//				System.out.print("[" + map.get("settDate") + "]");
//				System.out.print("[" + map.get("per") + "]");
//				System.out.print("[" + map.get("cardId") + "]");
//				System.out.print("[" + map.get("transAmt") + "]");
//				System.out.print("[" + map.get("rcvTime") + "]");
//				System.out.print("[" + map.get("termlId") + "]");
//				System.out.print("[" + map.get("sn") + "]");
//				System.out.print("[" + map.get("retrivlRefNum") + "]");
//				System.out.print("[" + map.get("respCode") + "]");
//				System.out.print("[" + map.get("flag") + "]");
//				System.out.print("[" + map.get("oldTransSn") + "]");
//				System.out.print("[" + map.get("additonInfo") + "]");
//				System.out.print("[" + map.get("cardBin") + "]");
//				System.out.print("[" + map.get("cardIssuer") + "]");
//				System.out.println();
//			}
//		}
//	}
	
//	public void testFindTerminalSubTotalReport() throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("settDate", "20130411");
//		params.put("cardIssuer", "10015810");
//		List<Map<String, Object>> rs = transDAO.findTerminalSubTotalReport(params);
//		if (CollectionUtils.isNotEmpty(rs)) {
//			System.out.println("进入");
//			for (Map<String, Object> map : rs) {
//				System.out.print("结果：");
//				System.out.print("[" + map.get("merNo") + "]");
//				System.out.print("[" + map.get("merchName") + "]");
//				System.out.print("[" + map.get("settDate") + "]");
//				System.out.print("[" + map.get("termlId") + "]");
//				System.out.print("[" + map.get("transCnt") + "]");
//				System.out.print("[" + map.get("transAmt") + "]");
//				System.out.print("[" + map.get("revcCnt") + "]");
//				System.out.print("[" + map.get("revcAmt") + "]");
//				System.out.print("[" + map.get("chgCnt") + "]");
//				System.out.print("[" + map.get("chgAmt") + "]");
//				System.out.println();
//			}
//		}
//	}

//	public void testFindMerchDetailSumReport() throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("settDate", "20130411");
//		params.put("cardIssuer", "10015810");
//		List<Map<String, Object>> rs = transDAO.findMerchDetailSumReport(params);
//		if (CollectionUtils.isNotEmpty(rs)) {
//			System.out.println("进入");
//			for (Map<String, Object> map : rs) {
//				System.out.print("结果：");
//				System.out.print("[" + map.get("merNo") + "]");
//				System.out.print("[" + map.get("merchName") + "]");
//				System.out.print("[" + map.get("settDate") + "]");
//				System.out.print("[" + map.get("transCnt") + "]");
//				System.out.print("[" + map.get("transAmt") + "]");
//				System.out.print("[" + map.get("revcCnt") + "]");
//				System.out.print("[" + map.get("revcAmt") + "]");
//				System.out.print("[" + map.get("chgCnt") + "]");
//				System.out.print("[" + map.get("chgAmt") + "]");
//				System.out.println();
//			}
//		}
//	}

	public void testFindActiveCardReport() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("settDate", "20110801");
		params.put("cardIssuer", "10015810");
		List<Map<String, Object>> rs = transDAO.findActiveCardReport(params);
		if (CollectionUtils.isNotEmpty(rs)) {
			System.out.println("进入");
			for (Map<String, Object> map : rs) {
				System.out.print("结果：");
				System.out.print("[" + map.get("funNo") + "]");
				System.out.print("[" + map.get("cardType") + "]");
				System.out.print("[" + map.get("minCardId") + "]");
				System.out.print("[" + map.get("maxCardId") + "]");
				System.out.print("[" + map.get("cnt") + "]");
				System.out.print("[" + map.get("amt") + "]");
				System.out.print("[" + map.get("settDate") + "]");
				System.out.print("[" + map.get("updateUser") + "]");
				System.out.print("[" + map.get("cardBin") + "]");
				System.out.println();
			}
		}
	}
}
