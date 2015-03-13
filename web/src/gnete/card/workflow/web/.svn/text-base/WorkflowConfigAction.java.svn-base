package gnete.card.workflow.web;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.web.BaseAction;
import gnete.card.workflow.dao.WorkflowConfigDAO;
import gnete.card.workflow.dao.WorkflowDAO;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.entity.WorkflowConfig;
import gnete.card.workflow.entity.WorkflowConfigKey;
import gnete.etc.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkflowConfigAction extends BaseAction {
	
	@Autowired
	private WorkflowDAO workflowDAO;
	
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	@Autowired
	private WorkflowConfigDAO workflowConfigDAO;
	
	private WorkflowConfig workflowConfig;
	
	private Paginater page;
	
	private List<Workflow> workflowList;
	
	private String refId_sel;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (workflowConfig != null) {
			params.put("workflowId", workflowConfig.getWorkflowId());
			params.put("isBranch", workflowConfig.getIsBranch());
			params.put("refId", workflowConfig.getRefId());
		}
		this.page = this.workflowConfigDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	private void initPage() {
		this.workflowList = this.workflowDAO.findAll();
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		initPage();
		return ADD;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		initPage();
		this.workflowConfig = (WorkflowConfig) this.workflowConfigDAO.findByPk(
				new WorkflowConfigKey(workflowConfig.getIsBranch(), workflowConfig.getRefId(), workflowConfig.getWorkflowId()));
		
		if (Symbol.YES.equals(this.workflowConfig.getIsBranch())) {
			this.setRefId_sel(getBranchName(this.workflowConfig.getRefId()));
		} else {
			this.setRefId_sel(getMerchName(this.workflowConfig.getRefId()));
		}
		return MODIFY;
	}
	
	private String getBranchName(String branchCode){
		if (StringUtils.isEmpty(branchCode)) {return "";}
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		if (branch == null) {return "";}
		return branch.getBranchName();
	}
	private String getMerchName(String merchId){
		if (StringUtils.isEmpty(merchId)) {return "";}
		MerchInfo merch = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		if (merch == null) {return "";}
		return merch.getMerchName();
	}
	
	// 新增对象操作
	public String add() throws Exception {
		
		// 调用service业务接口
		this.workflowService.addWorkflowConfig(this.workflowConfig, this.getSessionUserCode());
		
		this.addActionMessage("/workflow/config/list.do", "添加流程定义["+this.workflowConfig.getRefId()+"]成功！");
		return SUCCESS;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		initPage();
		
		// 调用service业务接口
		this.workflowService.modifyWorkflowConfig(workflowConfig, this.getSessionUserCode());
		
		this.addActionMessage("/workflow/config/list.do", "修改流程定义["+this.workflowConfig.getRefId()+"]成功！");
		return SUCCESS;
	}
	
	// 删除对象操作
	public String delete() throws Exception {
		String isBranch = request.getParameter("isBranch");
		String refId = request.getParameter("refId");
		String workflowId = request.getParameter("workflowId");
		
		WorkflowConfigKey key = new WorkflowConfigKey(isBranch, refId, workflowId);

		// 调用service业务接口
		this.workflowService.deleteWorkflowConfig(key);
		
		this.addActionMessage("/workflow/config/list.do", "删除流程定义["+refId+"]成功！");
		return SUCCESS;
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WorkflowConfig getWorkflowConfig() {
		return workflowConfig;
	}

	public void setWorkflowConfig(WorkflowConfig workflowConfig) {
		this.workflowConfig = workflowConfig;
	}

	public List<Workflow> getWorkflowList() {
		return workflowList;
	}

	public void setWorkflowList(List<Workflow> workflowList) {
		this.workflowList = workflowList;
	}

	public String getRefId_sel() {
		return refId_sel;
	}

	public void setRefId_sel(String refId_sel) {
		this.refId_sel = refId_sel;
	}
}
