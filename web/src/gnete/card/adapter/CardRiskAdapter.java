package gnete.card.adapter;

import gnete.card.dao.CardRiskRegDAO;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.service.CardRiskService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardRiskAdapter.java
 *
 * @description: 机构准备金调整申请流程审核适配器
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-30
 */
@Repository
public class CardRiskAdapter implements WorkflowAdapter {
	
	@Autowired
	private CardRiskRegDAO cardRiskRegDAO;
	
	@Autowired
	private CardRiskService cardRiskService;
	
	static Logger logger = Logger.getLogger(GiftAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		CardRiskReg cardRiskReg = (CardRiskReg) this.cardRiskRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		// 更新调整登记簿的状态、生效日期，更新余额表和变动表
		this.cardRiskService.activateCardRisk(cardRiskReg);
		
	}

	public Object getJobslip(String refid) {
		return this.cardRiskRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_CARD_RISK_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		CardRiskReg cardRiskReg = (CardRiskReg) this.cardRiskRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		
		// 更新状态为审核失败
		cardRiskReg.setStatus(RegisterState.FALURE.getValue());
		this.cardRiskRegDAO.update(cardRiskReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
