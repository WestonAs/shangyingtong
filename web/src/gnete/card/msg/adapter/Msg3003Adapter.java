package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.MessageRegDAO;
import gnete.card.entity.MessageReg;
import gnete.card.entity.state.MessageRegStatus;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * @File: Msg3003Adapter.java
 * 
 * @description: 发送短信后台命令返回处理
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-4-15
 */
@Repository
public class Msg3003Adapter implements MsgAdapter{

	@Autowired
	private MessageRegDAO messageRegDAO;

	static Logger logger = Logger.getLogger(Msg3003Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		MessageReg reg = (MessageReg) messageRegDAO.findByPk(id);
		Assert.notNull(reg, "找不到短信记录");

		if (isSuccess) {
			logger.debug("发送短息成功");
			reg.setStatus(MessageRegStatus.SUCCESS_SENT.getValue());
		} else {
			logger.debug("发送短息失败");
			reg.setStatus(MessageRegStatus.FAILE_SENT.getValue());
		}
		messageRegDAO.update(reg);
	}

}
