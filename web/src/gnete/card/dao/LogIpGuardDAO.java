package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.LogIpGuard;

import java.util.List;
import java.util.Map;

public interface LogIpGuardDAO extends BaseDAO {
	
	Paginater findLogIpGuard(Map<String, Object> params, int pageNumber,
			int pageSize);
    
	List<LogIpGuard> findLogIpGuard(Map<String, Object> params);
}