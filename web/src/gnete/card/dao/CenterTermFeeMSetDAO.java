package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CenterTermFeeMSet;

public interface CenterTermFeeMSetDAO extends BaseDAO {
	Paginater findCenterTermFeeMSet(Map<String, Object> params, int pageNumber,
    		int pageSize);
	
	List<CenterTermFeeMSet> findCenterTermFeeMSet(Map<String, Object> params);
}