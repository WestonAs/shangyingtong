package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface CardRiskBalanceDAO extends BaseDAO {
	public Paginater findCardRiskBalance(Map<String, Object> params, int pageNumber,
			int pageSize);
}