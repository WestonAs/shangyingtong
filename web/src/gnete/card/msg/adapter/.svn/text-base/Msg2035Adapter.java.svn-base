package gnete.card.msg.adapter;

import java.util.HashMap;
import java.util.Map;

import gnete.card.dao.IcCardActiveDAO;
import gnete.card.dao.IcEcashDepositRegDAO;
import gnete.card.dao.IcEcashReversalDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.IcCardActive;
import gnete.card.entity.IcEcashDepositReg;
import gnete.card.entity.IcEcashReversal;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.flag.ReversalFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.ReversalType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.etc.Assert;
import gnete.etc.BizException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2035Adapter.java
 * 
 * @description: 冲正处理
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-20
 */
@Repository
public class Msg2035Adapter implements MsgAdapter {

	@Autowired
	private IcEcashDepositRegDAO icEcashDepositRegDAO;
	@Autowired
	private IcEcashReversalDAO icEcashReversalDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private IcCardActiveDAO icCardActiveDAO;

	private static Logger logger = Logger.getLogger(Msg2035Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {

		IcEcashReversal reversal = this.lockReversal(Long.toString(id));
		Assert.notNull(reversal, "冲正ID为[" + id + "]的记录不存在");

		if (isSuccess) {
			logger.debug("冲正成功");
			reversal.setStatus(RegisterState.NORMAL.getValue());

			// 电子现金充值冲正
			if (ReversalType.DEPOSIT.getValue().equals(reversal.getReversalType())) {
				IcEcashDepositReg reg = lockDepsosit(reversal.getDepositBatchId());
				Assert.notNull(reg, "充值ID为[" + reversal.getDepositBatchId() + "]的记录不存在");

				reg.setReversalFlag(ReversalFlag.SUCCESS_REVERSAL.getValue());
				this.releaseDepositLock(reg);
			}
			// 激活冲正
			else {
				IcCardActive reg = (IcCardActive) this.icCardActiveDAO.findByPk(reversal.getDepositBatchId());
				Assert.notNull(reg, "激活批次号为[" + reversal.getDepositBatchId() + "]的记录不存在");
				
				reg.setReversalFlag(ReversalFlag.SUCCESS_REVERSAL.getValue());
				this.icCardActiveDAO.update(reg);
			}
		} else {
			logger.debug("冲正失败");
			reversal.setStatus(RegisterState.DISABLE.getValue());
			String msg = this.getNote(id);
			if (msg.length() >= 500) {
				reversal.setRemark("冲正失败，原因：" + msg.substring(0, 480));
			} else {
				reversal.setRemark("冲正失败，原因：" + msg);
			}
			
			// 电子现金充值冲正
			if (ReversalType.DEPOSIT.getValue().equals(reversal.getReversalType())) {
				IcEcashDepositReg reg = lockDepsosit(reversal.getDepositBatchId());
				Assert.notNull(reg, "充值ID为[" + reversal.getDepositBatchId() + "]的记录不存在");

				reg.setStatus(RegisterState.DISABLE.getValue());
				reg.setRemark(reversal.getRemark());
				reg.setReversalFlag(ReversalFlag.FAILURE_REVERSAL.getValue());
				this.releaseDepositLock(reg);
			}
			// 激活冲正
			else {
				IcCardActive reg = (IcCardActive) this.icCardActiveDAO.findByPk(reversal.getDepositBatchId());
				Assert.notNull(reg, "激活批次号为[" + reversal.getDepositBatchId() + "]的记录不存在");
				
				reg.setStatus(RegisterState.DISABLE.getValue());
				reg.setRemark(reversal.getRemark());
				reg.setReversalFlag(ReversalFlag.FAILURE_REVERSAL.getValue());
				this.icCardActiveDAO.update(reg);
			}
		}
		this.releaseReversalLock(reversal);
		
//		// 电子现金充值冲正
//		if (ReversalType.DEPOSIT.getValue().equals(reversal.getReversalType())) {
//			IcEcashDepositReg reg = lockDepsosit(reversal.getDepositBatchId());
//			Assert.notNull(reg, "充值ID为[" + reversal.getDepositBatchId() + "]的记录不存在");
//
//			// 冲正处理
//			if (isSuccess) {
//				logger.debug("冲正成功");
//				reversal.setStatus(RegisterState.NORMAL.getValue());
//
//				reg.setReversalFlag(ReversalFlag.SUCCESS_REVERSAL.getValue());
//			} else {
//				logger.debug("冲正失败");
//				reversal.setStatus(RegisterState.DISABLE.getValue());
//				String msg = this.getNote(id);
//				if (msg.length() >= 500) {
//					reversal.setRemark("冲正失败，原因：" + msg.substring(0, 480));
//				} else {
//					reversal.setRemark("冲正失败，原因：" + msg);
//				}
//
//				reg.setStatus(RegisterState.DISABLE.getValue());
//				reg.setRemark(reversal.getRemark());
//				reg.setReversalFlag(ReversalFlag.FAILURE_REVERSAL.getValue());
//			}
//
//			this.releaseReversalLock(reversal);
//			this.releaseDepositLock(reg);
//		} else {// 激活冲正
//			IcCardActive reg = (IcCardActive) this.icCardActiveDAO.findByPk(reversal.getDepositBatchId());
//			Assert.notNull(reg, "激活批次号为[" + reversal.getDepositBatchId() + "]的记录不存在");
//
//			// 冲正处理
//			if (isSuccess) {
//				logger.debug("冲正成功");
//				reversal.setStatus(RegisterState.NORMAL.getValue());
//
//				reg.setReversalFlag(ReversalFlag.SUCCESS_REVERSAL.getValue());
//			} else {
//				logger.debug("冲正失败");
//				reversal.setStatus(RegisterState.DISABLE.getValue());
//				String msg = this.getNote(id);
//				if (msg.length() >= 500) {
//					reversal.setRemark("冲正失败，原因：" + msg.substring(0, 480));
//				} else {
//					reversal.setRemark("冲正失败，原因：" + msg);
//				}
//
//				reg.setStatus(RegisterState.DISABLE.getValue());
//				reg.setRemark(reversal.getRemark());
//				reg.setReversalFlag(ReversalFlag.FAILURE_REVERSAL.getValue());
//			}
//
//			this.releaseReversalLock(reversal);
//			this.icCardActiveDAO.update(reg);
//		}
	}

	/**
	 * 锁定IC卡电子现金充值登记薄
	 * 
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcEcashReversal lockReversal(String id) throws BizException {
		IcEcashReversal depositReg = new IcEcashReversal();
		try {
			depositReg = (IcEcashReversal) this.icEcashReversalDAO
					.findByPkWithLock(id);
		} catch (Exception e) {
			String msg = "锁定IC卡电子现金冲正登记薄充值批次ID为[" + id + "]的记录失败，原因："
					+ e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
		return depositReg;
	}

	/**
	 * 将IC卡电子现金充值登记簿记录提交
	 * 
	 * @param depositReg
	 * @throws BizException
	 */
	private void releaseReversalLock(IcEcashReversal reversal)
			throws BizException {
		try {
			this.icEcashReversalDAO.update(reversal);
		} catch (Exception e) {
			String msg = "更新IC卡电子现金冲正登记簿，释放锁失败。原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
	}

	/**
	 * 锁定IC卡电子现金充值登记薄
	 * 
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcEcashDepositReg lockDepsosit(String id) throws BizException {
		IcEcashDepositReg depositReg = new IcEcashDepositReg();
		try {
			depositReg = (IcEcashDepositReg) this.icEcashDepositRegDAO
					.findByPkWithLock(id);
		} catch (Exception e) {
			String msg = "锁定IC卡电子现金充值登记薄充值批次ID为[" + id + "]的记录失败，原因："
					+ e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
		return depositReg;
	}

	/**
	 * 将IC卡电子现金充值登记簿记录提交
	 * 
	 * @param depositReg
	 * @throws BizException
	 */
	private void releaseDepositLock(IcEcashDepositReg depositReg)
			throws BizException {
		try {
			this.icEcashDepositRegDAO.update(depositReg);
		} catch (Exception e) {
			String msg = "更新IC卡电子现金充值登记簿，释放锁失败。原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
	}

	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.IC_ECASH_REVERSAL, id);
		return waitsinfo == null ? "" : StringUtils.trim(waitsinfo.getNote());
	}
}
