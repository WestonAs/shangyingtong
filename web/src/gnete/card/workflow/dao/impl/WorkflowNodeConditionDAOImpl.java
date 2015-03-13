package gnete.card.workflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowNodeConditionDAO;
import gnete.card.workflow.entity.WorkflowNodeCondition;

@Repository
public class WorkflowNodeConditionDAOImpl extends BaseDAOIbatisImpl implements WorkflowNodeConditionDAO {

    public String getNamespace() {
        return "WorkflowNodeCondition";
    }
    
    public List<WorkflowNodeCondition> findByNodeId(String workflowId,
			int nodeId, String isBranch, String refid) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("nodeId", nodeId);
    	params.put("isBranch", isBranch);
    	params.put("refid", refid);
    	
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