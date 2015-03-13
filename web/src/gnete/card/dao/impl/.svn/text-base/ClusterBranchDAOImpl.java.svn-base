package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.ClusterBranchDAO;
import gnete.card.entity.ClusterBranch;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ClusterBranchDAOImpl extends BaseDAOIbatisImpl implements ClusterBranchDAO {

	public String getNamespace() {
		return "ClusterBranch";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findClusterBranch", params, pageNumber, pageSize);
	}
	
	@Override
	public int deleteByClusterid(Long clusterid) {
		return this.delete("deleteByClusterid", clusterid);
	}
	
	public List<ClusterBranch> findClusterBranchList(Map<String, Object> params) {
		return this.queryForList("findClusterBranchList", params);
	}
}