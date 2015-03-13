package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface CardSaleDSetDAO extends BaseDAO {
	public Paginater findCardSaleDSet(Map<String, Object> params, int pageNumber,
    		int pageSize);
}