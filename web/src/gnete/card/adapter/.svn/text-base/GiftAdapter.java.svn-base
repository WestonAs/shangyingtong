package gnete.card.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.GiftDefDAO;
import gnete.card.entity.GiftDef;
import gnete.card.entity.state.GiftDefState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * @File: GiftAdapter.java
 *
 * @description: 礼品定义申请流程审核适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-2
 */
@Repository
public class GiftAdapter implements WorkflowAdapter {
	
	@Autowired
	private GiftDefDAO giftDefDAO;
	
	static Logger logger = Logger.getLogger(GiftAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		
		logger.debug("审批通过的相关处理。");
		
		GiftDef giftDef = (GiftDef) this.giftDefDAO.findByPkWithLock(refid);
		giftDef.setStatus(GiftDefState.PASSED.getValue());
		this.giftDefDAO.update(giftDef);
	}

	public Object getJobslip(String refid) {
		return this.giftDefDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_GIFT_DEF;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		
		logger.debug("审批不通过，回退的相关处理。");
		
		GiftDef giftDef = (GiftDef) this.giftDefDAO.findByPkWithLock(refid);
		
		// 更新状态为审核失败
		giftDef.setStatus(GiftDefState.FALURE.getValue());
		this.giftDefDAO.update(giftDef);
		
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
		
	}

}
