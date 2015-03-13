package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DrawDefDAO;
import gnete.card.entity.DrawDef;

@Repository
public class DrawDefDAOImpl extends BaseDAOIbatisImpl implements DrawDefDAO {

	public String getNamespace() {
		return "DrawDef";
	}

	public Paginater findDrawDefPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findDrawDefPage", params, pageNumber,
				pageSize);
	}

	public List<DrawDef> findByIssId(String issId) {
		return queryForList("findByIssId", issId);
	}
}