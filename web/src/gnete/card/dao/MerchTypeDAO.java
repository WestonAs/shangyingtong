package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MerchType;

public interface MerchTypeDAO extends BaseDAO {

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MerchType> findList(Map<String, Object> params);
	
	List<MerchType> findByStatus(String status);
}