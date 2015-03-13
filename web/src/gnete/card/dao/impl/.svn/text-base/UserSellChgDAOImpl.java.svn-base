package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.UserSellChgDAO;

@Repository
public class UserSellChgDAOImpl extends BaseDAOIbatisImpl implements UserSellChgDAO {

    public String getNamespace() {
        return "UserSellChg";
    }
    
    public List findByUser(String userId) {
    	return this.queryForList("findByUser", userId);
    }
    
}