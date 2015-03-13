package gnete.card.dao.impl;

import gnete.card.dao.PrivilegeResourceDAO;
import gnete.card.entity.PrivilegeResource;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PrivilegeResourceDAOImpl extends BaseDAOIbatisImpl implements PrivilegeResourceDAO {

    public String getNamespace() {
        return "PrivilegeResource";
    }

	public List<PrivilegeResource> getPrivilegeResources(String roleId) {
		return this.queryForList("getPrivilegeResources", roleId);
	}
}