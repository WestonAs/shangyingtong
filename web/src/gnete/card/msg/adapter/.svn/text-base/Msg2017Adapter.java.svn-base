package gnete.card.msg.adapter;

import gnete.card.dao.OverdraftLmtRegDAO;
import gnete.card.entity.OverdraftLmtReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2017Adapter.java
 *
 * @description: 账户透支额度调整报文后台返回处理适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-26
 */
@Repository
public class Msg2017Adapter implements MsgAdapter{

	@Autowired
	private OverdraftLmtRegDAO overdraftLmtRegDAO;
	
	static Logger logger = Logger.getLogger(Msg2017Adapter.class);
	
	public void deal(Long id, boolean isSuccess) {
		
		OverdraftLmtReg overdraftLmtReg = (OverdraftLmtReg) this.overdraftLmtRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			logger.debug("账户额度调整成功，将账户透支额度登记簿中的状态改为成功。");
			overdraftLmtReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("账户额度调整失败，将账户透支额度登记簿中的状态改为失败。");
			overdraftLmtReg.setStatus(RegisterState.DISABLE.getValue());
		}
		this.overdraftLmtRegDAO.update(overdraftLmtReg);
	}
}
