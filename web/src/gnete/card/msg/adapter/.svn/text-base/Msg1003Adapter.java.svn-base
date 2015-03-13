package gnete.card.msg.adapter;

import java.util.HashMap;
import java.util.Map;

import gnete.card.dao.CardInputDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.entity.CardInput;
import gnete.card.entity.state.CardStockState;
import gnete.card.msg.MsgAdapter;
import gnete.card.util.CardUtil;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg1003Adapter.java
 *
 * @description: 成品卡入库报文返回处理适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-20
 */
@Repository
public class Msg1003Adapter implements MsgAdapter {
	
	@Autowired
	private CardInputDAO cardInputDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	
	static Logger logger = Logger.getLogger(Msg1003Adapter.class);
	
	public void deal(Long id, boolean isSuccess) {
		StopWatch stopWatch = new StopWatch();
	
		stopWatch.start();
		logger.debug("成品卡入库报文返回处理适配器开始处理......");
		CardInput cardInput = (CardInput) cardInputDAO.findByPk(id);
		if (isSuccess) {
			logger.debug("成品卡入库成功！");
//			String cardBinNo = cardInput.getStrNo().substring(3, 9);
			// String cardNoSeq = cardInput.getStrNo().substring(11, 19);

//			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardInput.getStrNo());
//			CardSubClassDef subClass = (CardSubClassDef) cardSubClassDefDAO
//					.findByPk(cardInfo.getCardSubclass());

			// 根据起始卡号，卡数量得到要入库的卡号的数组
//			String[] cardNoArray = CardUtil.getCard(cardInput.getStrNo(), cardInput.getInputNum().intValue());
//			
//			// 批量更新要入库的卡的状态为已入库
//			this.cardStockInfoDAO.updateState(cardNoArray, CardStockState.IN_STOCK.getValue());

			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			params.put("strCardId", cardInput.getStrNo());
			params.put("endCardId", CardUtil.getMaxEndCardId(cardInput.getStrNo(), cardInput.getInputNum().intValue()));
			
			this.cardStockInfoDAO.updateStockBatch(params);
			
//			List<CardStockInfo> list = new ArrayList<CardStockInfo>();
//			for (Long i = 0L; i < cardInput.getInputNum().longValue(); i++) {
//				CardStockInfo cardStockInfo = new CardStockInfo();
//				String seqStr = Long.toString(cardInfo.getCardNoSeq() + i);
//				
//				String cardIdPre = cardInfo.getCardNoPrix() + StringUtils.leftPad(seqStr, 7, "0");
//				cardStockInfo.setCardId(cardIdPre + CardUtil.luhnMod10(cardIdPre));
//				cardStockInfo.setCardClass(cardInfo.getCardClass());
//				cardStockInfo.setCardSubclass(cardInfo.getCardSubclass());
//				cardStockInfo.setCardBin(cardBinNo);
//				cardStockInfo.setMakeId(cardInfo.getBatchNo());
//				cardStockInfo.setCardIssuer(subClass.getCardIssuer());// 发卡机构
//				cardStockInfo.setCardStatus(CardStockState.IN_STOCK.getValue());
//				list.add(cardStockInfo);
//			}
//			// 在卡库存信息表里插入该批次所有卡号的记录
//			cardStockInfoDAO.insertBatch(list);
		} else {
			logger.debug("成品卡入库失败！");
		}
		stopWatch.stop();
		
		logger.debug("成品卡入库报文返回处理适配器结束，耗费时间[" + stopWatch + "]");
	}
}
