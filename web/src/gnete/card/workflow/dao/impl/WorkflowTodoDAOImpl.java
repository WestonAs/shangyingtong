package gnete.card.workflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.entity.RoleInfo;
import gnete.card.workflow.dao.WorkflowTodoDAO;
import gnete.card.workflow.entity.WorkflowTodo;
import gnete.card.workflow.entity.state.WorkflowTodoState;

@Repository
public class WorkflowTodoDAOImpl extends BaseDAOIbatisImpl implements WorkflowTodoDAO {

    public String getNamespace() {
        return "WorkflowTodo";
    }
    
    public List<WorkflowTodo> findTodoByRefid(String workflowId, String id,
    		List<RoleInfo> roles) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("id", id);
    	params.put("state", WorkflowTodoState.TODO.getValue());
    	params.put("roles", roles);
    	return this.queryForList("findTodoByRefid", params);
    }
    
    public List<WorkflowTodo> findTodoByWorkflowId(String workflowId,
    		List<RoleInfo> roles) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("state", WorkflowTodoState.TODO.getValue());
    	params.put("roles", roles);
    	return this.queryForList("findTodoByWorkflowId", params);
    }
    
    public boolean isNodeFinished(String workflowId, String id,
    		List<RoleInfo> roles) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("id", id);
    	params.put("state", WorkflowTodoState.TODO.getValue());
    	params.put("roles", roles);
    	// 当前返回结果是指：查询当前节点未处理的任务个数(除开传进的roles中的信息)
    	Integer result = (Integer) this.queryForObject("isNodeFinished", params);
    	return !(result == null ? false : (result > 0));
    }
    
    public int deleteByRefId(String workflowId, String refId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("workflowId", workflowId);
    	params.put("refId", refId);
    	return this.delete("deleteByRefId", params);
    }
}