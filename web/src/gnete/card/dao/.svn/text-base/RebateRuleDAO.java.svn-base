package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.RebateRule;

public interface RebateRuleDAO extends BaseDAO {
	Paginater findRebateRule(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/** 查询返利规则列表 */
	List<RebateRule> findRebateRule(Map<String, Object> params);

	/** 判断cardId是否已经使用了rebateRuleId规则 */
	boolean isUsedPeriodRule(String cardId, Long rebateRuleId);
	
}