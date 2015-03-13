package gnete.card.adapter;

import gnete.card.dao.GreatDiscntProtclDefDAO;
import gnete.card.entity.GreatDiscntProtclDef;
import gnete.card.entity.state.GreatDiscntProtclDefState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 高级折扣
 * @Project: CardWeb
 * @File: GreatDiscntProtclDefAdapter.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-29下午5:35:35
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
@Repository
public class GreatDiscntProtclDefAdapter implements WorkflowAdapter {
	
	@Autowired
	private GreatDiscntProtclDefDAO greatDiscntProtclDefDAO;

	static Logger logger = Logger.getLogger(GreatDiscntProtclDefAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		GreatDiscntProtclDef greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPkWithLock(refid);
		greatDiscntProtclDef.setRuleStatus(GreatDiscntProtclDefState.EFFECT.getValue());
		greatDiscntProtclDefDAO.update(greatDiscntProtclDef);
	}

	public Object getJobslip(String refid) {
		return greatDiscntProtclDefDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_GREAT_DISCNT_PROTCL_DEF;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		GreatDiscntProtclDef greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPkWithLock(refid);
		greatDiscntProtclDef.setRuleStatus(GreatDiscntProtclDefState.FAIL.getValue());
		greatDiscntProtclDefDAO.update(greatDiscntProtclDef);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
