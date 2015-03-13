package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.WxUserInfo;

import java.util.List;
import java.util.Map;

public interface WxUserInfoDAO extends BaseDAO {

	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);

	List<WxUserInfo> findList(Map<String, Object> params);
	
	WxUserInfo findCardIdByPk(String userId);
}