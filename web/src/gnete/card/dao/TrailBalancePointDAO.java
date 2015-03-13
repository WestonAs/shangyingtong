package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface TrailBalancePointDAO extends BaseDAO {
	public Paginater findTrailBalancePoint(Map<String, Object> params, int pageNumber,
			int pageSize);
}