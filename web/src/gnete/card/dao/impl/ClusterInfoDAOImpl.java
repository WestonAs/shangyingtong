package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.ClusterInfoDAO;
import gnete.card.entity.ClusterInfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ClusterInfoDAOImpl extends BaseDAOIbatisImpl implements ClusterInfoDAO {

	public String getNamespace() {
		return "ClusterInfo";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findClusterInfo", params, pageNumber, pageSize);
	}

	@Override
	public List<ClusterInfo> findByClusterName(Map<String, Object> params) {
		List<ClusterInfo> clusterInfos = this.queryForList("findByClusterName", params);
		return clusterInfos.size() > 0 ? clusterInfos : null;
	}

}