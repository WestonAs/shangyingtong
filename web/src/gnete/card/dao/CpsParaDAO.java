package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface CpsParaDAO extends BaseDAO {
	Paginater findCpsPara(Map<String, Object> params, int pageNumber,
			int pageSize);
}