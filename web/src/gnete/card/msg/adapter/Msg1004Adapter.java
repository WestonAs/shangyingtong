package gnete.card.msg.adapter;

import gnete.card.dao.AppRegDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.entity.AppReg;
import gnete.card.entity.state.CardStockState;
import gnete.card.msg.MsgAdapter;
import gnete.card.util.CardUtil;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg1004Adapter.java
 * @description: 领卡报文返回处理适配器
 */
@Repository
public class Msg1004Adapter implements MsgAdapter {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
//	@Autowired
//	private CardInfoDAO cardInfoDAO;

	private static Logger logger = Logger.getLogger(Msg1004Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException{
		logger.debug("进入领卡报文返回处理适配器....");
		AppReg appReg = (AppReg) appRegDAO.findByPk(id);
		String strNo = "";
		int cardNum = 0;
		if (StringUtils.isNotEmpty(appReg.getCheckStrNo())) {
			strNo = appReg.getCheckStrNo();
			cardNum = appReg.getCheckCardNum().intValue();
		} else {
			strNo = appReg.getStrNo();
			cardNum = appReg.getCardNum().intValue();
		}

		// 先将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空（从发卡机构领卡）
		Map<String, Object> params1 = new HashMap<String, Object>();
		
		// 领在库状态的卡时，是向发卡机构领的，要先将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空
		if (StringUtils.equals(appReg.getCardStockStatus(), CardStockState.IN_STOCK.getValue())) {
			params1.put("cardStatus", CardStockState.IN_STOCK.getValue());
			params1.put("appOrgId", "");
			params1.put("appDate", "");
//			params1.put("cardSectorId", info.getCardSectorId());
		} else {// 从售卡代理
			params1.put("cardStatus", CardStockState.RECEIVED.getValue());
		}
		params1.put("strCardId", appReg.getStrNo());
		params1.put("endCardId", CardUtil.getMaxEndCardId(appReg.getStrNo(), appReg.getCardNum()));
		
		if (isSuccess) {
			logger.debug("领卡成功！");
			// 先将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空
			cardStockInfoDAO.updateStockBatch(params1);
			
			//  再更新批准的起始卡号
			Map<String, Object> params2 = new HashMap<String, Object>();
			
			params2.put("cardStatus", CardStockState.RECEIVED.getValue());
			params2.put("appOrgId", appReg.getAppOrgId());
			params2.put("appDate", appReg.getAppDate());
			params2.put("appRegId", appReg.getId());
			params2.put("cardSectorId", appReg.getCardSectorId());
			
			params2.put("strCardId", strNo);
			params2.put("endCardId", CardUtil.getMaxEndCardId(strNo, cardNum));
			
			cardStockInfoDAO.updateStockBatch(params2);
		} else {
			logger.debug("领卡失败！");
			// 将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空
			cardStockInfoDAO.updateStockBatch(params1);
		}
		logger.debug("领卡报文返回处理适配器处理完成....");
	}

//	public void deal(Long id, boolean isSuccess) throws BizException{
//		logger.debug("进入领卡报文返回处理适配器....");
//		AppReg appReg = (AppReg) appRegDAO.findByPk(id);
//		String strNo = "";
//		int cardNum = 0;
//		if (StringUtils.isNotEmpty(appReg.getCheckStrNo())) {
//			strNo = appReg.getCheckStrNo();
//			cardNum = appReg.getCheckCardNum().intValue();
//		} else {
//			strNo = appReg.getStrNo();
//			cardNum = appReg.getCardNum().intValue();
//		}
//		
//		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
//		
//		// 先将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空（从发卡机构领卡）
////		String[] cardNoArray1 = CardUtil.getCard(appReg.getStrNo(), appReg.getCardNum());
////		
////		for (String cardId : cardNoArray1) {
////			CardStockInfo info = cardStockInfoDAO.findCardStockInfoByCardId(cardId);
////			Assert.notNull(info, "卡库存信息表中没有卡号[" + cardId + "]的记录");
////			Map<String, Object> params1 = new HashMap<String, Object>();
////			if (StringUtils.equals(appReg.getCardIssuer(), appReg.getCardSectorId())) {// 从发卡机构领卡
////				params1.put("cardStatus", CardStockState.IN_STOCK.getValue());
////				params1.put("appOrgId", "");
////				params1.put("appDate", "");
////				params1.put("cardSectorId", info.getCardSectorId());
////				params1.put("cardId", cardId);
////				
////			} else { // 从售卡代理
////				params1.put("cardStatus", CardStockState.RECEIVED.getValue());
////				params1.put("appOrgId", info.getAppOrgId());
////				params1.put("appDate", info.getAppDate());
////				params1.put("cardSectorId", info.getCardSectorId());
////				params1.put("cardId", cardId);
////			}
////			list1.add(params1);
////			
////		}
//		
//		List<CardStockInfo> stockInfoList = this.getCardNoList(appReg.getStrNo(), appReg.getCardNum());
//		for (CardStockInfo info : stockInfoList) {
//			Map<String, Object> params1 = new HashMap<String, Object>();
//			// 领在库状态的卡时，是向发卡机构领的，要先将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空
//			if (StringUtils.equals(appReg.getCardStockStatus(), CardStockState.IN_STOCK.getValue())) {
//				params1.put("cardStatus", CardStockState.IN_STOCK.getValue());
//				params1.put("appOrgId", "");
//				params1.put("appDate", "");
//				params1.put("cardSectorId", info.getCardSectorId());
//				params1.put("cardId", info.getCardId());
//			} else { // 从售卡代理
//				params1.put("cardStatus", CardStockState.RECEIVED.getValue());
//				params1.put("appOrgId", info.getAppOrgId());
//				params1.put("appDate", info.getAppDate());
//				params1.put("cardSectorId", info.getCardSectorId());
//				params1.put("cardId", info.getCardId());
//			}
//			list1.add(params1);
//		}
//
//		if (isSuccess) {
//			logger.debug("领卡成功！");
//			// 先将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空
//			cardStockInfoDAO.updateStockBatch(list1);
//			
//			//  再更新批准的起始卡号
////			String[] cardNoArray2 = CardUtil.getCard(strNo, cardNum);
////			List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
////			for (String cardId : cardNoArray2) {
////				Map<String, Object> params2 = new HashMap<String, Object>();
////				params2.put("cardStatus", CardStockState.RECEIVED.getValue());
////				params2.put("appOrgId", appReg.getAppOrgId());
////				params2.put("appDate", appReg.getAppDate());
////				params2.put("cardId", cardId);
////				params2.put("appRegId", appReg.getId());
////				params2.put("cardSectorId", appReg.getCardSectorId());
////				
////				list2.add(params2);
////			}
//			
//			List<CardStockInfo> checkPassList = this.getCardNoList(strNo, cardNum);
//			List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
//			for (CardStockInfo stock : checkPassList) {
//				Map<String, Object> params2 = new HashMap<String, Object>();
//				params2.put("cardStatus", CardStockState.RECEIVED.getValue());
//				params2.put("appOrgId", appReg.getAppOrgId());
//				params2.put("appDate", appReg.getAppDate());
//				params2.put("cardId", stock.getCardId());
//				params2.put("appRegId", appReg.getId());
//				params2.put("cardSectorId", appReg.getCardSectorId());
//				
//				list2.add(params2);
//			}
//			
//			cardStockInfoDAO.updateStockBatch(list2);
//		} else {
//			logger.debug("领卡失败！");
//			// 将申请领出的卡的状态改为在库，领卡机构、领卡日期都置为空
//			cardStockInfoDAO.updateStockBatch(list1);
//		}
//	}
	
//	/**
//	 * 根据起始卡号，卡数量生成卡号数组
//	 * @param strCardId
//	 * @param cardNum
//	 * @return
//	 * @throws BizException
//	 */@Deprecated
//	private List<CardStockInfo> getCardNoList(String strCardId, int cardNum) throws BizException {
//		if (StringUtils.isEmpty(strCardId)) {
//			return Collections.<CardStockInfo> emptyList();
//		}
//		
//		List<CardStockInfo> list = new ArrayList<CardStockInfo>();
//		
//		// 新卡是19位
//		if (strCardId.length() == 19) {
//			String[] cardNoArray = CardUtil.getCard(strCardId, cardNum);
//			for (int i = 0; i < cardNoArray.length; i++) {
//				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNoArray[i]);
//				Assert.notNull(cardInfo, "卡信息表没有第[" + (i + 1) + "]张卡[" + cardNoArray[i] + "]的记录");
//				
//				CardStockInfo info = cardStockInfoDAO.findCardStockInfoByCardId(cardNoArray[i]);
//				Assert.notNull(info, "卡库存中没有第[" + (i + 1) + "]张[" + cardNoArray[i] + "]的记录。");
//				
//				list.add(info);
//			}
//		}
//		// 旧卡是18位
//		else if (strCardId.length() == 18) {
//			String[] cardNoArray = CardUtil.getOldCard(strCardId, cardNum);
//			for (int i = 0; i < cardNoArray.length; i++) {
//				String cardId = cardNoArray[i];
//				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
//				
//				//旧卡的另外一种校验规则
//				if (cardInfo == null) {
//					String cardPrex = cardNoArray[i].substring(0, cardNoArray[i].length() - 1);
//					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
//					
//					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
//				}
//				Assert.notNull(cardInfo, "卡信息表没有第[" + (i + 1) + "]张卡[" + cardId + "]的记录");
//				
//				CardStockInfo info = cardStockInfoDAO.findCardStockInfoByCardId(cardId);
//				Assert.notNull(info, "卡库存中没有第[" + (i + 1) + "]张[" + cardId + "]的记录。");
//				
//				list.add(info);
//			}
//		} else {
//			throw new BizException("卡号的长度有误");
//		}
//		return list;
//	 }
}
