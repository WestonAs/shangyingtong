package gnete.card.adapter;

import java.util.Date;

import gnete.card.dao.CardBinPreRegDAO;
import gnete.card.entity.CardBinPreReg;
import gnete.card.entity.state.CheckState;
import gnete.card.service.CardBinService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardBinPrexRegAdapter.java
 *
 * @description: 卡BIN前三位管理审核流程适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-10-13
 */
@Repository
public class CardBinPrexRegAdapter implements WorkflowAdapter {
	
	@Autowired
	private CardBinService cardBinService;
	@Autowired
	private CardBinPreRegDAO cardBinPreRegDAO;
	
	private static Logger logger = Logger.getLogger(CardBinPrexRegAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("新增卡BIN前三位审核通过的相关处理。");
		CardBinPreReg cardBinPreReg = (CardBinPreReg) this.cardBinPreRegDAO.findByPkWithLock(refid);
		cardBinService.checkCardBinPrexReg(cardBinPreReg, userId);
	}

	public Object getJobslip(String refid) {
		return this.cardBinPreRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_CARD_BIN_PREX_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("新增卡BIN前三位审核不通过，回退的相关处理。");
		CardBinPreReg cardBinPreReg = (CardBinPreReg) this.cardBinPreRegDAO.findByPkWithLock(refid);
		cardBinPreReg.setUpdateBy(userId);
		cardBinPreReg.setUpdateTime(new Date());
		cardBinPreReg.setStatus(CheckState.FAILED.getValue());
		
		this.cardBinPreRegDAO.update(cardBinPreReg);

	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发");
	}

}
