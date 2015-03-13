package gnete.card.msg.adapter;

import gnete.card.dao.IcCancelCardRegDAO;
import gnete.card.dao.IcCardReversalDAO;
import gnete.card.dao.IcRenewCardRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.IcCancelCardReg;
import gnete.card.entity.IcCardReversal;
import gnete.card.entity.IcRenewCardReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.flag.ReversalFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IcReversalType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2204Adapter.java
 * 
 * @description: IC卡冲正相关
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-10-19 上午11:09:46
 */
@Repository
public class Msg2204Adapter implements MsgAdapter {

	@Autowired
	private IcRenewCardRegDAO icRenewCardRegDAO;
	@Autowired
	private IcCancelCardRegDAO icCancelCardRegDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private IcCardReversalDAO icCardReversalDAO;

	private static Logger logger = Logger.getLogger(Msg2204Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {

		IcCardReversal reversal = (IcCardReversal) this.icCardReversalDAO.findByPk(Long.toString(id));
		Assert.notNull(reversal, "冲正ID为[" + id + "]的记录不存在");

		if (isSuccess) {
			logger.debug("IC卡冲正成功");
			reversal.setStatus(RegisterState.NORMAL.getValue());

			// 换卡冲正
			if (IcReversalType.SWAP_CARD.getValue().equals(reversal.getReversalType())) {
				IcRenewCardReg reg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(reversal.getRefId());
				Assert.notNull(reg, "IC卡换卡批次ID为[" + reversal.getRefId() + "]的记录不存在");

				reg.setReversalFlag(ReversalFlag.SUCCESS_REVERSAL.getValue());
				this.icRenewCardRegDAO.update(reg);
			}
			// 销卡冲正
			else if (IcReversalType.CANCEL_CARD.getValue().equals(reversal.getReversalType())) {
				IcCancelCardReg reg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(reversal.getRefId());
				Assert.notNull(reg, "IC卡销卡批次号为[" + reversal.getRefId() + "]的记录不存在");

				reg.setReversalFlag(ReversalFlag.SUCCESS_REVERSAL.getValue());
				this.icCancelCardRegDAO.update(reg);
			}
		} else {
			logger.debug("IC卡冲正失败");
			reversal.setStatus(RegisterState.DISABLE.getValue());

			String msg = this.getNote(id);
			if (msg.length() >= 500) {
				reversal.setRemark("冲正失败，原因：" + StringUtils.substring(msg, 0, 480));
			} else {
				reversal.setRemark("冲正失败，原因：" + msg);
			}

			// 换卡冲正
			if (IcReversalType.SWAP_CARD.getValue().equals(reversal.getReversalType())) {
				IcRenewCardReg reg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(reversal.getRefId());
				Assert.notNull(reg, "IC卡换卡批次ID为[" + reversal.getRefId() + "]的记录不存在");

				reg.setStatus(RegisterState.DISABLE.getValue());
				reg.setRemark(reversal.getRemark());
				reg.setReversalFlag(ReversalFlag.FAILURE_REVERSAL.getValue());
				
				this.icRenewCardRegDAO.update(reg);
			}
			// 销卡冲正
			else if (IcReversalType.CANCEL_CARD.getValue().equals(reversal.getReversalType())) {
				IcCancelCardReg reg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(reversal.getRefId());
				Assert.notNull(reg, "IC卡销卡批次号为[" + reversal.getRefId() + "]的记录不存在");

				reg.setStatus(RegisterState.DISABLE.getValue());
				reg.setRemark(reversal.getRemark());
				reg.setReversalFlag(ReversalFlag.FAILURE_REVERSAL.getValue());
				
				this.icCancelCardRegDAO.update(reg);
			}
		}
		this.icCardReversalDAO.update(reversal);

	}

	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.IC_CARD_REVERSAL, id);
		return waitsinfo == null ? "" : StringUtils.trim(waitsinfo.getNote());
	}
}
