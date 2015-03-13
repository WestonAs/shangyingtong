package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.TrailBalanceStatusDAO;

@Repository
public class TrailBalanceStatusDAOImpl extends BaseDAOIbatisImpl implements TrailBalanceStatusDAO {

    public String getNamespace() {
        return "TrailBalanceStatus";
    }

	public Paginater findTrailBalanceStatus(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTrailBalanceStatus", params, pageNumber, pageSize);
	}
}