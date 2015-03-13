package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.RoleInfoDAO;
import gnete.card.entity.RoleInfo;

@Repository
public class RoleInfoDAOImpl extends BaseDAOIbatisImpl implements RoleInfoDAO {

    public String getNamespace() {
        return "RoleInfo";
    }
    
    public List<RoleInfo> findByUserId(String userId) {
    	return this.queryForList("findByUserId", userId);
    }
    
    public Paginater findRole(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findRole", params, pageNumber, pageSize);
    }
    
    public List<RoleInfo> findCommonByRoleType(String roleType) {
    	return this.queryForList("findCommonByRoleType", roleType);
    }
    
    public List<RoleInfo> findAssignRole(String branchCode, String merchantNo, String deptId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchCode", branchCode);
    	params.put("merchantNo", merchantNo);
    	params.put("deptId", deptId);
    	return this.queryForList("findAssignRole", params);
    }
    
    public List<RoleInfo> findByRoleName(String roleName) {
    	return this.queryForList("findByRoleName", roleName);
    }
}