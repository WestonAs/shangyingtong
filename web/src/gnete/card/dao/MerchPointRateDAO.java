package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * 
 * @Project: CardWeb
 * @File: MerchPointRateDAO.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2012-12-10上午11:47:49
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public interface MerchPointRateDAO extends BaseDAO {

	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
}