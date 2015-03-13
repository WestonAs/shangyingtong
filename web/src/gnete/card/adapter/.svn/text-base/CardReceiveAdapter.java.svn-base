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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardReceiveAdapter.java
 * 
 * @description: 领卡流程审核适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-24
 */
@Repository
public class CardReceiveAdapter implements WorkflowAdapter {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;

	static Logger logger = Logger.getLogger(CardReceiveAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		AppReg appReg = (AppReg) appRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		if (StringUtils.isNotBlank(param)) {
			// 修改，然后再审核
			String[] p = param.split(",");
			appReg.setCheckStrNo(p[0]);
			appReg.setCheckCardNum(NumberUtils.toInt(p[1]));
		} else {
			appReg.setCheckStrNo(appReg.getStrNo());
			appReg.setCheckCardNum(appReg.getCardNum());
		}
		appReg.setStatus(CheckState.PASSED.getValue());
		appReg.setUpdateBy(userId);
		appReg.setUpdateTime(new Date());
		
		appReg.setEndCardId(CardUtil.getEndCard(appReg.getCheckStrNo(), appReg.getCheckCardNum()));
		
		appRegDAO.update(appReg);
		MsgSender.sendMsg(MsgType.RECEIVE_CARD, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return appRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.CARD_RECEIVE;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		AppReg appReg = (AppReg) appRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		appReg.setStatus(CheckState.FAILED.getValue());
		appReg.setUpdateBy(userId);
		appReg.setUpdateTime(new Date());
		
		appRegDAO.update(appReg);

		String appOrgId = "";
		String appDate = "";
		// 根据登记簿中的卡库存信息表状态字段更新卡库存信息的领卡机构号，如果库存状态为“卡在库”，审核不通过则返还给发卡机构
		if (StringUtils.equals(appReg.getCardStockStatus(), CardStockState.RECEIVED.getValue())) {
			appOrgId = appReg.getCardSectorId();
			appDate = appReg.getAppDate();
		} else {
			appOrgId = "";
			appDate = "";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardStatus", appReg.getCardStockStatus());
		params.put("appOrgId", appOrgId);
		params.put("appDate", appDate);
		params.put("strCardId", appReg.getStrNo());
		params.put("endCardId", CardUtil.getMaxEndCardId(appReg.getStrNo(), appReg.getCardNum()));
		
		cardStockInfoDAO.updateStockBatch(params);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发的相关处理。");
	}

}
