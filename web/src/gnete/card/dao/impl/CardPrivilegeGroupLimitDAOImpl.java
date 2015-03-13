package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.CardPrivilegeGroupLimitDAO;

@Repository
public class CardPrivilegeGroupLimitDAOImpl extends BaseDAOIbatisImpl implements CardPrivilegeGroupLimitDAO {

    public String getNamespace() {
        return "CardPrivilegeGroupLimit";
    }
    
    public int deleteByGroupId(Long id) {
    	return this.delete("deleteByGroupId", id);
    }
    
    public List findLimit(Long id) {
    	return this.queryForList("findLimit", id);
    }
}