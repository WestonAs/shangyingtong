package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.AccountSystemInfo;

import java.util.List;
import java.util.Map;

public interface AccountSystemInfoDAO extends BaseDAO {
	Paginater findAccountSystemInfo(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<AccountSystemInfo> findByInfos(Map<String, Object> params);
}