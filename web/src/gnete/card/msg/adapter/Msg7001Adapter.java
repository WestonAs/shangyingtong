package gnete.card.msg.adapter;

import gnete.card.dao.WxDepositReconRegDAO;
import gnete.card.entity.WxDepositReconReg;
import gnete.card.entity.state.WxRecocitionState;
import gnete.card.msg.MsgType;
import gnete.card.msg.adapter.base.MsgBaseAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <li>实名卡充值调账补帐报文后台返回处理适配器</li>
 * @Project: cardWx
 * @File: Msg7001Adapter.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午04:41:19
 */
@Repository
public class Msg7001Adapter extends MsgBaseAdapter {
	
	@Autowired
	private WxDepositReconRegDAO wxDepositReconRegDAO;
	
	private final static Logger logger = Logger.getLogger(Msg7001Adapter.class);

	@Override
	public void deal(Long id, boolean isSuccess) throws BizException {
		try {
			WxDepositReconReg depoReconReg = (WxDepositReconReg) this.wxDepositReconRegDAO.findByPk(id);
			Assert.notNull(depoReconReg, "实名卡扣款充值对账处理登记簿中不存在ID为[" + id + "]的记录");
			
			if (isSuccess) {
				logger.debug("实名卡扣款充值对账后台处理成功");
				depoReconReg.setStatus(WxRecocitionState.SUCCESS.getValue());
			} else {
				logger.debug("实名卡扣款充值对账后台处理失败");
				depoReconReg.setStatus(WxRecocitionState.FAILURE.getValue());
			}
			
			depoReconReg.setRemark(this.getWaitsinfoNote(MsgType.WX_DEPOSIT_RECO, id));
			
			this.wxDepositReconRegDAO.update(depoReconReg);
		} catch (Exception e) {
			logger.error("实名卡扣款充值对账报文后台返回处理发生异常，原因：" + e.getMessage(), e);
		}
	}

}
