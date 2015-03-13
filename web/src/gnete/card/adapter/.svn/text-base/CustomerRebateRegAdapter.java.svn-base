package gnete.card.adapter;

import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CustomerRebateRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.state.CardCustomerState;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CustomerRebateRegAdapter.java
 *
 * @description: 客户返利申请流程审核适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-7-30
 */
@Repository
public class CustomerRebateRegAdapter implements WorkflowAdapter {
	
	@Autowired
	private CustomerRebateRegDAO customerRebateRegDAO;
	
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	
		static Logger logger = Logger.getLogger(CustomerRebateRegAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		
		// 更改审核状态为通过
		CustomerRebateReg customerRebateReg = (CustomerRebateReg)this.customerRebateRegDAO.findByPk(NumberUtils.toLong(refid));
		Assert.notNull(customerRebateReg, "客户返利记录[" + refid + "]不能为空");
		customerRebateReg.setStatus(RegisterState.PASSED.getValue());
		customerRebateReg.setUpdateUser(userId);
		customerRebateReg.setUpdateTime(new Date());
		this.customerRebateRegDAO.update(customerRebateReg);		
		
		// 更改购卡客户的状态为‘已启用’
		CardCustomer cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(customerRebateReg.getCardCustomerId());
		Assert.notNull(cardCustomer, "购卡客户[" + customerRebateReg.getCardCustomerId() + "]不存在");
		cardCustomer.setStatus(CardCustomerState.USED.getValue());
		this.cardCustomerDAO.update(cardCustomer);
		
		// 更改售卡返利规则的状态为‘已启用’
		RebateRule rebateRule = (RebateRule)this.rebateRuleDAO.findByPk(customerRebateReg.getSaleRebateId());
		Assert.notNull(rebateRule, "返利规则[" + customerRebateReg.getSaleRebateId() + "]不存在");
		rebateRule.setStatus(RebateRuleState.USED.getValue());
		this.rebateRuleDAO.update(rebateRule);
		
		// 更改充值返利规则的状态为‘已启用’
		rebateRule = (RebateRule)this.rebateRuleDAO.findByPk(customerRebateReg.getDepositRebateId());
		Assert.notNull(rebateRule, "返利规则[" + customerRebateReg.getDepositRebateId() + "]不存在");
		rebateRule.setStatus(RebateRuleState.USED.getValue());
		this.rebateRuleDAO.update(rebateRule);
	}

	public Object getJobslip(String refid) {
		return this.customerRebateRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_CUSTOMER_REBATE_REG;
	}

	public void postBackward(String refid, Integer nodeId/*没用到*/, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
//		// 更新状态为审核失败
//		CustomerRebateReg customerRebateReg = (CustomerRebateReg) this.customerRebateRegDAO.findByPk(NumberUtils.toLong(refid));
//		customerRebateReg.setStatus(RegisterState.FALURE.getValue());
//		customerRebateReg.setUpdateUser(userId);
//		customerRebateReg.setUpdateTime(new Date());
//		this.customerRebateRegDAO.update(customerRebateReg);
		
		// 更新状态为审核失败，删除申请数据
		this.customerRebateRegDAO.delete(NumberUtils.toLong(refid));
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
