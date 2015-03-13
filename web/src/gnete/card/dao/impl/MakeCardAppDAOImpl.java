package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MakeCardAppDAO;

@Repository
public class MakeCardAppDAOImpl extends BaseDAOIbatisImpl implements
		MakeCardAppDAO {

	public String getNamespace() {
		return "MakeCardApp";
	}

	public Paginater findMakeCardAppPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMakeCardAppPage", params, pageNumber,
				pageSize);
	}

	public boolean isExistAppId(Map<String, Object> params) {
		return (Long)this.queryForObject("isExistAppId", params) != 0L;
	}
}