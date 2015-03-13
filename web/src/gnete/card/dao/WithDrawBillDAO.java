package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface WithDrawBillDAO extends BaseDAO {

	Paginater findPaginater(Map<String, Object> params, int pageNumber, int pageSize);
}