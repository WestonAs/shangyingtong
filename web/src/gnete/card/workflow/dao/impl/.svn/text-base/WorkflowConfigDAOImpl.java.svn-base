package gnete.card.workflow.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowConfigDAO;

@Repository
public class WorkflowConfigDAOImpl extends BaseDAOIbatisImpl implements WorkflowConfigDAO {

    public String getNamespace() {
        return "WorkflowConfig";
    }
    
    public int deleteByWorkflowId(String workflowId) {
    	return this.delete("deleteByWorkflowId", workflowId);
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
}