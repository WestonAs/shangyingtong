package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CenterTermRepFeeMSet;

public interface CenterTermRepFeeMSetDAO extends BaseDAO {
	 public Paginater findCenterTermRepFeeMSet(Map<String, Object> params, int pageNumber,
	    		int pageSize);
	 
	 public List<CenterTermRepFeeMSet> findCenterTermRepFeeMSet(Map<String, Object> params);
}