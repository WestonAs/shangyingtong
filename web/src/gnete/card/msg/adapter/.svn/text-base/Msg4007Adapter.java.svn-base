package gnete.card.msg.adapter;

import gnete.card.dao.MembLevelChgRegDAO;
import gnete.card.entity.MembLevelChgReg;
import gnete.card.entity.state.MembLevelChgState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 会员级别变更
 * @Project: CardWeb
 * @File: Msg4007Adapter.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-25下午4:21:09
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
@Repository
public class Msg4007Adapter implements MsgAdapter {

	@Autowired
	private MembLevelChgRegDAO membLevelChgRegDAO;

	static Logger logger = Logger.getLogger(Msg4007Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		MembLevelChgReg reg = (MembLevelChgReg) membLevelChgRegDAO.findByPk(id);
		Assert.notNull(reg, "找不到会员级别变更数据:"+id);

		if (isSuccess) {
			logger.debug("会员关联成功");
			reg.setProcStatus(MembLevelChgState.NORMAL.getValue());
		} else {
			logger.debug("会员关联失败");
			reg.setProcStatus(MembLevelChgState.DISABLE.getValue());
		}
		membLevelChgRegDAO.update(reg);
	}

}
