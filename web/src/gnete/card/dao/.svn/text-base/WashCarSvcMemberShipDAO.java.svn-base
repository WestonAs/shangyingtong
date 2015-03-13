package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.WashCarSvcMbShipDues;

public interface WashCarSvcMemberShipDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<WashCarSvcMbShipDues> washCarList(String status);
	
	WashCarSvcMbShipDues findWashCarMb(Map<String, Object> params);
}