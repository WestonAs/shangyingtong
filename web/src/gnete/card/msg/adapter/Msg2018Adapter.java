package gnete.card.msg.adapter;

import java.util.HashMap;
import java.util.Map;

import gnete.card.dao.CardDeferRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.CardDeferReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Filename: Msg2018Adapter.java Description: 卡延期报文后台返回处理适配器 Copyright:
 * Copyright (c)2010 Company: 深圳雁联计算系统有限公司
 * 
 * @author: aps-zsg
 * @version: V1.0 Create at: 2010-9-27 下午04:27:44
 */
@Repository
public class Msg2018Adapter implements MsgAdapter {

	@Autowired
	private CardDeferRegDAO cardDeferRegDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;

	public void deal(Long id, boolean isSuccess) {
		CardDeferReg cardDeferReg = (CardDeferReg) this.cardDeferRegDAO
				.findByPk(id);
		// 处理成功
		if (isSuccess){
			cardDeferReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			cardDeferReg.setStatus(RegisterState.DISABLE.getValue());
			
			Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.CARD_DEFER, id);
			cardDeferReg.setProcNote(waitsinfo.getNote());
		}
		this.cardDeferRegDAO.update(cardDeferReg);
	}

}
