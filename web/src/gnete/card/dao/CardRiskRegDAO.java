package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface CardRiskRegDAO extends BaseDAO {
	public Paginater findCardRiskReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}