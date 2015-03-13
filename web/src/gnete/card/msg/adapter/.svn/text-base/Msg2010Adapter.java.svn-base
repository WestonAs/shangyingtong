package gnete.card.msg.adapter;

import gnete.card.dao.TransAccRegDAO;
import gnete.card.entity.TransAccReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgType;
import gnete.card.msg.adapter.base.MsgBaseAdapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 转帐报文后台返回处理适配器
 * 
 * @author: aps-lih
 */
@Repository
public class Msg2010Adapter extends MsgBaseAdapter {

	@Autowired
	private TransAccRegDAO	transAccRegDAO;

	public void deal(Long id, boolean isSuccess) {
		TransAccReg transAccReg = (TransAccReg) this.transAccRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			transAccReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			transAccReg.setStatus(RegisterState.DISABLE.getValue());
			transAccReg.setRemark(StringUtils.trimToEmpty(transAccReg.getRemark())
					+ getWaitsinfoNote(MsgType.TRANS_ACC, id));
		}
		this.transAccRegDAO.update(transAccReg);
	}

}
