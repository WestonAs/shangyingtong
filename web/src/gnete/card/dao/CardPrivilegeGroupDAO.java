package gnete.card.dao;

import flink.util.Paginater;

import java.util.Map;

public interface CardPrivilegeGroupDAO extends BaseDAO {

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);

}