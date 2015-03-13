package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.WxBillInfo;

public interface WxBillInfoDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<WxBillInfo> findList(Map<String, Object> params);
	
}