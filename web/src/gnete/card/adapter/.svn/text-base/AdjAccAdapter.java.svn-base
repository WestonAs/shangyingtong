package gnete.card.adapter;

import gnete.card.dao.AdjAccRegDAO;
import gnete.card.entity.AdjAccReg;
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
 * 调账流程适配器
 * @author: aps-lih
 */
@Repository
public class AdjAccAdapter implements WorkflowAdapter {

	@Autowired
	private AdjAccRegDAO adjsAccRegDAO;

	static Logger logger = Logger.getLogger(AdjAccAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		
		AdjAccReg adjAccReg = (AdjAccReg) this.adjsAccRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		adjAccReg.setStatus(RegisterState.PASSED.getValue());
		adjAccReg.setUpdateUser(userId);
		adjAccReg.setUpdateTime(new Date());
		
		this.adjsAccRegDAO.update(adjAccReg);
		
		// 发送报文消息
		MsgSender.sendMsg(MsgType.ADJ_ACC, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return this.adjsAccRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_ADJ;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
		AdjAccReg adjAccReg = (AdjAccReg) this.adjsAccRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		adjAccReg.setStatus(RegisterState.FALURE.getValue());
		adjAccReg.setUpdateUser(userId);
		adjAccReg.setUpdateTime(new Date());
		
		this.adjsAccRegDAO.update(adjAccReg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
