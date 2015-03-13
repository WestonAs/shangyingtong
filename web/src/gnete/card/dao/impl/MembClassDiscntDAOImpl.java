package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.MembClassDiscntDAO;
import gnete.card.entity.MembClassDiscnt;

@Repository
public class MembClassDiscntDAOImpl extends BaseDAOIbatisImpl implements MembClassDiscntDAO {

    public String getNamespace() {
        return "MembClassDiscnt";
    }
    
    public List<MembClassDiscnt> findListByMembClass(String membClass) {
    	return this.queryForList("findListByMembClass", membClass);
    }
}