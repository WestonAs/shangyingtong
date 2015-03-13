package gnete.card.workflow.dao;

import java.util.List;

import gnete.card.dao.BaseDAO;
import gnete.card.workflow.entity.WorkflowHis;

public interface WorkflowHisDAO extends BaseDAO {

	/**
	 * 根据工单id查找处理记录
	 * @param workflowId
	 * @param refid
	 * @return
	 */
	List<WorkflowHis> findByRefId(String workflowId, String refid);

	int deleteByRefId(String workflowId, String refId);

	/**
	 * 根据工单ID和节点ID来查询
	 * @param workflowId
	 * @param refid
	 * @param nodeId
	 * @return
	 */
	WorkflowHis findByRefIdAndNodeId(String workflowId, String refid, int nodeId);
}