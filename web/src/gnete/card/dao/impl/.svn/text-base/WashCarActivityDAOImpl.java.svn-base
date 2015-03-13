package gnete.card.dao.impl;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.dao.WashCarActivityDAO;

import org.springframework.stereotype.Repository;

@Repository
public class WashCarActivityDAOImpl extends BaseDAOIbatisImpl implements WashCarActivityDAO {

	@Override
	protected String getNamespace() {
		return "WashCarActivity";
	}

	@Override
	public Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		
		return this.queryForPage("findPage", params, pageNumber, pageSize);
	}
}