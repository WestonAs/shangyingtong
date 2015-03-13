package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MerchGroup;

public interface MerchGroupDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MerchGroup> findList(Map<String, Object> params);
	
	String selectMerchGroupSEQ() ;
}