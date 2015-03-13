package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.RebateRuleDetail;

public interface RebateRuleDetailDAO extends BaseDAO {
	
	// 分页查询返利规则分段比例明细
	Paginater findRebateRuleDetail(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	// 查询返利规则分段比例明细
	List<RebateRuleDetail> findRebateRuleDetail(Map<String, Object> params);

	// 查询返利规则分段比例明细
	int deleteByRebateId(long rebateId);
	
}