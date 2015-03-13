package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardissuerPlanDAO;
import gnete.card.entity.CardissuerPlan;

@Repository
public class CardissuerPlanDAOImpl extends BaseDAOIbatisImpl implements CardissuerPlanDAO {

    public String getNamespace() {
        return "CardissuerPlan";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<CardissuerPlan> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
}