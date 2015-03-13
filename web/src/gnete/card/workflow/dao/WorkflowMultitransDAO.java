package gnete.card.workflow.dao;

import gnete.card.dao.BaseDAO;
import gnete.card.workflow.entity.WorkflowMultitrans;

import java.util.List;

public interface WorkflowMultitransDAO extends BaseDAO {

	/**
	 * 根据工作流ID和节点ID来查找协同处理角色
	 * 
	 * @param workflowId
	 * @param nodeId
	 * @return
	 */
	List<WorkflowMultitrans> findByNodeId(String workflowId, Integer nodeId, String isBranch, String refId);

	/**
	 * 根据工作流ID删除
	 * @param workflowId
	 * @return
	 */
	int deleteByWorkflowId(String workflowId);

	int deleteByWorkflowIdAndBranch(String workflowId, String isBranch,
			String refId);
}