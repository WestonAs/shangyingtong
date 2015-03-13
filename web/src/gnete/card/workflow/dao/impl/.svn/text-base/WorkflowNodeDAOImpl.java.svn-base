package gnete.card.workflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowNodeDAO;
import gnete.card.workflow.entity.WorkflowNode;

@Repository
public class WorkflowNodeDAOImpl extends BaseDAOIbatisImpl implements WorkflowNodeDAO {

    public String getNamespace() {
        return "WorkflowNode";
    }
    
    public int deleteByWorkflowId(String workflowId) {
    	return this.delete("deleteByWorkflowId", workflowId);
    }
    
    public int deleteByWorkflowIdAndBranch(String workflowId, String isBranch,
    		String refId) {
    	Map<String , Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("isBranch", isBranch);
    	params.put("refId", refId);
    	return this.delete("deleteByWorkflowIdAndBranch", params);
    }
    
    public List<WorkflowNode> findDefaultNode(String workflowId) {
    	return this.queryForList("findDefaultNode", workflowId);
    }
    
}