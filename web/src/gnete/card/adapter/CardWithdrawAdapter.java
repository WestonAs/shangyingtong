package gnete.card.adapter;

import gnete.card.dao.AppRegDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.entity.AppReg;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.CheckState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.util.CardUtil;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardReceiveAdapter.java
 * 
 * @description: 返库流程审核适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-24
 */
@Repository
public class CardWithdrawAdapter implements WorkflowAdapter {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;

	static Logger logger = Logger.getLogger(CardWithdrawAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("返库审批通过的相关处理。");
		AppReg appReg = (AppReg) appRegDAO.findByPkWithLock(NumberUtils
				.toLong(refid));
		appReg.setStatus(CheckState.PASSED.getValue());
		appReg.setUpdateBy(userId);
		appReg.setUpdateTime(new Date());
		appRegDAO.update(appReg);
		// 发送返库报文
		MsgSender.sendMsg(MsgType.WITHDRAW_CARD, NumberUtils.toLong(refid),
				userId);
	}

	public Object getJobslip(String refid) {
		return appRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.CARD_WITHDRAW;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("返库审批不通过，回退的相关处理。");
		AppReg appReg = (AppReg) appRegDAO.findByPkWithLock(NumberUtils
				.toLong(refid));
		appReg.setStatus(CheckState.FAILED.getValue());
		appReg.setUpdateBy(userId);
		appReg.setUpdateTime(new Date());
		appRegDAO.update(appReg);

		// 更新卡库存信息表
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		String[] cardNoArray = CardUtil.getCard(appReg.getStrNo(), appReg.getCardNum().intValue());
//		for (String cardId : cardNoArray) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("cardStatus", CardStockState.RECEIVED.getValue());
//			params.put("appOrgId", appReg.getAppOrgId());
//			params.put("appDate", appReg.getAppDate());
//			params.put("cardId", cardId);
//			
//			list.add(params);
//		}
//		
//		cardStockInfoDAO.updateStockBatch(list);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardStatus", CardStockState.RECEIVED.getValue());
		params.put("appOrgId", appReg.getAppOrgId());
		params.put("appDate", appReg.getAppDate());
		params.put("strCardId", appReg.getStrNo());
		params.put("endCardId", CardUtil.getMaxEndCardId(appReg.getStrNo(), appReg.getCardNum()));
		
		cardStockInfoDAO.updateStockBatch(params);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("返库流程下发的相关处理。");
	}

}
