package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface AddMagRegDAO extends BaseDAO {
	public Paginater findAddMag(Map<String, Object> params, int pageNumber,
			int pageSize);

}