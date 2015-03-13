package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface CancelCardRegDAO extends BaseDAO {
	public Paginater findCancelCardReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}