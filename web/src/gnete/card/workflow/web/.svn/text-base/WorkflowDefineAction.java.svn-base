package gnete.card.workflow.web;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.web.BaseAction;
import gnete.card.workflow.dao.WorkflowDAO;
import gnete.card.workflow.dao.WorkflowNodeDAO;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.entity.WorkflowNode;
import gnete.card.workflow.entity.type.WorkflowNodeFollow;
import gnete.card.workflow.service.WorkflowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class WorkflowDefineAction extends BaseAction {
	
	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private WorkflowDAO workflowDAO;
	
	@Autowired
	private WorkflowNodeDAO workflowNodeDAO;
	
	private Workflow workflow;
	
	private Paginater page;
	
	private String workflowId;

	private List<WorkflowNode> defaultNodeList;
	
	private String[] names;

	private List nodeFollowList;
	
	private String isReg;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (workflow != null) {
			params.put("workflowId", workflow.getWorkflowId());
			params.put("workflowName", MatchMode.ANYWHERE.toMatchString(workflow.getWorkflowName()));
		}
		this.page = this.workflowDAO.findWorkflow(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.workflow = (Workflow) this.workflowDAO.findByPk(this.workflow.getWorkflowId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		nodeFollowList = WorkflowNodeFollow.getAll();
		return ADD;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		nodeFollowList = WorkflowNodeFollow.getAll();
		this.workflow = (Workflow) this.workflowDAO.findByPk(this.workflow.getWorkflowId());
		this.defaultNodeList = this.workflowNodeDAO.findDefaultNode(this.workflow.getWorkflowId());
		
		this.isReg = defaultNodeList.get(0).getIsReg();
		return MODIFY;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		// 调用service业务接口
		this.workflowService.addWorkflow(this.workflow, this.names, isReg);
		
		this.addActionMessage("/workflow/define/list.do", "添加流程["+this.workflow.getWorkflowId()+"]成功！");
		return SUCCESS;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		
		// 调用service业务接口
		this.workflowService.modifyWorkflow(this.workflow, this.names, isReg);
		
		this.addActionMessage("/workflow/define/list.do", "修改流程["+this.workflow.getWorkflowId()+"]成功！");
		return SUCCESS;
	}
	
	// 删除对象操作
	public String delete() throws Exception {

		// 调用service业务接口
		this.workflowService.deleteWorkflow(this.getWorkflowId());
		
		this.addActionMessage("/workflow/define/list.do", "删除流程["+this.getWorkflowId()+"]成功！");
		return SUCCESS;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public List<WorkflowNode> getDefaultNodeList() {
		return defaultNodeList;
	}

	public void setDefaultNodeList(List<WorkflowNode> defaultNodeList) {
		this.defaultNodeList = defaultNodeList;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public List getNodeFollowList() {
		return nodeFollowList;
	}

	public void setNodeFollowList(List nodeFollowList) {
		this.nodeFollowList = nodeFollowList;
	}

	public String getIsReg() {
		return isReg;
	}

	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}

}
