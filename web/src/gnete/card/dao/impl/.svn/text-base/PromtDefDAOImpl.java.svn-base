package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.PromtDefDAO;
import gnete.card.entity.PromtDef;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PromtDefDAOImpl extends BaseDAOIbatisImpl implements PromtDefDAO {

	public String getNamespace() {
		return "PromtDef";
	}

	public Paginater findPromtDef(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findPromtDef", params, pageNumber, pageSize);
	}

	public List<PromtDef> findPromtList(Map<String, Object> params) {
		return queryForList("findPromtDef", params);
	}
}