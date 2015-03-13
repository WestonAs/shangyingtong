package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface RmaTransTypeLimitDAO extends BaseDAO {
	public Paginater findRmaTransTypeLimit(Map<String, Object> params, int pageNumber,
			int pageSize);
}