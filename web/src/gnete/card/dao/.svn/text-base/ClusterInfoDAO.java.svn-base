package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.ClusterInfo;

/**
 * @File: ClusterInfoDAO.java
 * 
 * @description: 集群信息表DAO
 * 
 * @since 1.0 2012-11-08
 */
public interface ClusterInfoDAO extends BaseDAO {

	/**
	 * 查找集群列表
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找集群
	 */
	List<ClusterInfo> findByClusterName(Map<String, Object> params);
	
}