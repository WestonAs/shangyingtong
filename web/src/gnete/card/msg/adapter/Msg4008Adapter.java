package gnete.card.msg.adapter;

import gnete.card.dao.PointSendApplyRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.PointSendApplyReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.PointSendApplyRegState;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @description: 积分累积赠送 明细汇总请求后台命令返回处理
 */
@Repository
public class Msg4008Adapter implements MsgAdapter {

	@Autowired
	private PointSendApplyRegDAO pointSendApplyRegDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDao;
	
	static Logger logger = Logger.getLogger(Msg4008Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		PointSendApplyReg reg = (PointSendApplyReg) pointSendApplyRegDAO.findByPk(id);
		Assert.notNull(reg, "找不到积分累积赠送申请记录");

		if (isSuccess) {
			logger.debug("积分累积赠送 明细汇总请求 汇总成功");
			reg.setStatus(PointSendApplyRegState.SUMMARIZING_SUCCESS.getValue());
			pointSendApplyRegDAO.update(reg);
		} else {
			logger.info("积分累积赠送 明细汇总请求 汇总失败");
			Waitsinfo wi = waitsinfoDao.findWaitsinfo(MsgType.POINT_ACCUMULATION_SEND_SUMMARIZING, id);
			reg.setMemo(wi.getNote());
			reg.setStatus(PointSendApplyRegState.SUMMARIZING_FAILED.getValue());
			pointSendApplyRegDAO.update(reg);
		}
	}

	
}
