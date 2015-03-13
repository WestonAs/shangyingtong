package gnete.card.msg.adapter;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CouponBatRegDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CouponBatReg;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.IssType;
import gnete.card.msg.MsgAdapter;
import gnete.card.service.CardRiskService;
import gnete.card.service.CardStockService;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * @File: Msg2021Adapter.java
 *
 * @description: 批量派赠赠券卡报文后台返回处理适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-10-14
 */
@Repository
public class Msg2021Adapter implements MsgAdapter{

	@Autowired
	private CouponBatRegDAO couponBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private CardStockService cardStockService;
	@Autowired
	private CardRiskService cardRiskService;
	
	static Logger logger = Logger.getLogger(Msg2017Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		CouponBatReg couponBatReg = (CouponBatReg) this.couponBatRegDAO.findByPk(id);
		if (isSuccess) {
			logger.debug("批量派赠赠券卡成功，将批量派赠赠券卡登记簿中的状态改为成功。");
			couponBatReg.setStatus(RegisterState.NORMAL.getValue());
			cardStockService.updateCardStockState(couponBatReg.getStartCard(), 
					couponBatReg.getCardNum().intValue(), CardStockState.SEND_OUT.getValue());
			
		} else {
			logger.debug("批量派赠赠券卡失败，将批量派赠赠券卡登记簿中的状态改为失败。");
			couponBatReg.setStatus(RegisterState.DISABLE.getValue());
			
			// 把风险准备金补回来
			BigDecimal cardNum = couponBatReg.getCardNum();
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(couponBatReg.getStartCard());
			Assert.notNull(cardInfo, "不存在卡号[" + couponBatReg.getStartCard() + "]");
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClassDef, "不存在卡子类型定义。");
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(cardSubClassDef.getCouponClass());
			
			if(IssType.CARD.getValue().equals(couponClassDef.getJinstType())){
				String cardssuer = couponClassDef.getJinstId();
				BigDecimal amount = cardNum.multiply(couponBatReg.getFaceValue());
				
				CardRiskReg cardRiskReg = new CardRiskReg();
				cardRiskReg.setId(couponBatReg.getCouponBatRegId());	
				cardRiskReg.setAdjType(AdjType.COUPON.getValue());
				cardRiskReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), amount));
				cardRiskReg.setBranchCode(cardssuer);	
				cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
				this.cardRiskService.activateCardRisk(cardRiskReg);
			}
		}
		this.couponBatRegDAO.update(couponBatReg);
	}

}
