package gnete.card.workflow.dao;

import java.util.List;

import gnete.card.dao.BaseDAO;
import gnete.card.workflow.entity.WorkflowPos;

public interface WorkflowPosDAO extends BaseDAO {

	/**
	 * 根据流程ID和运行状态来查找流程位置信息
	 * 
	 * @param workflowId
	 * @param state
	 * @return
	 */
	List<WorkflowPos> findByWorkflow(String workflowId, String state);
	
	
	
}