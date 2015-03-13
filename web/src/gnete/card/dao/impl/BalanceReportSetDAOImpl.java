package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BalanceReportSetDAO;

@Repository
public class BalanceReportSetDAOImpl extends BaseDAOIbatisImpl implements
		BalanceReportSetDAO {

	public String getNamespace() {
		return "BalanceReportSet";
	}

	public Paginater findBalanceReportSetPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findBalanceReportSetPage", params,
				pageNumber, pageSize);
	}
}