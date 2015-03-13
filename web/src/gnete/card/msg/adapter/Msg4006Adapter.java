package gnete.card.msg.adapter;

import gnete.card.dao.MembAppointDetailRegDAO;
import gnete.card.dao.MembAppointRegDAO;
import gnete.card.entity.MembAppointDetailReg;
import gnete.card.entity.MembAppointReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 会员关联
 * @Project: CardWeb
 * @File: Msg4006Adapter.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-22下午4:03:59
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
@Repository
public class Msg4006Adapter implements MsgAdapter {

	@Autowired
	private MembAppointRegDAO membAppointRegDAO;
	@Autowired
	private MembAppointDetailRegDAO membAppointDetailRegDAO;

	static Logger logger = Logger.getLogger(Msg4006Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		MembAppointReg reg = new MembAppointReg();
		reg.setMembAppointRegId(id);
		reg=	(MembAppointReg) membAppointRegDAO.findByPk(reg);
		Assert.notNull(reg, "找不到会员关联处理数据:"+id);

		MembAppointDetailReg membAppointDetailReg = new MembAppointDetailReg();
		membAppointDetailReg.setMembAppointRegId(reg.getMembAppointRegId());
		if (isSuccess) {
			logger.debug("会员关联成功");
			reg.setStatus(RegisterState.NORMAL.getValue());
			membAppointDetailReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("会员关联失败");
			reg.setStatus(RegisterState.DISABLE.getValue());
			membAppointDetailReg.setStatus(RegisterState.DISABLE.getValue());
		}
		//更新主表
		membAppointRegDAO.update(reg);
		
		//更新明细表
		membAppointDetailRegDAO.updateStautsByMembAppointRegId(membAppointDetailReg);
	}

}
