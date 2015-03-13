package gnete.card.dao.impl;

import java.util.List;

import gnete.card.dao.BranchPrivilegeDAO;

import org.springframework.stereotype.Repository;

@Repository
public class BranchPrivilegeDAOImpl extends BaseDAOIbatisImpl implements BranchPrivilegeDAO {

    public String getNamespace() {
        return "BranchPrivilege";
    }
    
    public int deleteByDeptId(String deptId) {
    	return this.delete("deleteByDeptId", deptId);
    }
    
    public List findByDept(String deptId) {
    	return this.queryForList("findByDept", deptId);
    }
    
}