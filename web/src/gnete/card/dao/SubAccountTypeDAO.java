package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface SubAccountTypeDAO extends BaseDAO {
	
	Paginater findSubAccountType(Map<String, Object> params, int pageNumber,
			int pageSize);
}