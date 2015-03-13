package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.ClusterMerch;

/**
 * 商户集群关系
 * @Project: CardWeb
 * @File: ClusterMerchDAO.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-7下午3:04:43
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public interface ClusterMerchDAO extends BaseDAO {
	
	/** 
	 * 查找集群关系列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据集群代码删除信息
	 * @param branchCode
	 */
	int deleteByMerchClusterId(Long merchClusterId);
	
	/** 
	 * 查找经营机构集群关系列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<ClusterMerch> findClusterMerch(Map<String, Object> params);
	
	/**
	 * 查找商户集群的商户列表（根据商户集群id）
	 * @param branchCode
	 */
	List<ClusterMerch>  findByMerchClusterId(String merchClusterId);
}