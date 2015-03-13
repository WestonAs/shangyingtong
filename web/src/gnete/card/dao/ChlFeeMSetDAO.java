package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface ChlFeeMSetDAO extends BaseDAO {
	public Paginater findChlFeeMSet(Map<String, Object> params, int pageNumber,
			int pageSize);
}