package gnete.card.adapter;

import gnete.card.dao.CardInputDAO;
import gnete.card.entity.CardInput;
import gnete.card.entity.state.CheckState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: FinishedCardStockAdapter.java
 * 
 * @description: 成品卡入库审核适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
@Repository
public class FinishedCardStockAdapter implements WorkflowAdapter {

	@Autowired
	private CardInputDAO cardInputDAO;

	static Logger logger = Logger.getLogger(FinishedCardStockAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		CardInput cardInput = (CardInput) cardInputDAO
				.findByPkWithLock(NumberUtils.toLong(refid));
		Assert.notNull(cardInput, "要审核的成品卡入库对象不存在。");

		cardInput.setStatus(CheckState.PASSED.getValue());

		cardInput.setChkUser(userId);
		cardInput.setChkTime(new Date());
		cardInput.setUpdateBy(userId);
		cardInput.setUpdateTime(new Date());
		// 更新卡入库登记薄中的记录
		cardInputDAO.update(cardInput);

		// 发送报文到后台
		MsgSender.sendMsg(MsgType.FINISHED_CARD_INPUT, NumberUtils
				.toLong(refid), userId);

	}

	public Object getJobslip(String refid) {
		return cardInputDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.FINISHED_CARD_STOCK;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		CardInput cardInput = (CardInput) cardInputDAO
				.findByPkWithLock(NumberUtils.toLong(refid));

		cardInput.setStatus(CheckState.FAILED.getValue());
		cardInput.setChkUser(userId);
		cardInput.setChkTime(new Date());
		cardInput.setUpdateBy(userId);
		cardInput.setUpdateTime(new Date());
		cardInputDAO.update(cardInput);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发");
	}

}
