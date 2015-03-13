package gnete.card.adapter;

import gnete.card.dao.MembClassDefDAO;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.state.MemberCertifyState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardBinAdapter.java
 *
 * @description: 会员定义申请流程审核适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-16
 */

@Repository
public class MembDefAdapter implements WorkflowAdapter{

	@Autowired
	private MembClassDefDAO membClassDefDAO;
	
	static Logger logger = Logger.getLogger(MembDefAdapter.class);
	
	public void flowEnd(String refid, String param, String userId) throws BizException {
		
		logger.debug("审批通过的相关处理。");
		
		MembClassDef membClassDef = (MembClassDef) this.membClassDefDAO.findByPkWithLock(refid);
		membClassDef.setStatus(MemberCertifyState.PASSED.getValue());
		this.membClassDefDAO.update(membClassDef);
		
	}

	public Object getJobslip(String refid) {
		return this.membClassDefDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_MEMB_CLASS_DEF;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		
		logger.debug("审批不通过，回退的相关处理。");
		
		MembClassDef membClassDef = (MembClassDef) this.membClassDefDAO.findByPkWithLock(refid);
		
		// 更新状态为审核失败
		membClassDef.setStatus(MemberCertifyState.FALURE.getValue());
		this.membClassDefDAO.update(membClassDef);
		
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}


}
