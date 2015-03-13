package gnete.card.dao;

import flink.util.Paginater;

import java.util.Map;

public interface TransLimitTermDAO extends BaseDAO {

	public Paginater findTransLimitTerm(Map<String, Object> params, int pageNumber, int pageSize);

}