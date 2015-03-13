package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardissuerPlanFeeRuleDAO;
import gnete.card.entity.CardissuerPlanFeeRule;

@Repository
public class CardissuerPlanFeeRuleDAOImpl extends BaseDAOIbatisImpl implements CardissuerPlanFeeRuleDAO {

    public String getNamespace() {
        return "CardissuerPlanFeeRule";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<CardissuerPlanFeeRule> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public int deleteByBranch(String branchCode) {
    	return this.delete("deleteByBranch", branchCode);
    }
}