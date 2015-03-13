package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MerchClusterInfo;

/**
 * 商户集群
 * @Project: CardWeb
 * @File: MerchClusterInfoDAO.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-7下午3:08:33
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public interface MerchClusterInfoDAO extends BaseDAO {

	/**
	 * 查找集群列表
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找集群
	 */
	List<MerchClusterInfo> findByMerchClusterName(Map<String, Object> params);
	
	/**
	 * 查找集群列表
	 */
	List<MerchClusterInfo> findMerchClusterInfo(Map<String, Object> params);
	/**
	 * 查找集群列表 （根据发卡机构编号）
	 * @param cardIssuer 发卡机构编号
	 * @return
	 */
	List<MerchClusterInfo> findByCardIssuer(String cardIssuer);
	
}