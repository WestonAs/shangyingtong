package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MerchRespCode;

public interface MerchRespCodeDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MerchRespCode> findList(Map<String, Object> params);
}