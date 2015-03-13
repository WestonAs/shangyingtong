package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.RechargeBillDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class RechargeBillDAOImpl extends BaseDAOIbatisImpl implements RechargeBillDAO {

    public String getNamespace() {
        return "RechargeBill";
    }

	@Override
	public Paginater findRechargeInfo(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findRechargeInfo", params, pageNumber, pageSize);
	}
}