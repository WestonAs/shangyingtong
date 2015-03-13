package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface TrailBalanceRegDAO extends BaseDAO {
	public Paginater findTrailBalanceReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}