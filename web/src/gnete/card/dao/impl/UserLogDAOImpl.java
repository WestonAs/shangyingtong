package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.UserLogDAO;

@Repository
public class UserLogDAOImpl extends BaseDAOIbatisImpl implements UserLogDAO {

    public String getNamespace() {
        return "UserLog";
    }
    
    public Paginater findLog(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findLog", params, pageNumber, pageSize);
    }
}