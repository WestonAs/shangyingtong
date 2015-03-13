package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface AdjAccRegDAO extends BaseDAO {
	public Paginater findAdjAccReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}