package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CouponAwardRegDAO;
import gnete.card.entity.CouponAwardReg;

@Repository
public class CouponAwardRegDAOImpl extends BaseDAOIbatisImpl implements CouponAwardRegDAO {

    public String getNamespace() {
        return "CouponAwardReg";
    }

	public Paginater findCouponAwardReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCouponAwardReg", params, pageNumber, pageSize);
	}

	public List<CouponAwardReg> getCouponAwardRegList(Map<String, Object> params) {
		return this.queryForList("findCouponAwardReg", params);
	}
}