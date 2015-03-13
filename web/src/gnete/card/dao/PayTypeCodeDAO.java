package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface PayTypeCodeDAO extends BaseDAO {

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
}