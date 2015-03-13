package gnete.card.workflow.dao;

import java.util.List;

import gnete.card.dao.BaseDAO;
import gnete.card.workflow.entity.WorkflowNodeCondition;

public interface WorkflowNodeConditionDAO extends BaseDAO {

	/**
	 * 根据工作流ID和节点ID来查找当前节点设置的条件
	 * 
	 * @param workflowId
	 * @param currentNodeId
	 * @param refid 
	 * @param isBranch 
	 * @return
	 */
	List<WorkflowNodeCondition> findByNodeId(String workflowId,
			int currentNodeId, String isBranch, String refid);

	/**
	 * 根据工作流ID删除
	 * @param workflowId
	 * @return
	 */
	int deleteByWorkflowId(String workflowId);

	int deleteByWorkflowIdAndBranch(String workflowId, String isBranch,
			String refId);
}