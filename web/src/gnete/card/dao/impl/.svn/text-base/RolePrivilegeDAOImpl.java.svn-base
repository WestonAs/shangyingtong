package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import java.util.List;
import gnete.card.dao.RolePrivilegeDAO;
import gnete.card.entity.RolePrivilegeKey;

@Repository
public class RolePrivilegeDAOImpl extends BaseDAOIbatisImpl implements RolePrivilegeDAO {

    public String getNamespace() {
        return "RolePrivilege";
    }
    
    public int deleteByRoleId(String roleId) {
    	return this.delete("deleteByRoleId", roleId);
    }

	public boolean hasPrivilege(Map<String, Object> params) {
		List<RolePrivilegeKey> rolePrivilegeKeyList  = this.queryForList("hasPrivilege", params);
		return !rolePrivilegeKeyList.isEmpty();
	}
}