package gnete.card.adapter;

import gnete.card.dao.PromtDefDAO;
import gnete.card.dao.PromtRuleDefDAO;
import gnete.card.dao.PromtScopeDAO;
import gnete.card.entity.PromtDef;
import gnete.card.entity.PromtRuleDef;
import gnete.card.entity.PromtScope;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: ProtocolAdapter.java
 * 
 * @description: 协议积分活动审核流程适配器
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-6
 */
@Repository
public class ProtocolAdapter implements WorkflowAdapter {

	@Autowired
	private PromtRuleDefDAO promtRuleDefDAO;
	@Autowired
	private PromtDefDAO promtDefDAO;
	@Autowired
	private PromtScopeDAO promtScopeDAO;

	static Logger logger = Logger.getLogger(ProtocolAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("协议积分活动审批通过的相关处理。");
		PromtDef def = (PromtDef) promtDefDAO.findByPkWithLock(refid);
		def.setStatus(PromotionsRuleState.EFFECT.getValue());
		promtDefDAO.update(def);

		List<PromtScope> scopeList = promtScopeDAO.findByPromtId(refid);
		if (CollectionUtils.isNotEmpty(scopeList)) {
			for (PromtScope scope : scopeList) {
				scope.setStatus(def.getStatus());

				promtScopeDAO.update(scope);
			}
		}

		List<PromtRuleDef> ruleList = promtRuleDefDAO.findByPromtId(refid);
		if (CollectionUtils.isNotEmpty(ruleList)) {
			for (PromtRuleDef rule : ruleList) {
				rule.setRuleStatus(def.getStatus());

				promtRuleDefDAO.update(rule);
			}
		}
	}

	public Object getJobslip(String refid) {
		return promtDefDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.PROTOCOL_DEFINE;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("协议积分活动审批不通过，回退的相关处理。");
		PromtDef def = (PromtDef) promtDefDAO.findByPkWithLock(refid);
		def.setStatus(PromotionsRuleState.CREATE.getValue());
		promtDefDAO.update(def);

		List<PromtScope> scopeList = promtScopeDAO.findByPromtId(refid);
		if (CollectionUtils.isNotEmpty(scopeList)) {
			for (PromtScope scope : scopeList) {
				scope.setStatus(def.getStatus());

				promtScopeDAO.update(scope);
			}
		}

		List<PromtRuleDef> ruleList = promtRuleDefDAO.findByPromtId(refid);
		if (CollectionUtils.isNotEmpty(ruleList)) {
			for (PromtRuleDef rule : ruleList) {
				rule.setRuleStatus(def.getStatus());

				promtRuleDefDAO.update(rule);
			}
		}
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
