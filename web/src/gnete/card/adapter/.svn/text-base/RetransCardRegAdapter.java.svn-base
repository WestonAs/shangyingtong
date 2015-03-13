package gnete.card.adapter;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.RetransCardRegDAO;
import gnete.card.entity.RetransCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * @File: RetransCardRegAdapter.java
 * 
 * @description: 卡补账申请流程审核适配器
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-9-3
 */
@Repository
public class RetransCardRegAdapter implements WorkflowAdapter {

	@Autowired
	private RetransCardRegDAO retransCardRegDAO;

	static Logger logger = Logger.getLogger(RetransCardRegAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");

		RetransCardReg retransCardReg = (RetransCardReg) this.retransCardRegDAO
				.findByPk(NumberUtils.toLong(refid));
		retransCardReg.setStatus(RegisterState.PASSED.getValue());
		retransCardReg.setUpdateUser(userId);
		retransCardReg.setUpdateTime(new Date());
		this.retransCardRegDAO.update(retransCardReg);
		// 发补帐命令2009
		MsgSender.sendMsg(MsgType.RETRANS_CARD, NumberUtils.toLong(refid),
				userId);
	}

	public Object getJobslip(String refid) {
		return this.retransCardRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_RETRANS_CARD_REG;
	}

	public void postBackward(String refid, Integer nodeId/* 没用到 */,
			String param, String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");

		// 更新状态为审核失败
		RetransCardReg retransCardReg = (RetransCardReg) this.retransCardRegDAO
				.findByPk(NumberUtils.toLong(refid));
		retransCardReg.setStatus(RegisterState.FALURE.getValue());
		retransCardReg.setUpdateUser(userId);
		retransCardReg.setUpdateTime(new Date());
		this.retransCardRegDAO.update(retransCardReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
