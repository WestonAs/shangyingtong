package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.UserCertificateRevDAO;
import gnete.card.entity.UserCertificate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @Project: CardFourth
 * @File: UserCertificateRevDAOImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-25
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Repository("userCertificateRevDAO")
public class UserCertificateRevDAOImpl extends BaseDAOIbatisImpl implements UserCertificateRevDAO {

	public String getNamespace() {
		return "UserCertificateRev";
	}

	public Paginater find(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("find", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<UserCertificate> findUserCertificate(Map params) {
		return this.queryForList("find", params);
	}

	public int updateExpiredUserCertificate(String currentDate) {
		return this.update("updateExpiredUserCertificate", currentDate);
	}
}
