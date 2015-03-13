package gnete.card.workflow.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowFieldDAO;

@Repository
public class WorkflowFieldDAOImpl extends BaseDAOIbatisImpl implements WorkflowFieldDAO {

    public String getNamespace() {
        return "WorkflowField";
    }
    
    public Paginater findWorkflowField(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findWorkflowField", params, pageNumber, pageSize);
    }
    
    public int deleteByWorkflowId(String workflowId) {
    	return this.delete("deleteByWorkflowId", workflowId);
    }
}