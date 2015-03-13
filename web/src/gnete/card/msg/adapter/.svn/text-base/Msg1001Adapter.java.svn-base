package gnete.card.msg.adapter;

import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CostRecordDAO;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CostRecord;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.card.service.MakeCardService;
import gnete.card.util.CardUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg1001Adapter.java
 * 
 * @description: 卡资料建档报文后台返回处理适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-20
 */
@Repository
public class Msg1001Adapter implements MsgAdapter {

	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private MakeCardService makeCardService;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CostRecordDAO costRecordDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;

	static Logger logger = Logger.getLogger(Msg1001Adapter.class);

	public void deal(Long id, boolean isSuccess){
		try {
			MakeCardApp app = (MakeCardApp) this.makeCardAppDAO.findByPk(id);
			// 处理成功
			if (isSuccess) {
				logger.debug("卡建档处理成功，将制卡申请簿中的状态改为建档成功。在卡库存信息表里加记录。");
				app.setStatus(MakeCardAppState.CREATE_SUCCESS.getValue());
				
				CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(app.getStrNo());
				Assert.notNull(cardInfo, "卡信息表中没有卡号[" + app.getStrNo() + "]的记录");
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("branchCode", cardInfo.getCardIssuer());
				params.put("makeId", app.getAppId());
				List<CostRecord> recordList = this.costRecordDAO.findList(params);
				boolean isSingleProduct = false;
				if (CollectionUtils.isNotEmpty(recordList)) {
					isSingleProduct = true;
				}
				
				if (isSingleProduct) {
					logger.debug("单机产品制卡[" + id + "]");
				} else {
					logger.debug("普通制卡[" + id + "]");
				}
				
				// 有bug，现去掉。
				/*CardStockInfo cardStockInfo = null;
				List<CardStockInfo> list = null;
				for (int j = 0; j < (app.getCardNum().longValue() / 500 + 1); j++) {
					
					list = new ArrayList<CardStockInfo>();
					for (Long i = 0L; i < 500 && i < (app.getCardNum().longValue() - j * 500); i++) {
						cardStockInfo = new CardStockInfo();
						String seqStr = Long.toString(cardInfo.getCardNoSeq() + (j * 500 + i));
						
						String cardIdPre = cardInfo.getCardNoPrix() + StringUtils.leftPad(seqStr, 7, "0");
						cardStockInfo.setCardId(cardIdPre + CardUtil.luhnMod10(cardIdPre));
						cardStockInfo.setCardClass(cardInfo.getCardClass());
						cardStockInfo.setCardSubclass(app.getCardSubtype());
						cardStockInfo.setCardBin(app.getBinNo());
						cardStockInfo.setMakeId(cardInfo.getBatchNo());
						cardStockInfo.setCardIssuer(app.getBranchCode());// 发卡机构
						
						//如果是单机产品制卡，则进行以下操作
						if (isSingleProduct) {
							cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
							cardStockInfo.setAppOrgId(app.getBranchCode());
							cardStockInfo.setCardSectorId(app.getBranchCode());
							cardStockInfo.setAppDate(DateUtil.formatDate("yyyyMMdd"));
						} else {
							cardStockInfo.setCardStatus(CardStockState.UN_STOCK.getValue());
						}
						list.add(cardStockInfo);
					}
					// 在卡库存信息表里插入该批次所有卡号的记录
					logger.debug("在卡库存信息表中第[" + (j + 1) + "]次插入记录，插入记录数为[" + list.size() + "]");
					cardStockInfoDAO.insertBatch(list);
				}*/
				
				List<CardStockInfo> list = new ArrayList<CardStockInfo>();
				for (Long i = 0L; i < app.getCardNum().longValue(); i++) {
					CardStockInfo cardStockInfo = new CardStockInfo();
					String seqStr = Long.toString(cardInfo.getCardNoSeq() + i);
					
					String cardIdPre = cardInfo.getCardNoPrix() + StringUtils.leftPad(seqStr, 7, "0");
					cardStockInfo.setCardId(cardIdPre + CardUtil.luhnMod10(cardIdPre));
					cardStockInfo.setCardClass(cardInfo.getCardClass());
					cardStockInfo.setCardSubclass(app.getCardSubtype());
					cardStockInfo.setCardBin(app.getBinNo());
					cardStockInfo.setMakeId(cardInfo.getBatchNo());
					cardStockInfo.setCardIssuer(app.getBranchCode());// 发卡机构
					
					//如果是单机产品制卡，则进行以下操作
					if (isSingleProduct) {
						cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
						cardStockInfo.setAppOrgId(app.getBranchCode());
						cardStockInfo.setCardSectorId(app.getBranchCode());
						cardStockInfo.setAppDate(DateUtil.formatDate("yyyyMMdd"));
					} else {
						cardStockInfo.setCardStatus(CardStockState.UN_STOCK.getValue());
					}
					list.add(cardStockInfo);
				}
				// 在卡库存信息表里插入该批次所有卡号的记录
				cardStockInfoDAO.insertBatch(list);
			} else {
				logger.debug("卡建档处理失败，将制卡申请簿中的状态改建档失败。");
				app.setStatus(MakeCardAppState.CREATE_FAILURE.getValue());
				app.setReason(getNote(id));
				makeCardService.releaseCardNoAssign(app);
			}
			this.makeCardAppDAO.update(app);
		} catch (BizException e) {
			logger.error("卡资料建档报文后台返回处理发生异常，原因：" + e.getMessage(), e);
		}
	}

	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.CREATE_CARD_FILE, id);
		return waitsinfo == null ? "" : waitsinfo.getNote();
	}
}
