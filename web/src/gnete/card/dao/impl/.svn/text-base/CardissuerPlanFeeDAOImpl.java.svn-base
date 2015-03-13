package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardissuerPlanFeeDAO;
import gnete.card.entity.CardissuerPlanFee;

@Repository
public class CardissuerPlanFeeDAOImpl extends BaseDAOIbatisImpl implements CardissuerPlanFeeDAO {

    public String getNamespace() {
        return "CardissuerPlanFee";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<CardissuerPlanFee> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
}