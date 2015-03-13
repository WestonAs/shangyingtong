package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PromtRuleDefDAO;
import gnete.card.entity.PromtRuleDef;

@Repository
public class PromtRuleDefDAOImpl extends BaseDAOIbatisImpl implements
		PromtRuleDefDAO {

	public String getNamespace() {
		return "PromtRuleDef";
	}

	public Paginater findPromtRuleDef(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPromtRuleDef", params, pageNumber,
				pageSize);
	}

	public Paginater findPromtRuleDefCheck(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPromtRuleDefCheck", params, pageNumber,
				pageSize);
	}

	public List<PromtRuleDef> findByPromtId(String promtId) {
		return queryForList("findByPromtId", promtId);
	}

	public boolean deleteByPromtId(String promtId) {
		return this.delete("deleteByPromtId", promtId) > 0;
	}
}