package gnete.card.adapter;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.WhiteCardInputDAO;
import gnete.card.entity.WhiteCardInput;
import gnete.card.entity.state.CheckState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

/**
 * @File: WhiteCardStockAdapter.java
 * 
 * @description: 白卡入库审核流程适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
@Repository
public class WhiteCardStockAdapter implements WorkflowAdapter {

	@Autowired
	private WhiteCardInputDAO whiteCardInputDAO;

	static Logger logger = Logger.getLogger(WhiteCardStockAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		WhiteCardInput whiteCardInput = (WhiteCardInput) whiteCardInputDAO
				.findByPkWithLock(NumberUtils.toLong(refid));

		Assert.notNull(whiteCardInput, "要审核的白卡入库登记对象不存在。");

		// 发送白卡入库报文到后台，成功则进行以下处理。

		whiteCardInput.setStatus(CheckState.PASSED.getValue());
		whiteCardInput.setChkUser(userId);
		whiteCardInput.setChkTime(new Date());
		whiteCardInput.setUpdateBy(userId);
		whiteCardInput.setUpdateTime(new Date());

		whiteCardInputDAO.update(whiteCardInput);
	}

	public Object getJobslip(String refid) {
		return whiteCardInputDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WHITE_CARD_STOCK;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		WhiteCardInput whiteCardInput = (WhiteCardInput) whiteCardInputDAO
				.findByPkWithLock(NumberUtils.toLong(refid));
		Assert.notNull(whiteCardInput, "要审核的白卡入库登记对象不存在。");

		whiteCardInput.setStatus(CheckState.FAILED.getValue());
		whiteCardInput.setChkUser(userId);
		whiteCardInput.setChkTime(new Date());
		whiteCardInput.setUpdateBy(userId);
		whiteCardInput.setUpdateTime(new Date());

		whiteCardInputDAO.update(whiteCardInput);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发");

	}

}
