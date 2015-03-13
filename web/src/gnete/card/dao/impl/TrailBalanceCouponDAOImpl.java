package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.TrailBalanceCouponDAO;

@Repository
public class TrailBalanceCouponDAOImpl extends BaseDAOIbatisImpl implements TrailBalanceCouponDAO {

    public String getNamespace() {
        return "TrailBalanceCoupon";
    }

	public Paginater findTrailBalanceCoupon(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTrailBalanceCoupon", params, pageNumber, pageSize);
	}
}