package gnete.card.workflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowHisDAO;
import gnete.card.workflow.entity.WorkflowHis;

@Repository
public class WorkflowHisDAOImpl extends BaseDAOIbatisImpl implements WorkflowHisDAO {

    public String getNamespace() {
        return "WorkflowHis";
    }
    
    public List<WorkflowHis> findByRefId(String workflowId, String refid) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("refid", refid);
    	return this.queryForList("findByRefId", params);
    }
    
    public int deleteByRefId(String workflowId, String refid) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("refid", refid);
    	return this.delete("deleteByRefId", params);
    }
    
    public WorkflowHis findByRefIdAndNodeId(String workflowId, String refid,
    		int nodeId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("refid", refid);
    	params.put("nodeId", nodeId);
    	return (WorkflowHis) this.queryForObject("findByRefIdAndNodeId", params);
    }
}