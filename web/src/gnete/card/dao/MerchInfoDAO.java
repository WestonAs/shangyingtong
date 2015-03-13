package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.MerchInfo;

import java.util.List;
import java.util.Map;

/**
 * @File: MerchInfoDAO.java
 *
 * @description: 商户信息表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-8-11
 */
public interface MerchInfoDAO extends BaseDAO {

	String getMerchId(String merchIdPrev);
	
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MerchInfo> find(Map<String, Object> params);

	List<MerchInfo> findByManage(String branchNo);
	
	List<MerchInfo> findFranchMerchList(String branchCode);

	boolean isDirectManagedBy(String merchId, String manageBranchCode);

}