package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.RiskParamDAO;

@Repository
public class RiskParamDAOImpl extends BaseDAOIbatisImpl implements RiskParamDAO {

    public String getNamespace() {
        return "RiskParam";
    }

	public Paginater findRiskParam(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findRiskParam", params, pageNumber, pageSize);
	}

}