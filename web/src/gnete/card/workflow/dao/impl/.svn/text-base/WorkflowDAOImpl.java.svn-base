package gnete.card.workflow.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowDAO;

@Repository
public class WorkflowDAOImpl extends BaseDAOIbatisImpl implements WorkflowDAO {

    public String getNamespace() {
        return "Workflow";
    }
    
    public Paginater findWorkflow(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findWorkflow", params, pageNumber, pageSize);
    }
}