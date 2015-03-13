package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.GiftExcRegDAO;
import gnete.card.entity.GiftExcReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;

/**
 * @File: Msg1001Adapter.java
 *
 * @description: 积分兑换礼品报文后台返回处理适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-21
 */
@Repository
public class Msg2014Adapter implements MsgAdapter{

	@Autowired
	private GiftExcRegDAO giftExcRegDAO;
	
	static Logger logger = Logger.getLogger(Msg2014Adapter.class);

	public void deal(Long id, boolean isSuccess) {
		GiftExcReg app = (GiftExcReg) this.giftExcRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			logger.debug("积分兑换礼品处理成功，将礼品兑换登记簿中的状态改为成功。");
			app.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("积分兑换礼品处理失败，将礼品兑换登记簿中的状态改为失败。");
			app.setStatus(RegisterState.DISABLE.getValue());
		}
		this.giftExcRegDAO.update(app);
	}
	
}
