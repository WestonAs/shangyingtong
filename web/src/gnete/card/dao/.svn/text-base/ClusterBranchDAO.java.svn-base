package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ClusterBranch;

/**
 * @File: CardClusterDAO.java
 *
 * @description: 经营机构集群表DAO
 * @version: 1.0
 * @since 1.0 2012-11-08
 */
public interface ClusterBranchDAO extends BaseDAO {
	
	/** 
	 * 查找经营机构集群关系列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据集群代码删除机构信息
	 * @param branchCode
	 */
	int deleteByClusterid(Long clusterid);
	
	/** 
	 * 查找经营机构集群关系列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<ClusterBranch> findClusterBranchList(Map<String, Object> params);
}