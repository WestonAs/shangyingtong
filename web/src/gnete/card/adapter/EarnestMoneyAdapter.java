package gnete.card.adapter;

import gnete.card.dao.RiskMarginRegDAO;
import gnete.card.entity.RiskMarginReg;
import gnete.card.entity.state.CheckState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: EarnestMoneyAdapter.java
 * 
 * @description: 风险准备金申请流程审核适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-10
 */
@Repository
public class EarnestMoneyAdapter implements WorkflowAdapter {

	@Autowired
	private RiskMarginRegDAO riskMarginRegDAO;

	static Logger logger = Logger.getLogger(EarnestMoneyAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {

		logger.debug("审批通过的相关处理。");
		RiskMarginReg riskMarginReg = (RiskMarginReg) riskMarginRegDAO
				.findByPkWithLock(NumberUtils.toLong(refid));
		riskMarginReg.setStatus(CheckState.PASSED.getValue());
		this.riskMarginRegDAO.update(riskMarginReg);
		
		// 组建档报文，然后发到后台。根据后台处理结果更新发卡机构准备金参数表里的记录
		//MsgSender.sendMsg(MsgType., NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return this.riskMarginRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_CARD_RISK_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {

		logger.debug("审批不通过，回退的相关处理。");
		RiskMarginReg riskMarginReg = (RiskMarginReg) this.riskMarginRegDAO
				.findByPkWithLock(NumberUtils.toLong(refid));

		// 更新状态为审核失败
		riskMarginReg.setStatus(CheckState.FAILED.getValue());
		this.riskMarginRegDAO.update(riskMarginReg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {

		logger.debug("下发");
	}

}
