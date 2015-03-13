package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MerchInfoReg;

/**
 * @File: MerchInfoRegDAO.java
 *
 * @description: 商户登记簿操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-8-11
 */
public interface MerchInfoRegDAO extends BaseDAO {
	
	/**
	 * 商户新增登记记录（包含所有的新增记录）
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MerchInfoReg> find(Map<String, Object> params);
}