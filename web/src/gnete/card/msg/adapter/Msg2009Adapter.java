package gnete.card.msg.adapter;

import gnete.card.dao.RetransCardRegDAO;
import gnete.card.entity.RetransCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgType;
import gnete.card.msg.adapter.base.MsgBaseAdapter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2009Adapter.java
 * @description: 卡补帐报文返回处理适配器
 */
@Repository
public class Msg2009Adapter extends MsgBaseAdapter {

	@Autowired
	private RetransCardRegDAO	retransCardRegDAO;

	static final Logger			logger	= Logger.getLogger(Msg2009Adapter.class);

	public void deal(Long id, boolean isSuccess) {

		RetransCardReg retransCardReg = (RetransCardReg) this.retransCardRegDAO.findByPk(id);

		if (isSuccess) {
			logger.debug("补帐处理成功，将卡补账登记簿中的状态改为成功。");
			retransCardReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("补帐处理失败，将卡补账登记簿中的状态改为失败。");
			retransCardReg.setStatus(RegisterState.DISABLE.getValue());
			retransCardReg.setRemark(StringUtils.trimToEmpty(retransCardReg.getRemark())
					+ super.getWaitsinfoNote(MsgType.RETRANS_CARD, id));
		}
		this.retransCardRegDAO.update(retransCardReg);
	}
}
