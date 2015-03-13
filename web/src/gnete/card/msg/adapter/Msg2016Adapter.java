package gnete.card.msg.adapter;

import gnete.card.dao.PointExcRegDAO;
import gnete.card.entity.PointExcReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2016Adapter.java
 *
 * @description: 积分返利报文后台返回处理适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-29
 */
@Repository
public class Msg2016Adapter implements MsgAdapter{

	@Autowired
	private PointExcRegDAO pointExcRegDAO;
	
	static Logger logger = Logger.getLogger(Msg2016Adapter.class);
	
	public void deal(Long id, boolean isSuccess) {
		PointExcReg app = (PointExcReg) this.pointExcRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			logger.debug("积分返利处理成功，将积分返利登记簿中的状态改为成功。");
			app.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("积分返利处理失败，将积分返利登记簿中的状态改为失败。");
			app.setStatus(RegisterState.DISABLE.getValue());
		}
		this.pointExcRegDAO.update(app);
	}

}
