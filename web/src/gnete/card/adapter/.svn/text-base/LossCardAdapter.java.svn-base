package gnete.card.adapter;

import gnete.card.dao.LossCardRegDAO;
import gnete.card.entity.LossCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: LossCardAdapter.java
 *
 * @description: 挂失审核流程适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-5-4 下午03:05:58
 */
@Repository
public class LossCardAdapter implements WorkflowAdapter {
	
	@Autowired
	private LossCardRegDAO lossCardRegDAO;
	
	static Logger logger = Logger.getLogger(LossCardAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("卡挂失审批通过的相关处理。");
		
		LossCardReg lossCardReg = (LossCardReg) this.lossCardRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		// 审核通过，改为待处理状态，同时发送命令到后台
		lossCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		lossCardReg.setUpdateTime(new Date());
		lossCardReg.setUpdateUser(userId);
		
		this.lossCardRegDAO.update(lossCardReg);
		
		//登记成功后，组挂失报文，发送后台修改卡信息表“卡状态”为"挂失"
		MsgSender.sendMsg(MsgType.LOSS_CARD, lossCardReg.getLossBatchId(), lossCardReg.getUpdateUser());
	}

	public Object getJobslip(String refid) {
		return this.lossCardRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_LOSS_CARD;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		
		logger.debug("卡挂失审批不通过，回退的相关处理。");
		
		LossCardReg lossCardReg = (LossCardReg) this.lossCardRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		// 审核通过，改为待处理状态，同时发送命令到后台
		lossCardReg.setStatus(RegisterState.FALURE.getValue());
		lossCardReg.setUpdateTime(new Date());
		lossCardReg.setUpdateUser(userId);
		
		this.lossCardRegDAO.update(lossCardReg);
		
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
		
	}

}
