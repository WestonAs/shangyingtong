package gnete.card.adapter;

import java.util.Date;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.CouponAwardRegDAO;
import gnete.card.entity.CouponAwardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * @File: CouponAwardRegAdapter.java
 * 
 * @description: 赠券赠送申请流程审核适配器
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-12-22
 */
@Repository
public class CouponAwardRegAdapter implements WorkflowAdapter {

	@Autowired
	private CouponAwardRegDAO couponAwardRegDAO;

	static Logger logger = Logger.getLogger(CouponAwardRegAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");

		CouponAwardReg couponAwardReg = (CouponAwardReg) this.couponAwardRegDAO.findByPk(NumberUtils.toLong(refid));
		couponAwardReg.setStatus(RegisterState.PASSED.getValue());
		couponAwardReg.setUpdateBy(userId);
		couponAwardReg.setUpdateTime(new Date());
		
		this.couponAwardRegDAO.update(couponAwardReg);
		
		// 发补帐命令2009
		MsgSender.sendMsg(MsgType.COUPON_AWARD, NumberUtils.toLong(refid),
				userId);
	}

	public Object getJobslip(String refid) {
		return this.couponAwardRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_COUPON_AWARD_REG;
	}

	public void postBackward(String refid, Integer nodeId/* 没用到 */,
			String param, String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");

		// 更新状态为审核失败
		CouponAwardReg couponAwardReg = (CouponAwardReg) this.couponAwardRegDAO.findByPk(NumberUtils.toLong(refid));
		couponAwardReg.setStatus(RegisterState.FALURE.getValue());
		couponAwardReg.setUpdateBy(userId);
		couponAwardReg.setUpdateTime(new Date());
		this.couponAwardRegDAO.update(couponAwardReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
