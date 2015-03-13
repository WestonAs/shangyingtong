package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface RiskMarginRegDAO extends BaseDAO {
	public Paginater findRiskMarginReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}