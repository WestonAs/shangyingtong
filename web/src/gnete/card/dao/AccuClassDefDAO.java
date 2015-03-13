package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.AccuClassDef;

public interface AccuClassDefDAO extends BaseDAO {
	public Paginater findAccuClassDef(Map<String, Object> params, int pageNumber,
    		int pageSize);
	
	List<AccuClassDef> findAccuClassList(Map<String, Object> params);
}