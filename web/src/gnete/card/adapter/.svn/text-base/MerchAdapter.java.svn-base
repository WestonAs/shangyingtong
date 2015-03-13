package gnete.card.adapter;

import gnete.card.dao.MerchInfoRegDAO;
import gnete.card.entity.MerchInfoReg;
import gnete.card.entity.state.MerchState;
import gnete.card.service.MerchService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: MerchAdapter.java
 *
 * @description: 商户新增审核流程适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-7-6
 */
@Repository
public class MerchAdapter implements WorkflowAdapter {
	
	@Autowired
	private MerchService merchService;
	@Autowired
	private MerchInfoRegDAO merchInfoRegDAO;
	
	private static Logger logger = Logger.getLogger(MerchAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("新增商户审核通过的相关处理。");
		MerchInfoReg merchInfoReg = (MerchInfoReg) this.merchInfoRegDAO.findByPkWithLock(refid);
		this.merchService.checkMerchPass(merchInfoReg, userId);

	}

	public Object getJobslip(String refid) {
		return this.merchInfoRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_ADD_MERCH;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("新增商户审核不通过，回退的相关处理。");
		MerchInfoReg merchInfoReg = (MerchInfoReg) this.merchInfoRegDAO.findByPkWithLock(refid);
		merchInfoReg.setStatus(MerchState.UNPASS.getValue());
		merchInfoReg.setUpdateBy(userId);
		merchInfoReg.setUpdateTime(new Date());
		
		this.merchInfoRegDAO.update(merchInfoReg);

	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发");
	}

}
