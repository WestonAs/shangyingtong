package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.TransBillDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class TransBillDAOImpl extends BaseDAOIbatisImpl implements TransBillDAO {

    public String getNamespace() {
        return "TransBill";
    }

	@Override
	public Paginater findPaginater(Map<String, Object> params, int pageNumber, int pageSize) {
		return super.queryForPage("findTransBillInfo", params, pageNumber, pageSize);
	}
}