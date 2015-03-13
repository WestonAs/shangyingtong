package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface LargessRegDAO extends BaseDAO {
	public Paginater findLargessReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}