package gnete.card.dao.impl;

import flink.util.WebResource;
import gnete.card.dao.PrivilegeDAO;
import gnete.card.entity.Privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PrivilegeDAOImpl extends BaseDAOIbatisImpl implements PrivilegeDAO {

    public String getNamespace() {
        return "Privilege";
    }
    
    public List getMenus(String roleId) {
		List list = this.queryForList("getMenus", roleId);
		List menus = new ArrayList();
		
		for (Iterator i = list.iterator(); i.hasNext();) {
			Map<String, Object> element = (Map<String, Object>) i.next();
			Privilege p = new Privilege();
			p.setLimitId((String) element.get("LIMIT_ID"));
			p.setLimitName((String) element.get("LIMIT_NAME"));
			p.setParent((String) element.get("PARENT"));
			p.setEntry(WebResource.getLink((String) element.get("URL"), (String) element.get("PARAM")));
			p.setIsMenu((String) element.get("IS_MENU"));
			p.setIfAudit((String) element.get("IF_AUDIT"));
			
			menus.add(p);
		}
		
		return menus;
	}
    
    public List<Privilege> getPrivilege(String roleId) {
		return this.queryForList("getPrivilege", roleId);
	}
    
    public List findByRoleId(String roleId) {
    	return this.queryForList("findByRoleId", roleId);
    }

    public List<Privilege> findByProxyAndCard(String proxyId, String cardBranch) {
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", proxyId);
		params.put("proxyCard", cardBranch);
		
    	return this.queryForList("findByProxyAndCard", params);
    }
    
    public List<Privilege> findByDept(String branchNo, String deptId) {
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", branchNo);
		params.put("deptId", deptId);
		
    	return this.queryForList("findByDept", params);
    }
    
    public List<Privilege> findByRoleType(String roleType) {
    	return this.queryForList("findByRoleType", roleType);
    }
}