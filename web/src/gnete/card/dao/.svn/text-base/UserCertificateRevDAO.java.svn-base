package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.UserCertificate;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Project: Card
 * @File: UserCertificateRevDAO.java
 * @See: UserCertificateDAO.java
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-25
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface UserCertificateRevDAO extends BaseDAO {
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);

	List<UserCertificate> findUserCertificate(Map params);

	int updateExpiredUserCertificate(String currentDate);

}
