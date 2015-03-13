package gnete.card.adapter;

import gnete.card.dao.OverdraftLmtRegDAO;
import gnete.card.entity.OverdraftLmtReg;
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
 * @File: OverdraftAdapter.java
 *
 * @description: 账户透支金额调整申请流程审核适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-26
 */
@Repository
public class OverdraftAdapter implements WorkflowAdapter{

	@Autowired
	private OverdraftLmtRegDAO overdraftLmtRegDAO;
	
	static Logger logger = Logger.getLogger(GiftAdapter.class);
	
	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		
		logger.debug("审批通过的相关处理。");
		
		OverdraftLmtReg overdraftLmtReg = (OverdraftLmtReg) this.overdraftLmtRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		overdraftLmtReg.setStatus(RegisterState.PASSED.getValue());
		this.overdraftLmtRegDAO.update(overdraftLmtReg);
		
		// 组建档报文，然后发到后台。根据后台处理结果更新账户信息表和签单客户表里的记录
		MsgSender.sendMsg(MsgType.OVERDRAFT_LMT, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return this.overdraftLmtRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_OVERDRAFT_LMT_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		
		logger.debug("审批不通过，回退的相关处理。");
		OverdraftLmtReg overdraftLmtReg = (OverdraftLmtReg) this.overdraftLmtRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		// 更新状态为审核失败
		overdraftLmtReg.setStatus(RegisterState.FALURE.getValue());
		this.overdraftLmtRegDAO.update(overdraftLmtReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		
		logger.debug("下发");
	}

}
