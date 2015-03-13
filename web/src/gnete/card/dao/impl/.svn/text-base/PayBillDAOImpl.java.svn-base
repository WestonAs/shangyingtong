package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PayBillDAO;
@Repository
public class PayBillDAOImpl extends BaseDAOIbatisImpl implements PayBillDAO {

    public String getNamespace() {
        return "PayBill";
    }

	@Override
	public Paginater findPaginater(Map<String, Object> params, int pageNumber, int pageSize) {
		return super.queryForPage("findPayBillInfo", params, pageNumber, pageSize);
	}
}