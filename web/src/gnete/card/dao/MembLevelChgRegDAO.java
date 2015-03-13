package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MembLevelChgReg;

/**
 * 会员级别变更
 * @Project: CardWeb
 * @File: MembLevelChgRegDAO.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-25下午12:07:25
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public interface MembLevelChgRegDAO extends BaseDAO {

	/**
	 * 查找集群列表
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找集群
	 */
	List<MembLevelChgReg> findList(Map<String, Object> params);
	
}