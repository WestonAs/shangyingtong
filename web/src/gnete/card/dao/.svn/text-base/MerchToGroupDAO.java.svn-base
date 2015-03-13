package gnete.card.dao;

import gnete.card.entity.MerchToGroup;

import java.util.List;
import java.util.Map;

public interface MerchToGroupDAO extends BaseDAO {

	/**
	 * 根据商户组ID来删除
	 * @param groupId
	 * @return
	 */
	int deleteByGroupId(String groupId);

	/**
	 * 根据组代码来查询
	 * @param groupId
	 * @return
	 */
	List<MerchToGroup> findByGroupId(String groupId);
	
	List<MerchToGroup> findMerchToGroup(Map<String, Object> params);
}