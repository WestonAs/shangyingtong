package gnete.card.adapter;

import gnete.card.dao.CardDeferRegDAO;
import gnete.card.entity.CardDeferReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardDefferAdapter.java
 *
 * @description: 卡延期流程审核适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-1 下午03:33:12
 */
@Repository
public class CardDefferAdapter implements WorkflowAdapter {

	@Autowired
	private CardDeferRegDAO cardDeferRegDAO;

	private static final Logger logger = Logger.getLogger(CardDefferAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("卡延期流程审核通过的相关处理。");
		CardDeferReg cardDeferReg = (CardDeferReg) this.cardDeferRegDAO.findByPk(NumberUtils.toLong(refid));
		
		cardDeferReg.setStatus(RegisterState.WAITEDEAL.getValue());
		cardDeferReg.setUpdateTime(new Date());
		cardDeferReg.setUpdateUser(userId);
		
		this.cardDeferRegDAO.update(cardDeferReg);
		// 发报文到后台
		MsgSender.sendMsg(MsgType.CARD_DEFER, cardDeferReg.getCardDeferId(), userId);
	}

	public Object getJobslip(String refid) {
		return cardDeferRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_CARD_DEFFER;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("卡延期流程审批不通过，回退的相关处理。");
		CardDeferReg cardDeferReg = (CardDeferReg) this.cardDeferRegDAO.findByPk(NumberUtils.toLong(refid));
		
		cardDeferReg.setStatus(RegisterState.FALURE.getValue());
		cardDeferReg.setUpdateTime(new Date());
		cardDeferReg.setUpdateUser(userId);
		
		this.cardDeferRegDAO.update(cardDeferReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
