package gnete.card.workflow.dao;

import java.util.List;

import gnete.card.dao.BaseDAO;
import gnete.card.entity.RoleInfo;
import gnete.card.workflow.entity.WorkflowTodo;

public interface WorkflowTodoDAO extends BaseDAO {

	/**
	 * 查找我的某一个工作单的待办任务
	 * 
	 * @param workflowId
	 * @param id
	 * @param roles
	 * @return
	 */
	List<WorkflowTodo> findTodoByRefid(String workflowId, String id, List<RoleInfo> roles);
	
	/**
	 * 查找我某一个流程的待办任务
	 * @param workflowId
	 * @param roles
	 * @return
	 */
	List<WorkflowTodo> findTodoByWorkflowId(String workflowId, List<RoleInfo> roles);

	/**
	 * 判断当前流程是否结束
	 * 
	 * @param workflowId
	 * @param id
	 * @param roles 排除角色列表
	 * @return
	 */
	boolean isNodeFinished(String workflowId, String id, List<RoleInfo> roles);

	/**
	 * 根据流程和工单ID来删除待办信息
	 * @param workflowId
	 * @param refId
	 */
	int deleteByRefId(String workflowId, String refId);

	
}