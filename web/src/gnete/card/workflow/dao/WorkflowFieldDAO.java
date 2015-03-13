package gnete.card.workflow.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.dao.BaseDAO;

public interface WorkflowFieldDAO extends BaseDAO {

	/**
	 * 查找工作单
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findWorkflowField(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 根据流程ID删除
	 * @param workflowId
	 * @return
	 */
	int deleteByWorkflowId(String workflowId);
}