package gnete.card.adapter;

import java.util.Date;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.PointChgRegDAO;
import gnete.card.entity.PointChgReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.Constants;

@Repository
public class PointChgRegAdapter implements WorkflowAdapter{

	@Autowired
	private PointChgRegDAO pointChgRegDAO;

	static Logger logger = Logger.getLogger(PointChgRegAdapter.class);
	
	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");

		PointChgReg pointChgReg = (PointChgReg) this.pointChgRegDAO.findByPk(NumberUtils.toLong(refid));
		pointChgReg.setStatus(RegisterState.PASSED.getValue());
		pointChgReg.setUpdateUser(userId);
		pointChgReg.setUpdateTime(new Date());
		this.pointChgRegDAO.update(pointChgReg);
		MsgSender.sendMsg(MsgType.POINT_CHG, NumberUtils.toLong(refid), userId);
	}

	public Object getJobslip(String refid) {
		return this.pointChgRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return Constants.WORKFLOW_POINT_CHG_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");

		// 更新状态为审核失败
		PointChgReg pointChgReg = (PointChgReg) this.pointChgRegDAO.findByPk(NumberUtils.toLong(refid));
		pointChgReg.setStatus(RegisterState.FALURE.getValue());
		pointChgReg.setUpdateUser(userId);
		pointChgReg.setUpdateTime(new Date());
		this.pointChgRegDAO.update(pointChgReg);
		
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
		
	}

}
