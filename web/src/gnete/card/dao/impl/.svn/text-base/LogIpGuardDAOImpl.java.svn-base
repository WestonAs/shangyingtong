package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.LogIpGuardDAO;
import gnete.card.entity.LogIpGuard;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class LogIpGuardDAOImpl extends BaseDAOIbatisImpl implements LogIpGuardDAO {

	public String getNamespace() {
		return "StaticDataQry";
	}

	@Override
	public Paginater findLogIpGuard(Map<String, Object> params, int pageNumber,
			int pageSize) {
	
		return this.queryForPage("listLogIpGuard", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<LogIpGuard> findLogIpGuard(Map<String, Object> params) {
		
		return this.queryForList("listLogIpGuard", params);
	}
}