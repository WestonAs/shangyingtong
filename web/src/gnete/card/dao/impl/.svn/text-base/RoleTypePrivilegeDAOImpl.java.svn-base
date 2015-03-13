package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.RoleTypePrivilegeDAO;
import gnete.card.entity.RoleTypePrivilege;

@Repository
public class RoleTypePrivilegeDAOImpl extends BaseDAOIbatisImpl implements RoleTypePrivilegeDAO {

    public String getNamespace() {
        return "RoleTypePrivilege";
    }
    
    public List<RoleTypePrivilege> findByType(String roleType) {
    	return this.queryForList("findByType", roleType);
    }
    
    public int deleteByRoleType(String roleType) {
    	return this.delete("deleteByRoleType", roleType);
    }
}