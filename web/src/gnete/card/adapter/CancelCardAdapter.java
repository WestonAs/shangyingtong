package gnete.card.adapter;

import gnete.card.dao.CancelCardRegDAO;
import gnete.card.entity.CancelCardReg;
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
 * @File: CancelCardAdapter.java
 *
 * @description: 退卡销户流程适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-3-22
 */
@Repository
public class CancelCardAdapter implements WorkflowAdapter {
	
	@Autowired
	private CancelCardRegDAO cancelCardRegDAO;
	
	static Logger logger = Logger.getLogger(CancelCardAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		
		CancelCardReg cancelCardReg = (CancelCardReg) this.cancelCardRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		cancelCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		cancelCardReg.setUpdateUser(userId);
		cancelCardReg.setUpdateTime(new Date());
		
		this.cancelCardRegDAO.update(cancelCardReg);
		
		//登记成功后，组退卡销户报文
		MsgSender.sendMsg(MsgType.CANCEL_CARD, cancelCardReg.getCancelCardId(), cancelCardReg.getUpdateUser());
	}

	public Object getJobslip(String refid) {
		return this.cancelCardRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_CANCEL_CARD;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
		CancelCardReg cancelCardReg = (CancelCardReg) this.cancelCardRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		cancelCardReg.setStatus(RegisterState.FALURE.getValue());
		cancelCardReg.setUpdateUser(userId);
		cancelCardReg.setUpdateTime(new Date());
		
		this.cancelCardRegDAO.update(cancelCardReg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}
}
