package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.TestLeaveDAO;
import gnete.card.entity.TestLeave;

@Repository
public class TestLeaveDAOImpl extends BaseDAOIbatisImpl implements TestLeaveDAO {

    public String getNamespace() {
        return "TestLeave";
    }
    
    public List<TestLeave> findByIds(String[] ids) {
    	return this.queryForList("findByIds", ids);
    }
}