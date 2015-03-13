package gnete.card.adapter;

import flink.util.DateUtil;
import gnete.card.dao.BranchSellRegDAO;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.service.CardRiskService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 配额流程适配器
 * @author: aps-lih
 */
@Repository
public class BranchSellAdapter implements WorkflowAdapter {

	@Autowired
	private BranchSellRegDAO branchSellRegDAO;
	
	@Autowired
	private CardRiskService cardRiskService;

	static Logger logger = Logger.getLogger(BranchSellAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		
		BranchSellReg branchSellReg = (BranchSellReg) this.branchSellRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		// 配额生效的相关处理
		this.cardRiskService.activateSell(branchSellReg);
	}

	public Object getJobslip(String refid) {
		return this.branchSellRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_BRANCH_SELL_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		
		BranchSellReg branchSellReg = (BranchSellReg) this.branchSellRegDAO
			.findByPkWithLock(NumberUtils.toLong(refid));
		
		branchSellReg.setStatus(RegisterState.FALURE.getValue());
		branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
		
		this.branchSellRegDAO.update(branchSellReg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
