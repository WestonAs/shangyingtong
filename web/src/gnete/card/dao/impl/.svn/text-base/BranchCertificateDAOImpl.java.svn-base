package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.entity.BranchCertificate;
import gnete.card.dao.BranchCertificateDAO;

/**
 * 
 * @Project: MyCard
 * @File: BranchCertificateDAOImpl.java
 * @See:
 * @description：<li>面向机构证书的DAO处理类</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
@Repository("branchCertificateDAO")
public class BranchCertificateDAOImpl extends BaseDAOIbatisImpl implements BranchCertificateDAO {

	@Override
	protected String getNamespace() {
		return "BranchCertificate";
	}

	public Paginater findBranchCertificate(Map params, int pageNo, int pageSize) {
		return this.queryForPage("findBranchCertificate", params, pageNo, pageSize);
	}
	
	public List<BranchCertificate> findExpiredBranchCertificate(String params) {
		return this.queryForList("findExpiredCertificate", params);
	}

}
