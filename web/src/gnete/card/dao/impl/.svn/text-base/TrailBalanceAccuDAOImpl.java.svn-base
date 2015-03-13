package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.TrailBalanceAccuDAO;

@Repository
public class TrailBalanceAccuDAOImpl extends BaseDAOIbatisImpl implements TrailBalanceAccuDAO {

    public String getNamespace() {
        return "TrailBalanceAccu";
    }

	public Paginater findTrailBalanceAccu(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTrailBalanceAccu", params, pageNumber, pageSize);
	}
}