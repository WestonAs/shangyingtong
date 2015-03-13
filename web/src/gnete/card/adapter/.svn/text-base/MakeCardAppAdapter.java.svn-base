package gnete.card.adapter;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import flink.util.DateUtil;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.MakeCardService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

/**
 * @File: MakeCardAppAdapter.java
 * 
 * @description: 制卡申请审核流程适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-10
 */
@Repository
public class MakeCardAppAdapter implements WorkflowAdapter {

	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private MakeCardService makeCardService;

	static Logger logger = Logger.getLogger(MakeCardAppAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		MakeCardApp makeCardApp = (MakeCardApp) makeCardAppDAO
				.findByPkWithLock(NumberUtils.toLong(refid));
		// 状态改为审核通过
		makeCardApp.setStatus(MakeCardAppState.CHECK_PASSED.getValue());

		makeCardApp.setChkUser(userId);
		makeCardApp.setChkDate(DateUtil.formatDate("yyyyMMdd"));
		makeCardApp.setUpdateBy(userId);
		makeCardApp.setUpdateTime(new Date());
		makeCardAppDAO.update(makeCardApp);

		// 组建档报文，然后发到后台。根据后台处理结果更新制卡申请表里的记录
		MsgSender.sendMsg(MsgType.CREATE_CARD_FILE, NumberUtils.toLong(refid), userId);
//		makeCardApp.setStatus(MakeCardAppState.CREATE_SUCCESS.getValue());
//		makeCardAppDAO.update(makeCardApp);
//		// 建档失败后要进行哪些处理？

	}

	public Object getJobslip(String refid) {
		return this.makeCardAppDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.MAKE_CARD_APP;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		MakeCardApp makeCardApp = (MakeCardApp) makeCardAppDAO
				.findByPkWithLock(NumberUtils.toLong(refid));
		if (nodeId == 1) {
			// 复核失败，状态变为撤销
			makeCardApp.setStatus(MakeCardAppState.CANCEL.getValue());
			makeCardApp.setCancelDate(DateUtil.formatDate("yyyyMMdd"));
		} else if (nodeId == 2) {
			// 审核不通过状态为审核失败
			makeCardApp.setStatus(MakeCardAppState.CHECK_FAILED.getValue());
		}
		makeCardApp.setChkUser(userId);
		makeCardApp.setChkDate(DateUtil.formatDate("yyyyMMdd"));
		makeCardApp.setUpdateBy(userId);
		makeCardApp.setUpdateTime(new Date());
		makeCardAppDAO.update(makeCardApp);
		makeCardService.releaseCardNoAssign(makeCardApp);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("复核通过，流程下发");
		if (nodeId == 2) {
			// 复核
			MakeCardApp makeCardApp = (MakeCardApp) makeCardAppDAO
					.findByPkWithLock(NumberUtils.toLong(refid));
			makeCardApp.setStatus(MakeCardAppState.WAITED_CHECK.getValue());

			makeCardApp.setUpdateBy(userId);
			makeCardApp.setUpdateTime(new Date());
			makeCardAppDAO.update(makeCardApp);
		}

	}

}
