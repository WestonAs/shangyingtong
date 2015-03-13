package gnete.card.workflow.dao;

import flink.util.Paginater;
import gnete.card.dao.BaseDAO;

import java.util.Map;

public interface WorkflowDAO extends BaseDAO {

	/**
	 * 查询流程信息
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findWorkflow(Map<String, Object> params, int pageNumber,
			int pageSize);

}