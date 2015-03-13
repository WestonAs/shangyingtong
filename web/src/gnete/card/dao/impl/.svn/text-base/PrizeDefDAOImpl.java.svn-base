package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PrizeDefDAO;
import gnete.card.entity.PrizeDef;

@Repository
public class PrizeDefDAOImpl extends BaseDAOIbatisImpl implements PrizeDefDAO {

	public String getNamespace() {
		return "PrizeDef";
	}

	public Paginater findPrizeDefPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPrizeDefPage", params, pageNumber,
				pageSize);
	}

	public List<PrizeDef> findByDrawId(String drawId) {
		return queryForList("findByDrawId", drawId);
	}
}