package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.RebateCardRegDAO;
import gnete.card.entity.RebateCardReg;

@Repository
public class RebateCardRegDAOImpl extends BaseDAOIbatisImpl implements RebateCardRegDAO {

    public String getNamespace() {
        return "RebateCardReg";
    }
    
    public List<RebateCardReg> findRebateCardRegList(Map<String, Object> params) {
    	return this.queryForList("findRebateCardReg", params);
    }
}