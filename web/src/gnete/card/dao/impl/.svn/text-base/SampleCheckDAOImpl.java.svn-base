package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SampleCheckDAO;

@Repository
public class SampleCheckDAOImpl extends BaseDAOIbatisImpl implements
		SampleCheckDAO {

	public String getNamespace() {
		return "SampleCheck";
	}

	public Paginater findSampleCheckPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findSampleCheckPage", params, pageNumber,
				pageSize);
	}
}