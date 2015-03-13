package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.UserSellAmtDAO;

@Repository
public class UserSellAmtDAOImpl extends BaseDAOIbatisImpl implements UserSellAmtDAO {

    public String getNamespace() {
        return "UserSellAmt";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
}