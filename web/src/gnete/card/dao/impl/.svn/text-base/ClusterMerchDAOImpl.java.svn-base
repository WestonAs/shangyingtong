package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.ClusterMerchDAO;
import gnete.card.entity.ClusterMerch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ClusterMerchDAOImpl extends BaseDAOIbatisImpl implements ClusterMerchDAO {

	public String getNamespace() {
		return "ClusterMerch";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findClusterMerch", params, pageNumber, pageSize);
	}
	
	@Override
	public int deleteByMerchClusterId(Long merchClusterId) {
		return this.delete("deleteByMerchClusterId", merchClusterId);
	}
	
	public List<ClusterMerch> findClusterMerch(Map<String, Object> params) {
		return this.queryForList("findClusterMerch", params);
	}

	@Override
	public List<ClusterMerch> findByMerchClusterId(String merchClusterId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchClusterId", merchClusterId);
		return findClusterMerch(params);
	}
}