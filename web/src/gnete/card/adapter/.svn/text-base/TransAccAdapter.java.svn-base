package gnete.card.adapter;

import java.util.Date;

import gnete.card.dao.TransAccRegDAO;
import gnete.card.entity.TransAccReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 转帐流程适配器
 * @author: aps-lih
 */
@Repository
public class TransAccAdapter implements WorkflowAdapter {

	@Autowired
	private TransAccRegDAO transAccRegDAO;

	static Logger logger = Logger.getLogger(TransAccAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		
		TransAccReg transAccReg = (TransAccReg) this.transAccRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		transAccReg.setStatus(RegisterState.PASSED.getValue());
		transAccReg.setUpdateUser(userId);
		transAccReg.setUpdateTime(new Date());
		
		this.transAccRegDAO.update(transAccReg);
		
		// 发送报文消息
		MsgSender.sendMsg(MsgType.TRANS_ACC, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return this.transAccRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_TRANSFER;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
		TransAccReg transAccReg = (TransAccReg) this.transAccRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		transAccReg.setStatus(RegisterState.FALURE.getValue());
		transAccReg.setUpdateUser(userId);
		transAccReg.setUpdateTime(new Date());
		
		this.transAccRegDAO.update(transAccReg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
