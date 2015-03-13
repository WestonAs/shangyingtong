package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.ClusterInfo;
import gnete.card.entity.MembInfoReg;

/**
 * 会员登记
 * @Project: CardWeb
 * @File: MembInfoRegDAO.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-15上午9:42:47
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public interface MembInfoRegDAO extends BaseDAO {

	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MembInfoReg> findList(Map<String, Object> params);
	
	Long selectMembInfoRegSEQ();
	
	List<MembInfoReg> findMembInfoIdList(Map<String, Object> params);
}