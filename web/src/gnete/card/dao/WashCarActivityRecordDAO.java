package gnete.card.dao;

import flink.util.Paginater;

import java.util.Map;

public interface WashCarActivityRecordDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
}