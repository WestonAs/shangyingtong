package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface BDPtDtotalDAO extends BaseDAO {
	Paginater findBDPtDtotal(Map<String, Object> params, int pageNumber,
			int pageSize);
}