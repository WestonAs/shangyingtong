package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardAreaRiskDAO;
import gnete.card.entity.CardAreaRisk;

@Repository
public class CardAreaRiskDAOImpl extends BaseDAOIbatisImpl implements CardAreaRiskDAO {

	public String getNamespace() {
		return "CardAreaRisk";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findCardAreaRisk", params, pageNumber, pageSize);
	}
	
	public List<CardAreaRisk> findList(Map<String, Object> params) {
		return this.queryForList("findCardAreaRisk", params);
	}
}