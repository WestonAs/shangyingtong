package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.TrailBalanceSubacctDAO;

@Repository
public class TrailBalanceSubacctDAOImpl extends BaseDAOIbatisImpl implements TrailBalanceSubacctDAO {

    public String getNamespace() {
        return "TrailBalanceSubacct";
    }

	public Paginater findTrailBalanceSubacct(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTrailBalanceSubacct", params, pageNumber, pageSize);
	}
}