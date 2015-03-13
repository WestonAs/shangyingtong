package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.TrailBalancePointDAO;

@Repository
public class TrailBalancePointDAOImpl extends BaseDAOIbatisImpl implements TrailBalancePointDAO {

    public String getNamespace() {
        return "TrailBalancePoint";
    }

	public Paginater findTrailBalancePoint(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTrailBalancePoint", params, pageNumber, pageSize);
	}
}