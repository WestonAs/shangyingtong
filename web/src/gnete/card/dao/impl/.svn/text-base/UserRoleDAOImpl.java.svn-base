package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.UserRoleDAO;
import gnete.card.entity.UserRoleKey;

@Repository
public class UserRoleDAOImpl extends BaseDAOIbatisImpl implements UserRoleDAO {

    public String getNamespace() {
        return "UserRole";
    }
    
    public List<UserRoleKey> findByRoleId(String roleId) {
    	return this.queryForList("findByRoleId", roleId);
    }
    
    public List<UserRoleKey> findByUserId(String userId) {
    	return this.queryForList("findByUserId", userId);
    }
    
    public int deleteByUserId(String userId) {
    	return this.delete("deleteByUserId", userId);
    }
}