package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PlanPrivilegeDAO;
import gnete.card.entity.PlanPrivilege;

@Repository
public class PlanPrivilegeDAOImpl extends BaseDAOIbatisImpl implements PlanPrivilegeDAO {

    public String getNamespace() {
        return "PlanPrivilege";
    }
    
    public List<PlanPrivilege> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
}