package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.TrailBalanceRegDAO;

@Repository
public class TrailBalanceRegDAOImpl extends BaseDAOIbatisImpl implements TrailBalanceRegDAO {

    public String getNamespace() {
        return "TrailBalanceReg";
    }

	public Paginater findTrailBalanceReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTrailBalanceReg", params, pageNumber, pageSize);
	}
}