package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WithDrawBillDAO;
@Repository
public class WithDrawBillDAOImpl extends BaseDAOIbatisImpl implements WithDrawBillDAO {

    public String getNamespace() {
        return "WithDrawBill";
    }

	@Override
	public Paginater findPaginater(Map<String, Object> params, int pageNumber, int pageSize) {
		return super.queryForPage("findWithdrawInfo", params, pageNumber, pageSize);
	}
}