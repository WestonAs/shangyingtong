package gnete.card.msg.adapter;

import gnete.card.dao.DrawDefDAO;
import gnete.card.entity.DrawDef;
import gnete.card.entity.state.DrawDefineState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 抽奖
 * 
 * @Project: CardWeb
 * @File: Msg4005Adapter.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-5下午3:34:36
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
@Repository
public class Msg4005Adapter implements MsgAdapter {

	@Autowired
	private DrawDefDAO drawDefDAO;

	static Logger logger = Logger.getLogger(Msg4005Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		DrawDef reg = (DrawDef) drawDefDAO.findByPk(String.valueOf(id));
		Assert.notNull(reg, "找不到该抽奖活动:"+id);

		if (isSuccess) {
			logger.debug("抽奖成功");
			reg.setStatus(DrawDefineState.DRAWED_Y.getValue());
		} else {
			logger.debug("抽奖失败");
		}
		drawDefDAO.update(reg);
	}

}
