package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.WashCarActivityRecordDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WashCarActivityRecordDAOImpl extends BaseDAOIbatisImpl implements WashCarActivityRecordDAO {

	@Override
	protected String getNamespace() {
		return "WashCarActivityRecord";
	}

	@Override
	public Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		
		return this.queryForPage("findPage", params, pageNumber, pageSize);
	}
}