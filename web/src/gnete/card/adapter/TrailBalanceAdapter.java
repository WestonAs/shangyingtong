package gnete.card.adapter;

import java.util.Date;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.TrailBalanceRegDAO;
import gnete.card.entity.TrailBalanceReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * 试算平衡流程适配器
 * @author: aps-lib
 */
@Repository
public class TrailBalanceAdapter implements WorkflowAdapter{

	@Autowired
	private TrailBalanceRegDAO trailBalanceRegDAO;
	
	static Logger logger = Logger.getLogger(TrailBalanceAdapter.class);
	
	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		
		TrailBalanceReg reg = (TrailBalanceReg) this.trailBalanceRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		reg.setStatus(RegisterState.PASSED.getValue());
		reg.setUpdateBy(userId);
		reg.setUpdateTime(new Date());
		
		this.trailBalanceRegDAO.update(reg);
		
		// 发送报文消息
		MsgSender.sendMsg(MsgType.TRAIL_BALANCE, NumberUtils.toLong(refid), userId);
		
	}

	public Object getJobslip(String refid) {
		return this.trailBalanceRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_TRAIL_BALANCE_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
		TrailBalanceReg reg = (TrailBalanceReg) this.trailBalanceRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		reg.setStatus(RegisterState.FALURE.getValue());
		reg.setUpdateBy(userId);
		reg.setUpdateTime(new Date());
		
		this.trailBalanceRegDAO.update(reg);
		
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");		
	}

}
