package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardExtraInfo;

import java.util.List;
import java.util.Map;

public interface CardExtraInfoDAO extends BaseDAO {
	
	public Paginater findCardExtraInfo(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	public Object findCardExtraInfoByIdName(Map<String, Object> params);
	
	public List<CardExtraInfo> findCardExtraInfoByParam(Map<String, String> formMap);
}