package gnete.card.adapter;

import java.util.Date;

import gnete.card.dao.DepositRegDAO;
import gnete.card.entity.DepositReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: DepositCancelAdapter.java
 *
 * @description: 充值撤销审核处理流程
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-16
 */
@Repository
public class DepositCancelAdapter implements WorkflowAdapter {

	@Autowired
	private DepositRegDAO depositRegDAO;

	static Logger logger = Logger.getLogger(DepositCancelAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("充值撤销审核通过的相关处理。将原记录改为已撤销。");
		DepositReg deposit = (DepositReg) depositRegDAO.findByPk(NumberUtils.toLong(refid));
		deposit.setStatus(RegisterState.PASSED.getValue());
		deposit.setUpdateTime(new Date());
		deposit.setUpdateUser(userId);
		
		Assert.notNull(deposit.getOldDepositBatch(), "原充值批次号不能为空");
		DepositReg oldReg = (DepositReg) depositRegDAO.findByPk(deposit.getOldDepositBatch());
		oldReg.setStatus(RegisterState.CANCELED.getValue());
		
		depositRegDAO.update(deposit);
		depositRegDAO.update(oldReg);
		
		// 发送充值撤销报文
		MsgSender.sendMsg(MsgType.DEPOSIT_CANCEL, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return depositRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_DEPOSIT_CANCEL;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，将撤销的记录改为审核不通过状态，原纪录的状态改为成功状态，可再次撤销。");
		DepositReg deposit = (DepositReg) depositRegDAO.findByPk(NumberUtils.toLong(refid));
		
		deposit.setStatus(RegisterState.FALURE.getValue());
		deposit.setUpdateTime(new Date());
		deposit.setUpdateUser(userId);

		DepositReg oldReg = (DepositReg) depositRegDAO.findByPk(deposit.getOldDepositBatch());
		oldReg.setStatus(RegisterState.NORMAL.getValue());
		
		depositRegDAO.update(oldReg);
		depositRegDAO.update(deposit);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发的相关处理。");
	}

}
