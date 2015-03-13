package gnete.card.workflow.service;

import java.util.List;

import gnete.card.entity.UserInfo;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.entity.WorkflowConfig;
import gnete.card.workflow.entity.WorkflowConfigKey;
import gnete.card.workflow.entity.WorkflowField;
import gnete.card.workflow.entity.WorkflowFieldKey;
import gnete.card.workflow.entity.WorkflowHis;
import gnete.etc.BizException;

/**
 * 工作流应用接口
 * @author aps-lih
 */
public interface WorkflowService {

	/**
	 * 单个启动流程
	 * @param jobslip
	 * @param adapterClass
	 * @param refid
	 * @param refid
	 */
	public void startFlow(Object jobslip, String adapterClass, String refid, UserInfo userInfo) throws Exception;
	
	/**
	 * 批量启动流程
	 * @param jobslip
	 * @param adapterClass
	 * @param ids
	 * @param userInfo
	 */
	public void startFlow(Object jobslip, String adapterClass, String[] ids, UserInfo userInfo) throws Exception;
	
	/**
	 * 删除一项流程数据
	 * @param workflowId
	 * @param refId
	 * @throws Exception
	 */
	public void deleteFlow(String workflowId, String refId) throws Exception;
	
	/**
	 * 得到我的待审批ID
	 * @param userId
	 * @return
	 */
	public String[] getMyJob(String workflowId, UserInfo userInfo);
	
	/**
	 * 查找流程审批记录
	 * @param userId
	 * @return
	 */
	public List<WorkflowHis> findFlowHis(String workflowId, String refid);

	/**
	 * 添加一个流程定义
	 * @param workflow
	 * @param names 
	 * @param isReg 
	 */
	public void addWorkflow(Workflow workflow, String[] names, String isReg) throws BizException;

	/**
	 * 修改一个流程定义
	 * @param workflow
	 * @param roleIdArray 
	 * @param roleTypeArray 
	 */
	public void modifyWorkflow(Workflow workflow, String[] names, String isReg) throws BizException;

	/**
	 * 删除一个流程定义
	 * @param workflowId
	 */
	public void deleteWorkflow(String workflowId) throws BizException;

	/**
	 * 添加一个工单属性
	 * @param workflowField
	 */
	public void addWorkflowField(WorkflowField workflowField) throws BizException;

	/**
	 * 修改一个工单属性
	 * @param workflowField
	 */
	public void modifyWorkflowField(WorkflowField workflowField) throws BizException;

	/**
	 * 删除一个工单属性
	 * @param key
	 */
	public void deleteWorkflowField(WorkflowFieldKey key) throws BizException;

	/**
	 * 添加流程配置
	 * @param workflowConfig
	 * @param userId 
	 */
	public void addWorkflowConfig(WorkflowConfig workflowConfig, String userId) throws BizException;

	/**
	 * 修改流程配置
	 * @param workflowConfig
	 * @param sessionUserCode
	 */
	public void modifyWorkflowConfig(WorkflowConfig workflowConfig,
			String sessionUserCode) throws BizException;
	
	/**
	 * 删除流程配置
	 * @param key
	 */
	public void deleteWorkflowConfig(WorkflowConfigKey key) throws BizException;
	
}
