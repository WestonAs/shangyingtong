package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.RiskMarginRegDAO;

@Repository
public class RiskMarginRegDAOImpl extends BaseDAOIbatisImpl implements RiskMarginRegDAO {

    public String getNamespace() {
        return "RiskMarginReg";
    }

	public Paginater findRiskMarginReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		
		return this.queryForPage("findRiskMarginReg", params, pageNumber, pageSize);
	}
}