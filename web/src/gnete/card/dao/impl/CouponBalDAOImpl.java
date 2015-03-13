package gnete.card.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CouponBalDAO;
import gnete.card.entity.CouponBal;

@Repository
public class CouponBalDAOImpl extends BaseDAOIbatisImpl implements CouponBalDAO {

    public String getNamespace() {
        return "CouponBal";
    }

	public BigDecimal getBalTotal(Map<String, Object> params) {
		return (BigDecimal) this.queryForObject("getBalTotal", params);
	}

	public Paginater getCouponBalList(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("getCouponBalList", params, pageNumber, pageSize);
	}
	
	public List<CouponBal> findCouponBalList(Map<String, Object> params) {
		return this.queryForList("getCouponBalList", params);
	}
}