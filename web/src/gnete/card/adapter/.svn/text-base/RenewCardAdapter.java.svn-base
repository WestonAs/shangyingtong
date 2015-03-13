package gnete.card.adapter;

import gnete.card.dao.RenewCardRegDAO;
import gnete.card.entity.RenewCardReg;
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
 * @File: RenewCardAdapter.java
 *
 * @description: 换卡审核流程
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-1 下午04:18:44
 */
@Repository
public class RenewCardAdapter implements WorkflowAdapter {

	@Autowired
	private RenewCardRegDAO renewCardRegDAO;

	private static final Logger logger = Logger.getLogger(RenewCardAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("换卡流程审核通过的相关处理。");
		RenewCardReg renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(NumberUtils.toLong(refid));
		
		renewCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		renewCardReg.setUpdateTime(new Date());
		renewCardReg.setUpdateUser(userId);
		
		this.renewCardRegDAO.update(renewCardReg);
		
		// 发报文到后台，以下由后台处理
		MsgSender.sendMsg(MsgType.RENEW_CARD, renewCardReg.getRenewCardId(), userId);
	}

	public Object getJobslip(String refid) {
		return renewCardRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_RENEW_CARD;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("换卡流程审批不通过，回退的相关处理。");
		RenewCardReg renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(NumberUtils.toLong(refid));
		
		renewCardReg.setStatus(RegisterState.FALURE.getValue());
		renewCardReg.setUpdateTime(new Date());
		renewCardReg.setUpdateUser(userId);
		
		this.renewCardRegDAO.update(renewCardReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
