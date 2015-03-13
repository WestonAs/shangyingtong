package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface MembImportRegDAO extends BaseDAO {
	public Paginater findMembImportReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}