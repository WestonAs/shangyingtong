package gnete.card.adapter;

import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.state.CheckState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardSubClassDefAdapter.java
 *
 * @description: 卡子类型申请流程审核适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-4-1
 */
@Repository
public class CardSubClassDefAdapter implements WorkflowAdapter {

	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;

	private static Logger logger = Logger.getLogger(CardSubClassDefAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("卡子类型审批通过的相关处理。");
		CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPkWithLock(refid);
		
		cardSubClass.setStatus(CheckState.PASSED.getValue());
		cardSubClass.setUpdateBy(userId);
		cardSubClass.setUpdateTime(new Date());
		
		this.cardSubClassDefDAO.update(cardSubClass);
	}

	public Object getJobslip(String refid) {
		return this.cardSubClassDefDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.CARD_SUB_CLASS_DEF;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("卡子类型审批不通过，回退的相关处理。");
		CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPkWithLock(refid);
		
		cardSubClass.setStatus(CheckState.FAILED.getValue());
		cardSubClass.setUpdateBy(userId);
		cardSubClass.setUpdateTime(new Date());
		
		this.cardSubClassDefDAO.update(cardSubClass);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
