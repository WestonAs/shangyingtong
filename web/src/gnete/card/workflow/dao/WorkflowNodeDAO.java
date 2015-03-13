package gnete.card.workflow.dao;

import java.util.List;

import gnete.card.dao.BaseDAO;
import gnete.card.workflow.entity.WorkflowNode;

public interface WorkflowNodeDAO extends BaseDAO {

	/**
	 * 根据工作流ID删除
	 * @param workflowId
	 * @return
	 */
	int deleteByWorkflowId(String workflowId);

	/**
	 * 根据流程ID和机构ID删除节点
	 * @param workflowId
	 * @param isBranch
	 * @param refId
	 * @return
	 */
	int deleteByWorkflowIdAndBranch(String workflowId, String isBranch,
			String refId);

	/**
	 * 查找默认流程节点
	 * @param workflowId
	 * @return
	 */
	List<WorkflowNode> findDefaultNode(String workflowId);
}