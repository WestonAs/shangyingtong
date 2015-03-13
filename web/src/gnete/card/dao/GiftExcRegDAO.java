package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface GiftExcRegDAO extends BaseDAO {
	public Paginater findGiftExcReg(Map<String, Object> params, int pageNumber,
			int pageSize);
	
}