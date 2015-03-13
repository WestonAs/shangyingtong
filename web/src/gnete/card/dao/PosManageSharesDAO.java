package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface PosManageSharesDAO extends BaseDAO {
	 public Paginater findPosManageShares(Map<String, Object> params, int pageNumber,
	    		int pageSize);
}