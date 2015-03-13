package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface TrailBalanceAccuDAO extends BaseDAO {
	public Paginater findTrailBalanceAccu(Map<String, Object> params, int pageNumber,
			int pageSize);
}