package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface PointAccRegDAO extends BaseDAO {
	public Paginater findPointAccReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}