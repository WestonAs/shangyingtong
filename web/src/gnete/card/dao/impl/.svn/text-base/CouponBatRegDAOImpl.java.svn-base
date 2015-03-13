package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CouponBatRegDAO;

@Repository
public class CouponBatRegDAOImpl extends BaseDAOIbatisImpl implements CouponBatRegDAO {

    public String getNamespace() {
        return "CouponBatReg";
    }

	public Paginater findCouponBatReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCouponBatReg", params, pageNumber, pageSize);
	}
}