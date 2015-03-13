package gnete.card.adapter;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.SignCustDAO;
import gnete.card.dao.SignRuleRegDAO;
import gnete.card.entity.SignCust;
import gnete.card.entity.SignRuleReg;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.RegisterState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * @File: SignRuleRegAdapter.java
 *
 * @description: 签单规则申请流程审核适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-7-30
 */
@Repository
public class SignRuleRegAdapter implements WorkflowAdapter {
	
	@Autowired
	private SignRuleRegDAO signRuleRegDAO;
	
	@Autowired
	private SignCustDAO signCustDAO;
	
	static Logger logger = Logger.getLogger(SignRuleRegAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		
		SignRuleReg signRuleReg = (SignRuleReg)this.signRuleRegDAO.findByPk(NumberUtils.toLong(refid));
		signRuleReg.setStatus(RegisterState.PASSED.getValue());
		signRuleReg.setUpdateUser(userId);
		signRuleReg.setUpdateTime(new Date());
		this.signRuleRegDAO.update(signRuleReg);	
		
		// 更改签单客户的状态为“已启用”
		SignCust signCust = (SignCust)this.signCustDAO.findByPk(signRuleReg.getSignCustId());
		signCust.setStatus(CommonState.USED.getValue());
		this.signCustDAO.update(signCust);
	}

	public Object getJobslip(String refid) {
		return this.signRuleRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_SIGN_RULE_REG;
	}

	public void postBackward(String refid, Integer nodeId/*没用到*/, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
//		// 更新状态为审核失败
//		SignRuleReg signRuleReg = (SignRuleReg) this.signRuleRegDAO.findByPk(NumberUtils.toLong(refid));
//		signRuleReg.setStatus(RegisterState.FALURE.getValue());
//		signRuleReg.setUpdateUser(userId);
//		signRuleReg.setUpdateTime(new Date());
//		this.signRuleRegDAO.update(signRuleReg);
		
		// 删除签单规则申请
		this.signRuleRegDAO.delete(NumberUtils.toLong(refid));				
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
