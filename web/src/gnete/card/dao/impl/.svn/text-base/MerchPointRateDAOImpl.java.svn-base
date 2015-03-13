package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchPointRateDAO;

@Repository
public class MerchPointRateDAOImpl extends BaseDAOIbatisImpl implements MerchPointRateDAO {

	public String getNamespace() {
		return "MerchPointRate";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMerchPointRate", params, pageNumber, pageSize);
	}
}