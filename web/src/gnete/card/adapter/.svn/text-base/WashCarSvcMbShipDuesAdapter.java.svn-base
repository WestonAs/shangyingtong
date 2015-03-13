package gnete.card.adapter;

import gnete.card.dao.WashCarSvcMemberShipDAO;
import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.WashCarSvcMbShipDues;
import gnete.card.entity.state.WashCarCheckState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @description: 缴交会费 适配器
 */
@Repository
public class WashCarSvcMbShipDuesAdapter implements WorkflowAdapter {
	
	@Autowired
	private WashCarSvcMemberShipDAO washCarSvcMemberShipDAO;

	static Logger						logger	= LoggerFactory.getLogger(WashCarSvcMbShipDuesAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("缴交会费 审批通过 ...");

		WashCarSvcMbShipDues reg = (WashCarSvcMbShipDues) this.washCarSvcMemberShipDAO
				.findByPk(NumberUtils.toLong(refid));

		reg.setCheckStatus(WashCarCheckState.PASSED.getValue());
		reg.setUpdateUser(userId);
		reg.setUpdateTime(new Date());

		washCarSvcMemberShipDAO.update(reg);
		
	}

	public Object getJobslip(String refid) {
		return this.washCarSvcMemberShipDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WASH_CAR_SVC_MB_SHIP_DUES;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId) throws BizException {
		logger.debug("缴交会费 审批不通过，回退的相关处理 ...");

		WashCarSvcMbShipDues reg = (WashCarSvcMbShipDues) this.washCarSvcMemberShipDAO
				.findByPk(NumberUtils.toLong(refid));

		reg.setCheckStatus(WashCarCheckState.FALURE.getValue());
		reg.setUpdateUser(userId);
		reg.setUpdateTime(new Date());

		washCarSvcMemberShipDAO.update(reg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId) throws BizException {
		logger.debug("下发");
	}
}
