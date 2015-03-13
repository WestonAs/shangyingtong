package gnete.card.adapter;

import gnete.card.dao.ExternalCardImportRegDAO;
import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.ExternalCardImportService;
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
 * @description: 外部号码开卡流程适配器
 */
@Repository
public class ExternalNumMakeCardAdapter implements WorkflowAdapter {

	@Autowired
	private ExternalCardImportRegDAO	externalCardImportRegDAO;
	@Autowired
	private ExternalCardImportService	externalCardImportService;

	static Logger						logger	= LoggerFactory.getLogger(ExternalNumMakeCardAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("外部号码开卡 审批通过 ...");

		ExternalCardImportReg reg = (ExternalCardImportReg) this.externalCardImportRegDAO
				.findByPk(NumberUtils.toLong(refid));

		reg.setStatus(ExternalCardImportState.WAIT_DEAL.getValue());
		reg.setUpdateBy(userId);
		reg.setUpdateTime(new Date());

		externalCardImportRegDAO.update(reg);
		// 扣减风险保证金
		externalCardImportService.deductCardRisk(reg);
		
		// 发送命令
		MsgSender.sendMsg(MsgType.EXTERNAL_CARD_IMPORT, reg.getId(), reg.getUpdateBy());
	}

	public Object getJobslip(String refid) {
		return this.externalCardImportRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_EXTERNAL_NUM_MAKE_CARD;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId) throws BizException {
		logger.debug("外部号码开卡 审批不通过，回退的相关处理 ...");

		ExternalCardImportReg reg = (ExternalCardImportReg) this.externalCardImportRegDAO
				.findByPk(NumberUtils.toLong(refid));

		reg.setStatus(ExternalCardImportState.UNPASS.getValue());
		reg.setUpdateBy(userId);
		reg.setUpdateTime(new Date());

		externalCardImportRegDAO.update(reg);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId) throws BizException {
		logger.debug("下发");
	}
}
