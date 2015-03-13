package gnete.card.msg.adapter;

import gnete.card.dao.CancelCardRegDAO;
import gnete.card.entity.CancelCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgType;
import gnete.card.msg.adapter.base.MsgBaseAdapter;
import gnete.etc.BizException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Filename: Msg2013Adapter.java Description: 退卡销户报文后台返回处理适配器
 */
@Repository
public class Msg2013Adapter extends MsgBaseAdapter {

	@Autowired
	private CancelCardRegDAO	cancelCardRegDAO;

	public void deal(Long id, boolean isSuccess) throws BizException {
		CancelCardReg cancelCardReg = (CancelCardReg) this.cancelCardRegDAO.findByPk(id);
		if (isSuccess) {
			cancelCardReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			cancelCardReg.setStatus(RegisterState.DISABLE.getValue());
			cancelCardReg.setRemark(StringUtils.trimToEmpty(cancelCardReg.getRemark())
					+ super.getWaitsinfoNote(MsgType.CANCEL_CARD, id));
		}
		this.cancelCardRegDAO.update(cancelCardReg);
	}

}
