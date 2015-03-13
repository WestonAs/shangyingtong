package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.entity.UserCertificate;
import gnete.card.dao.UserCertificateDAO;

@Repository("userCertificateDAO")
public class UserCertificateDAOImpl extends BaseDAOIbatisImpl implements UserCertificateDAO {

    public String getNamespace() {
        return "UserCertificate";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<UserCertificate> findUserCertificate(Map params) {
    	return this.queryForList("find", params);
    }
    
    public List<UserCertificate> findExpiredUserCertificate(String param) {
    	return this.queryForList("findExpiredUserCertificate", param);
    }
 }