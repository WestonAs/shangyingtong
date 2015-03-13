package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface WxReconciliationDetailDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
}