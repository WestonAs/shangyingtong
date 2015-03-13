package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface PasswordResetRegDAO extends BaseDAO {
	public Paginater findPwReset(Map<String, Object> params, int pageNumber,
			int pageSize);
}