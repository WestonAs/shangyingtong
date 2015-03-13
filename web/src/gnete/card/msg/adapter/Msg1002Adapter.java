package gnete.card.msg.adapter;

import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.msg.MsgAdapter;
import gnete.card.service.MakeCardService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg1002Adapter.java
 * 
 * @description: 建档撤销报文返回后续处理适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-20
 */
@Repository
public class Msg1002Adapter implements MsgAdapter {
	
	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private MakeCardService makeCardService;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;

	static Logger logger = Logger.getLogger(Msg1002Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		MakeCardApp app = (MakeCardApp) makeCardAppDAO.findByPk(id);
		if (isSuccess) {
			logger.debug("撤销制卡申请ID为" + id + "的建档成功！");
			makeCardService.releaseCardNoAssign(app);
			
			Assert.notEmpty(app.getStrNo(), "起始卡号不能为空");
			
			app.setStatus(MakeCardAppState.CREATE_CANCEL.getValue());
			
//			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(app.getStrNo());
//			Assert.notNull(cardInfo, "卡信息表中没有卡号[" + app.getStrNo() + "]的记录");
			logger.debug("起始卡号：[" + app.getStrNo() + "]");
			// 删除卡库存信息表里的记录 
//			for (Long i = 0L; i < app.getCardNum().longValue(); i++) {
//				// 卡的流水位
//				Long cardNoSeq = NumberUtils.toLong(StringUtils.substring(app.getStrNo(), 11, app.getStrNo().length() -1));
//				
//				String seqStr = Long.toString(cardNoSeq + i);
//				// 卡的前11位
//				String cardNoPrefix = StringUtils.substring(app.getStrNo(), 0, 11);
//				
//				String cardIdPre = cardNoPrefix + StringUtils.leftPad(seqStr, 7, "0");
//				String cardId = cardIdPre + CardUtil.luhnMod10(cardIdPre);
//				
//				this.cardStockInfoDAO.deleteByCardId(cardId);
//			}
			
			int count = this.cardStockInfoDAO.deleteByMakeId(app.getAppId());
			logger.debug("删除卡的张数为[" + count + "]");
		} else {
			logger.debug("撤销制卡申请ID为" + id + "的建档失败！");
			app.setStatus(MakeCardAppState.CREATE_CANCEL_FAILURE.getValue());
		}
		makeCardAppDAO.update(app);
	}

}
