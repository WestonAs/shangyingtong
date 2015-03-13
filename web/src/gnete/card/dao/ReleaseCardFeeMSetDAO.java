package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface ReleaseCardFeeMSetDAO extends BaseDAO {
	public Paginater findReleaseCardFeeMSet(Map<String, Object> params, int pageNumber,
    		int pageSize);
}