package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.BranchCertificate;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Project: MyCard
 * @File: BranchCertificateDAO.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface BranchCertificateDAO extends BaseDAO {

	Paginater findBranchCertificate(Map params, int pageNo, int pageSize);

	List<BranchCertificate> findExpiredBranchCertificate(String params);

}
