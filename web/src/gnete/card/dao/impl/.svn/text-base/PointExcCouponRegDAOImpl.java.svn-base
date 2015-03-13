package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.PointExcCouponRegDAO;

@Repository
public class PointExcCouponRegDAOImpl extends BaseDAOIbatisImpl implements PointExcCouponRegDAO {

    public String getNamespace() {
        return "PointExcCouponReg";
    }

	public Paginater findPointExcCouponReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPointExcCouponReg", params, pageNumber, pageSize);
	}

}