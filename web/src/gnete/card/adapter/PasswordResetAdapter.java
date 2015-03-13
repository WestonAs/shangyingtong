package gnete.card.adapter;

import java.util.Date;
import gnete.card.dao.PasswordResetRegDAO;
import gnete.card.entity.PasswordResetReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 密码重置流程适配器
 * @author: aps-lib
 */
@Repository
public class PasswordResetAdapter implements WorkflowAdapter{
	@Autowired
	private PasswordResetRegDAO passwordResetRegDAO;
	
	static Logger logger = Logger.getLogger(PasswordResetAdapter.class);
	
	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		
		PasswordResetReg reg = (PasswordResetReg) this.passwordResetRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		reg.setStatus(RegisterState.PASSED.getValue());
		reg.setUpdateBy(userId);
		reg.setUpdateTime(new Date());
		
		this.passwordResetRegDAO.update(reg);
		
		// 发送报文消息
		MsgSender.sendMsg(MsgType.PASSWORD_RESET, NumberUtils.toLong(refid), userId);
		
	}

	public Object getJobslip(String refid) {
		return this.passwordResetRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_PASSWORD_RESET_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
		PasswordResetReg reg = (PasswordResetReg) this.passwordResetRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		reg.setStatus(RegisterState.FALURE.getValue());
		reg.setUpdateBy(userId);
		reg.setUpdateTime(new Date());
		
		this.passwordResetRegDAO.update(reg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
