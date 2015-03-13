package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface RiskParamDAO extends BaseDAO {
	public Paginater findRiskParam(Map<String, Object> params, int pageNumber,
			int pageSize);
}