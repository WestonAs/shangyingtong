package gnete.card.workflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowPosDAO;
import gnete.card.workflow.entity.WorkflowPos;

@Repository
public class WorkflowPosDAOImpl extends BaseDAOIbatisImpl implements WorkflowPosDAO {

    public String getNamespace() {
        return "WorkflowPos";
    }
    
    public List<WorkflowPos> findByWorkflow(String workflowId, String state) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("state", state);
    	return this.queryForList("findByWorkflow", params);
    }
    
}