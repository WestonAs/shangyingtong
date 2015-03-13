package gnete.card.msg.adapter;

import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.etc.BizException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**  
 * Filename:    Msg2007Adapter.java  
 * Description: 换卡报文后台返回处理适配器
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-25 下午02:35:22  
 */
@Repository
public class Msg2007Adapter implements MsgAdapter {

	@Autowired
	private RenewCardRegDAO renewCardRegDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		RenewCardReg renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(id);
		
		// 处理成功
		if (isSuccess){
			renewCardReg.setStatus(RegisterState.NORMAL.getValue());

			this.renewCardRegDAO.update(renewCardReg);
			
			// 更新新卡的卡库存状态为“已售出”
			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(renewCardReg.getNewCardId());
			
			cardStockInfo.setCardStatus(CardStockState.SOLD_OUT.getValue());
			this.cardStockInfoDAO.update(cardStockInfo);
		} else {
			String remark = String.format("%s [后台处理失败原因：%s]", renewCardReg.getRemark() == null ? ""
					: renewCardReg.getRemark(), getNote(id));
			renewCardReg.setRemark(remark);
			renewCardReg.setStatus(RegisterState.DISABLE.getValue());
			this.renewCardRegDAO.update(renewCardReg);
		}
	}

	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.RENEW_CARD, id);
		return waitsinfo == null ? "" : waitsinfo.getNote();
	}
}
