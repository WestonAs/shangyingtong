package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.RiskMerchDAO;

@Repository
public class RiskMerchDAOImpl extends BaseDAOIbatisImpl implements RiskMerchDAO {

    public String getNamespace() {
        return "RiskMerch";
    }

	public Paginater findRiskMerch(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findRiskMerch", params, pageNumber, pageSize);
	}
}