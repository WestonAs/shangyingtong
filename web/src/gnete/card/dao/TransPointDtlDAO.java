package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface TransPointDtlDAO extends BaseDAO {
	public Paginater findTransPointDtl(Map<String, Object> params, int pageNumber,
			int pageSize);
}