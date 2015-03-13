package gnete.card.workflow.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.dao.BaseDAO;

public interface WorkflowConfigDAO extends BaseDAO {

	/**
	 * 根据工作流ID删除
	 * @param workflowId
	 * @return
	 */
	int deleteByWorkflowId(String workflowId);

	/**
	 * 查找流程配置信息
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
}