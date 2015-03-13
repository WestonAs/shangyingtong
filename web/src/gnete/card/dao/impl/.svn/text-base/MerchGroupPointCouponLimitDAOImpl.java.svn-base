package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchGroupPointCouponLimitDAO;
import gnete.card.entity.MerchGroupPointCouponLimit;

@Repository
public class MerchGroupPointCouponLimitDAOImpl extends BaseDAOIbatisImpl implements MerchGroupPointCouponLimitDAO {

	public String getNamespace() {
		return "MerchGroupPointCouponLimit";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMerchGroupPointCouponLimit", params, pageNumber, pageSize);
	}
	
	public List<MerchGroupPointCouponLimit> findList(Map<String, Object> params) {
		return this.queryForList("findMerchGroupPointCouponLimit", params);
	}
}