package gnete.card.workflow.web;

import flink.util.LogUtils;
import flink.util.SpringContext;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.card.workflow.dao.WorkflowDAO;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.service.WorkflowEngine;

import org.springframework.beans.factory.annotation.Autowired;

public class WorkflowAction extends BaseAction  {
	
	private String adapter;
	
	private boolean pass;
	
	private String ids;
	
	private String desc;
	
	private String returnUrl;
	
	private String param;
	
	@Autowired
	private WorkflowEngine workflowEngine;

	@Autowired
	private WorkflowDAO workflowDAO;
	
	@Override
	public String execute() throws Exception {
		WorkflowAdapter workflowAdapter = (WorkflowAdapter) SpringContext.getService(adapter);
		this.workflowEngine.doFlow(workflowAdapter, ids, pass, desc, param, this.getSessionUser());
		
		Workflow workflow = (Workflow) this.workflowDAO.findByPk(workflowAdapter.getWorkflowId());

		String msg = LogUtils.r("{0}编号[{1}]", workflow.getWorkflowName(), ids);
		if (pass) {
			msg += "处理成功，处理结果审核通过";
		} else {
			msg += "处理成功，处理结果审核不通过";
		}
		addActionMessage(this.returnUrl, msg);
		this.log(msg, UserLogType.CHECK, workflow.getAuditLimit());
		return SUCCESS;
	}

	public String getAdapter() {
		return adapter;
	}

	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
