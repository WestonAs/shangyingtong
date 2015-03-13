package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PromtScopeDAO;
import gnete.card.entity.PromtScope;

@Repository
public class PromtScopeDAOImpl extends BaseDAOIbatisImpl implements
		PromtScopeDAO {

	public String getNamespace() {
		return "PromtScope";
	}

	public Paginater findPromtScope(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return queryForPage("findPromtScope", params, pageNumber, pageSize);
	}

	public boolean deleteByPromtId(String promtId) {
		return this.delete("deleteByPromtId", promtId) > 0;
	}

	public List<PromtScope> findByPromtId(String promtId) {
		return queryForList("findByPromtId", promtId);
	}
}