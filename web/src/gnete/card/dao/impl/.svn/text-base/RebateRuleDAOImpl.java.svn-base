package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.entity.RebateRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RebateRuleDAOImpl extends BaseDAOIbatisImpl implements RebateRuleDAO {

    public String getNamespace() {
        return "RebateRule";
    }
	public Paginater findRebateRule(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findRebateRule", params, pageNumber, pageSize);
    }
	
	// 查询返利规则列表
	public List<RebateRule> findRebateRule(Map<String, Object> params){
		return queryForList("findRebateRule", params);		
	}
	
	@Override
	public boolean isUsedPeriodRule(String cardId, Long rebateRuleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardId", cardId);
		params.put("rebateId", rebateRuleId);
		return (Long) this.queryForObject("isUsedPeriodRule", params) > 0;
	}
}