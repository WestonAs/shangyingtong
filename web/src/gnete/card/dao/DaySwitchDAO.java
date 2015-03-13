package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface DaySwitchDAO extends BaseDAO {
	public Paginater findDaySwitch(Map<String, Object> params, int pageNumber,
			int pageSize);
}