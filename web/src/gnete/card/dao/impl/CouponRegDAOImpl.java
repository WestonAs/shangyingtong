package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CouponRegDAO;

@Repository
public class CouponRegDAOImpl extends BaseDAOIbatisImpl implements CouponRegDAO {

    public String getNamespace() {
        return "CouponReg";
    }

	public Paginater findCouponReg(Map<String, Object> params, int pageNumber,
			int pageSize) {
		
		return this.queryForPage("findCouponReg", params, pageNumber, pageSize);
	}
}