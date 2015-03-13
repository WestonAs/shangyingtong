package gnete.card.workflow.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.impl.BaseDAOIbatisImpl;
import gnete.card.workflow.dao.WorkflowPrivilegeDAO;
import gnete.card.workflow.entity.WorkflowPrivilegeKey;

@Repository
public class WorkflowPrivilegeDAOImpl extends BaseDAOIbatisImpl implements WorkflowPrivilegeDAO {

    public String getNamespace() {
        return "WorkflowPrivilege";
    }
    
    public List<WorkflowPrivilegeKey> findByRole(String roleId) {
    	return this.queryForList("findByRole", roleId);
    }
    
    public int deleteByRoleId(String roleId) {
    	return this.delete("deleteByRoleId", roleId);
    }
}