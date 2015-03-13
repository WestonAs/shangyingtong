package gnete.card.adapter;

import java.util.Date;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.state.BranchState;
import gnete.card.service.BranchService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: BranchAdapter.java
 *
 * @description: 机构审核流程适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-7-6
 */
@Repository
public class BranchAdapter implements WorkflowAdapter {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchService branchService;
	
	private static Logger logger = Logger.getLogger(BranchAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("新增机构审核通过的相关处理。");
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(refid);
		this.branchService.checkBranchPass(branchInfo, userId);
	}

	public Object getJobslip(String refid) {
		return this.branchInfoDAO.findBranchInfo(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_ADD_BRANCH;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("新增机构审核不通过，回退的相关处理。");
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(refid);
		branchInfo.setStatus(BranchState.UNPASS.getValue());
		branchInfo.setUpdateUser(userId);
		branchInfo.setUpdateTime(new Date());
		
		this.branchInfoDAO.update(branchInfo);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发");
	}

}
