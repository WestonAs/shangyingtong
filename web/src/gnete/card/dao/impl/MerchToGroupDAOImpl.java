package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.MerchToGroupDAO;
import gnete.card.entity.MerchToGroup;

@Repository
public class MerchToGroupDAOImpl extends BaseDAOIbatisImpl implements MerchToGroupDAO {

    public String getNamespace() {
        return "MerchToGroup";
    }
    
    public int deleteByGroupId(String groupId) {
    	return this.delete("deleteByGroupId", groupId);
    }
    
    public List<MerchToGroup> findByGroupId(String groupId) {
    	return this.queryForList("findByGroupId", groupId);
    }
    
    public List<MerchToGroup> findMerchToGroup(Map<String, Object> params) {
    	return this.queryForList("findMerchToGroup", params);
    }
}