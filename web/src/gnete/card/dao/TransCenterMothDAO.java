package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.TransCenterMoth;

public interface TransCenterMothDAO extends BaseDAO {
	
	Paginater findTransCenterMoth(Map<String,Object> params, int pageNumber,
			int pageSize);
	
	List<TransCenterMoth> findTransCenterMoth(Map<String,Object> params);
}