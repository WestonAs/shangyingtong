package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.ClusterMerch;
import gnete.card.entity.MembAppointReg;

/**
 * 会员登记与卡号关联
 * @Project: CardWeb
 * @File: MembAppointRegDAO.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-15上午9:37:11
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public interface MembAppointRegDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MembAppointReg> findList(Map<String, Object> params);
}