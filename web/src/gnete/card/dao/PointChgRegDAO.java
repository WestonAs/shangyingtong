package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface PointChgRegDAO extends BaseDAO {
	 public Paginater findPointChgReg(Map<String, Object> params, int pageNumber,
	    		int pageSize);
}