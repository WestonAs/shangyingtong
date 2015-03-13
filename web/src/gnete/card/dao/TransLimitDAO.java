package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.TransLimit;

public interface TransLimitDAO extends BaseDAO {
	public Paginater findTransLimit(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	List<TransLimit> getTransLimitList(Map<String, Object> params);
}