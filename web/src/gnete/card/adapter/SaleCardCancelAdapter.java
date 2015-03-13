package gnete.card.adapter;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.SaleCardRegDAO;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

/**
 * @File: SaleCardCancelAdapter.java
 *
 * @description: 售卡撤销流程审核适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-11-24
 */
@Repository
public class SaleCardCancelAdapter implements WorkflowAdapter {
	
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;

	static Logger logger = Logger.getLogger(SaleCardCancelAdapter.class);
	
	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("售卡撤销流程审批通过的相关处理。");
		// 将售卡撤销的记录的状态改为"待处理"。等待发报文后的处理。
		SaleCardReg saleCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(NumberUtils.toLong(refid));
		Assert.notNull(saleCardReg, "售卡撤销[" + refid + "]的记录不存在");
		
		saleCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(userId);
		
		// 将原售卡记录的状态保持不变，还是“待处理状态”
		Assert.notNull(saleCardReg.getOldSaleBatch(), "原售卡批次号不能为空");
		SaleCardReg oldReg = (SaleCardReg) this.saleCardRegDAO.findByPk(saleCardReg.getOldSaleBatch());
		
		Assert.notNull(oldReg, "原售卡记录已经不存在");
		// oldReg.setStatus(RegisterState.CANCELED.getValue());
		
		this.saleCardRegDAO.update(saleCardReg);
//		this.saleCardRegDAO.update(oldReg);
		
		// 发送售卡撤销报文
		MsgSender.sendMsg(MsgType.SELL_CARD_CANCEL, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return this.saleCardRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_SALECARD_CANCEL;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("售卡撤销流程审批通过的相关处理。");
		// 将当前售卡撤销的记录改为审核不通过
		SaleCardReg saleCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(NumberUtils.toLong(refid));
		Assert.notNull(saleCardReg, "售卡撤销[" + refid + "]的记录不存在");
		
		saleCardReg.setStatus(RegisterState.FALURE.getValue());
		saleCardReg.setUpdateTime(new Date());
		saleCardReg.setUpdateUser(userId);
		
		// 将原售卡记录改为成功，可再次撤销
		Assert.notNull(saleCardReg.getOldSaleBatch(), "原售卡批次号不能为空");
		SaleCardReg oldReg = (SaleCardReg) this.saleCardRegDAO.findByPk(saleCardReg.getOldSaleBatch());
		Assert.notNull(oldReg, "原售卡记录已经不存在");
		oldReg.setStatus(RegisterState.NORMAL.getValue());
		
		this.saleCardRegDAO.update(saleCardReg);
		this.saleCardRegDAO.update(oldReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发流程。");
	}

}
