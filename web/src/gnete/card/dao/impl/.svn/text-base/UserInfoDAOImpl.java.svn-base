package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDAOImpl extends BaseDAOIbatisImpl implements UserInfoDAO {

    public String getNamespace() {
        return "UserInfo";
    }
    
    public Paginater findUser(Map params, int pageNo, int pageSize) {
    	return this.queryForPage("findUser", params, pageNo, pageSize);
    }
    
    public List<UserInfo> findByBranch(String branchCode, String deptId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchNo", branchCode);
    	params.put("deptId", deptId);
    	return this.queryForList("findByBranch", params);
    }
    
    public List<UserInfo> findCertficateUser(Map params) {
    	return this.queryForList("findUser", params);
    }
    
	@Override
	public boolean isUserOfLimitedTransQuery(String userId) {
		try{
			Object ret = this.queryForObject("isUserOfLimitedTransQuery", userId);
			return ret == null ? false : true;
		}catch (Exception e) {
			return false;
		}
	}
 }