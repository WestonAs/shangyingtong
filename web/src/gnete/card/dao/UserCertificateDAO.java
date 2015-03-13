package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.UserCertificate;

public interface UserCertificateDAO extends BaseDAO {

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);

	List<UserCertificate> findUserCertificate(Map params);
	
	List<UserCertificate> findExpiredUserCertificate(String param);

}