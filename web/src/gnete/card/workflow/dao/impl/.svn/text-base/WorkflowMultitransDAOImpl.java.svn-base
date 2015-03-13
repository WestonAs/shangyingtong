package gnete.card.workflow.dao.impl;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowMultitransDAO;
import gnete.card.workflow.entity.WorkflowMultitrans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WorkflowMultitransDAOImpl extends BaseDAOIbatisImpl implements WorkflowMultitransDAO {

    public String getNamespace() {
        return "WorkflowMultitrans";
    }
    
    public List<WorkflowMultitrans> findByNodeId(String workflowId, Integer nodeId, 
    		String isBranch, String refId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("nodeId", nodeId);
    	params.put("isBranch", isBranch);
    	params.put("refId", refId);
    	
    	return this.queryForList("findByNodeId", params);
    }
    
    public int deleteByWorkflowId(String workflowId) {
    	return this.delete("deleteByWorkflowId", workflowId);
    }
    
    public int deleteByWorkflowIdAndBranch(String workflowId, String isBranch,
    		String refId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("isBranch", isBranch);
    	params.put("refId", refId);
    	return this.delete("deleteByWorkflowIdAndBranch", params);
    }
}