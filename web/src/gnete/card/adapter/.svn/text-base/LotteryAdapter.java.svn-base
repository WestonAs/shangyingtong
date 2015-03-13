package gnete.card.adapter;

import gnete.card.dao.DrawDefDAO;
import gnete.card.entity.DrawDef;
import gnete.card.entity.state.DrawDefineState;
import gnete.card.entity.type.DrawType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: LotteryAdapter.java
 * 
 * @description:
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-8
 */
@Repository
public class LotteryAdapter implements WorkflowAdapter {
	
	@Autowired
	private DrawDefDAO drawDefDAO;

	static Logger logger = Logger.getLogger(LotteryAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("审批通过的相关处理。");
		DrawDef drawDef = (DrawDef) drawDefDAO.findByPkWithLock(refid);
		if(DrawType.BRUSH_ISIN.getValue().equals(drawDef.getDrawType())){//即刷即中:审核通过直接变为已抽奖
			drawDef.setStatus(DrawDefineState.DRAWED_Y.getValue());
		}else{
		    drawDef.setStatus(DrawDefineState.PASSED.getValue());
		}
		drawDefDAO.update(drawDef);
	}

	public Object getJobslip(String refid) {
		return drawDefDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.DRAW_DEFINE;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		DrawDef drawDef = (DrawDef) drawDefDAO.findByPkWithLock(refid);
		drawDef.setStatus(DrawDefineState.FAILED.getValue());
		drawDefDAO.update(drawDef);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("下发");
	}

}
