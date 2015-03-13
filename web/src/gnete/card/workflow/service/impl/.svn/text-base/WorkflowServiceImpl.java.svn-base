package gnete.card.workflow.service.impl;

import flink.util.SpringContext;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.card.workflow.dao.WorkflowConfigDAO;
import gnete.card.workflow.dao.WorkflowDAO;
import gnete.card.workflow.dao.WorkflowFieldDAO;
import gnete.card.workflow.dao.WorkflowMultitransDAO;
import gnete.card.workflow.dao.WorkflowNodeConditionDAO;
import gnete.card.workflow.dao.WorkflowNodeDAO;
import gnete.card.workflow.dao.WorkflowPosDAO;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.entity.WorkflowConfig;
import gnete.card.workflow.entity.WorkflowConfigKey;
import gnete.card.workflow.entity.WorkflowField;
import gnete.card.workflow.entity.WorkflowFieldKey;
import gnete.card.workflow.entity.WorkflowHis;
import gnete.card.workflow.entity.WorkflowNode;
import gnete.card.workflow.entity.WorkflowPos;
import gnete.card.workflow.entity.state.WorkflowState;
import gnete.card.workflow.entity.type.WorkflowNodeType;
import gnete.card.workflow.service.WorkflowEngine;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {
	
	@Autowired
	private WorkflowEngine workflowEngine;
	
	@Autowired
	private WorkflowDAO workflowDAO;
	
	@Autowired
	private WorkflowPosDAO workflowPosDAO;

	@Autowired
	private WorkflowConfigDAO workflowConfigDAO;

	@Autowired
	private WorkflowNodeDAO workflowNodeDAO;

	@Autowired
	private WorkflowNodeConditionDAO workflowNodeConditionDAO;

	@Autowired
	private WorkflowMultitransDAO workflowMultitransDAO;

	@Autowired
	private WorkflowFieldDAO workflowFieldDAO;
	
	public void startFlow(Object jobslip, String adapterClass, String[] ids, UserInfo userInfo) throws Exception {
		this.workflowEngine.startFlow(jobslip, (WorkflowAdapter) SpringContext.getService(adapterClass), ids, userInfo);
	}
	
	public void startFlow(Object jobslip, String adapterClass, String refid, UserInfo userInfo) throws Exception {
		String[] ids = new String[]{refid};
		this.startFlow(jobslip, adapterClass, ids, userInfo);
	}
	
	public void deleteFlow(String workflowId, String refId) throws Exception {
		this.workflowEngine.deleteFlow(workflowId, refId);
	}

	public String[] getMyJob(String workflowId, UserInfo userInfo) {
		List<RoleInfo> roles = new ArrayList<RoleInfo>();
		roles.add(userInfo.getRole());
		return this.workflowEngine.getMyJob(workflowId, roles);
	}
	
	public List<WorkflowHis> findFlowHis(String workflowId, String refid) {
		return this.workflowEngine.findFlowHis(workflowId, refid);
	}
	
	public void addWorkflow(Workflow workflow, String[] names, String isReg) throws BizException {
		Assert.notNull(workflow, "流程信息不能为空");
		Assert.notTrue(this.workflowDAO.isExist(workflow.getWorkflowId()), "流程["+workflow.getWorkflowId()+"]已存在");
		
		for (int i = 0; i <= workflow.getDefaultLevel(); i++) {
			WorkflowNode workflowNode = new WorkflowNode();
			workflowNode.setWorkflowId(workflow.getWorkflowId());
			workflowNode.setIsBranch(Symbol.YES);
			workflowNode.setRefId(WorkflowConstants.DEFAULT_REF_ID);
			workflowNode.setNodeId(i);
			workflowNode.setNodeName(names[i]);
			workflowNode.setIsReg(isReg);
			if (i == 0) {
				workflowNode.setNodeType(WorkflowNodeType.ENTRY.getValue());
			} else {
				workflowNode.setNodeType(WorkflowNodeType.COMMON.getValue());
			}
			this.workflowNodeDAO.insert(workflowNode);
		}
		
		workflow.setCreateTime(new Date());
		this.workflowDAO.insert(workflow);
	}
	
	public void modifyWorkflow(Workflow workflow, String[] names, String isReg) throws BizException {
		Assert.notNull(workflow, "流程信息不能为空");

		Assert.notTrue(!this.workflowDAO.isExist(workflow.getWorkflowId()), "流程["+workflow.getWorkflowId()+"]不存在");
		
		// 正在运行中的流程禁止修改
//		List<WorkflowPos> list = this.workflowPosDAO.findByWorkflow(workflow.getWorkflowId(), WorkflowState.RUNNING.getValue());
//		Assert.notTrue(CollectionUtils.isNotEmpty(list), "流程[" + workflow.getWorkflowId() + "]存在运行中的流程，禁止修改流程信息!");
		
		this.workflowNodeDAO.deleteByWorkflowIdAndBranch(workflow.getWorkflowId(), Symbol.YES, WorkflowConstants.DEFAULT_REF_ID);
		
		for (int i = 0; i <= workflow.getDefaultLevel(); i++) {
			WorkflowNode workflowNode = new WorkflowNode();
			workflowNode.setWorkflowId(workflow.getWorkflowId());
			workflowNode.setIsBranch(Symbol.YES);
			workflowNode.setRefId(WorkflowConstants.DEFAULT_REF_ID);
			workflowNode.setNodeId(i);
			workflowNode.setNodeName(names[i]);
			workflowNode.setIsReg(isReg);
			if (i == 0) {
				workflowNode.setNodeType(WorkflowNodeType.ENTRY.getValue());
			} else {
				workflowNode.setNodeType(WorkflowNodeType.COMMON.getValue());
			}
			this.workflowNodeDAO.insert(workflowNode);
		}
		
		workflow.setCreateTime(new Date());
		this.workflowDAO.update(workflow);
	}
	
	public void deleteWorkflow(String workflowId) throws BizException {
		Assert.notEmpty(workflowId, "删除的流程编号不能为空");
		
		// 正在运行中的流程禁止删除
		List<WorkflowPos> list = this.workflowPosDAO.findByWorkflow(workflowId, WorkflowState.RUNNING.getValue());
		Assert.notTrue(CollectionUtils.isNotEmpty(list), "该流程存在正在运行中的流程，禁止删除");
		
		this.workflowFieldDAO.deleteByWorkflowId(workflowId);
		this.workflowNodeDAO.deleteByWorkflowId(workflowId);
		this.workflowNodeConditionDAO.deleteByWorkflowId(workflowId);
		this.workflowConfigDAO.deleteByWorkflowId(workflowId);
		this.workflowMultitransDAO.deleteByWorkflowId(workflowId);
		this.workflowDAO.delete(workflowId);
	}
	
	public void addWorkflowField(WorkflowField workflowField) throws BizException {
		Assert.notNull(workflowField, "流程工作单属性信息不能为空");
		
		WorkflowFieldKey key = new WorkflowFieldKey(workflowField.getField(), workflowField.getWorkflowId());
		Assert.notTrue(this.workflowFieldDAO.isExist(key), "流程工作单属性["+workflowField.getField()
				+ ", " + workflowField.getWorkflowId()+"]已存在");
		
		this.workflowFieldDAO.insert(workflowField);
	}
	
	public void modifyWorkflowField(WorkflowField workflowField) throws BizException {
		Assert.notNull(workflowField, "流程工作单属性信息不能为空");

		this.workflowFieldDAO.update(workflowField);
	}
	
	public void deleteWorkflowField(WorkflowFieldKey key) throws BizException {
		Assert.notNull(key, "流程工作单属性信息不能为空");
		
		this.workflowFieldDAO.delete(key);
	}
	
	public void addWorkflowConfig(WorkflowConfig workflowConfig, String userId) throws BizException {
		Assert.notNull(workflowConfig, "流程配置信息不能为空");
		
		WorkflowConfigKey key = new WorkflowConfigKey(workflowConfig.getIsBranch(), 
				workflowConfig.getRefId(), workflowConfig.getWorkflowId());
		Assert.notTrue(this.workflowConfigDAO.isExist(key), "流程配置信息["+workflowConfig.getRefId()+"]已存在");
		
		if (workflowConfig.getAuditLevel() == null) {
			workflowConfig.setAuditLevel(0);
		}
		
		for (int i = 0; i <= workflowConfig.getAuditLevel(); i++) {
			WorkflowNode workflowNode = new WorkflowNode();
			workflowNode.setWorkflowId(workflowConfig.getWorkflowId());
			workflowNode.setIsBranch(Symbol.YES);
			workflowNode.setRefId(workflowConfig.getRefId());
			workflowNode.setNodeId(i);
			if (i == 0) {
				workflowNode.setNodeType(WorkflowNodeType.ENTRY.getValue());
				workflowNode.setNodeName("录入节点");
			} else {
				workflowNode.setNodeType(WorkflowNodeType.COMMON.getValue());
				workflowNode.setNodeName(""+ i +"级审批节点");
			}
			this.workflowNodeDAO.insert(workflowNode);
		}
		workflowConfig.setUpdateTime(new Date());
		workflowConfig.setUpdateUser(userId);
		this.workflowConfigDAO.insert(workflowConfig);
	}
	
	public void modifyWorkflowConfig(WorkflowConfig workflowConfig,
			String userId) throws BizException {
		Assert.notNull(workflowConfig, "流程配置信息不能为空");
		
		WorkflowConfigKey key = new WorkflowConfigKey(workflowConfig.getIsBranch(), 
				workflowConfig.getRefId(), workflowConfig.getWorkflowId());
		Assert.notTrue(!this.workflowConfigDAO.isExist(key), "流程配置信息["+workflowConfig.getRefId()+"]不存在");
		
		this.workflowNodeDAO.deleteByWorkflowIdAndBranch(key.getWorkflowId(), 
				key.getIsBranch(), key.getRefId());
		this.workflowNodeConditionDAO.deleteByWorkflowIdAndBranch(key.getWorkflowId(), 
				key.getIsBranch(), key.getRefId());
		this.workflowMultitransDAO.deleteByWorkflowIdAndBranch(key.getWorkflowId(), 
				key.getIsBranch(), key.getRefId());
		
		for (int i = 0; i <= workflowConfig.getAuditLevel(); i++) {
			WorkflowNode workflowNode = new WorkflowNode();
			workflowNode.setWorkflowId(workflowConfig.getWorkflowId());
			workflowNode.setIsBranch(Symbol.YES);
			workflowNode.setRefId(workflowConfig.getRefId());
			workflowNode.setNodeId(i);
			if (i == 0) {
				workflowNode.setNodeType(WorkflowNodeType.ENTRY.getValue());
				workflowNode.setNodeName("录入节点");
			} else {
				workflowNode.setNodeType(WorkflowNodeType.COMMON.getValue());
				workflowNode.setNodeName(""+ i +"级审批节点");
			}
			this.workflowNodeDAO.insert(workflowNode);
		}
		workflowConfig.setUpdateTime(new Date());
		workflowConfig.setUpdateUser(userId);
		this.workflowConfigDAO.update(workflowConfig);
		
	}
	
	public void deleteWorkflowConfig(WorkflowConfigKey key) throws BizException {
		Assert.notNull(key, "流程配置信息不能为空");
		
		this.workflowConfigDAO.delete(key);
		this.workflowNodeDAO.deleteByWorkflowIdAndBranch(key.getWorkflowId(), 
				key.getIsBranch(), key.getRefId());
		this.workflowNodeConditionDAO.deleteByWorkflowIdAndBranch(key.getWorkflowId(), 
				key.getIsBranch(), key.getRefId());
		this.workflowMultitransDAO.deleteByWorkflowIdAndBranch(key.getWorkflowId(), 
				key.getIsBranch(), key.getRefId());
	}

}
