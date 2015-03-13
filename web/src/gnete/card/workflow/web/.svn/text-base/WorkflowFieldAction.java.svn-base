package gnete.card.workflow.web;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.web.BaseAction;
import gnete.card.workflow.dao.WorkflowDAO;
import gnete.card.workflow.dao.WorkflowFieldDAO;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.entity.WorkflowField;
import gnete.card.workflow.entity.WorkflowFieldKey;
import gnete.card.workflow.service.WorkflowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class WorkflowFieldAction extends BaseAction {
	
	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private WorkflowDAO workflowDAO;
	
	@Autowired
	private WorkflowFieldDAO workflowFieldDAO;
	
	private WorkflowField workflowField;
	
	private Paginater page;
	
	private WorkflowFieldKey key;
	
	private List<Workflow> workflowList;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		initPage();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (workflowField != null) {
			params.put("workflowId", workflowField.getWorkflowId());
			params.put("fieldName", MatchMode.ANYWHERE.toMatchString(workflowField.getFieldName()));
		}
		this.page = this.workflowFieldDAO.findWorkflowField(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	private void initPage(){
		this.workflowList = this.workflowDAO.findAll();
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		initPage();
		return ADD;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.workflowField = (WorkflowField) this.workflowFieldDAO.findByPk(key);
		return MODIFY;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		
		// 调用service业务接口
		this.workflowService.addWorkflowField(this.workflowField);
		
		this.addActionMessage("/workflow/field/list.do", "添加流程工作单字段["+this.workflowField.getFieldName()+"]成功！");
		return SUCCESS;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		initPage();
		
		// 调用service业务接口
		this.workflowService.modifyWorkflowField(workflowField);
		
		this.addActionMessage("/workflow/field/list.do", "修改流程工作单字段["+this.workflowField.getFieldName()+"]成功！");
		return SUCCESS;
	}
	
	// 删除对象操作
	public String delete() throws Exception {

		// 调用service业务接口
		this.workflowService.deleteWorkflowField(key);
		
		this.addActionMessage("/workflow/field/list.do", "删除流程工作单字段["+key.getField()+"]成功！");
		return SUCCESS;
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WorkflowField getWorkflowField() {
		return workflowField;
	}

	public void setWorkflowField(WorkflowField workflowField) {
		this.workflowField = workflowField;
	}

	public List<Workflow> getWorkflowList() {
		return workflowList;
	}

	public void setWorkflowList(List<Workflow> workflowList) {
		this.workflowList = workflowList;
	}

	public WorkflowFieldKey getKey() {
		return key;
	}

	public void setKey(WorkflowFieldKey key) {
		this.key = key;
	}

}
