package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PlanSubRuleDAO;
import gnete.card.entity.PlanSubRule;

@Repository
public class PlanSubRuleDAOImpl extends BaseDAOIbatisImpl implements PlanSubRuleDAO {

    public String getNamespace() {
        return "PlanSubRule";
    }
    
    public List<PlanSubRule> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
}