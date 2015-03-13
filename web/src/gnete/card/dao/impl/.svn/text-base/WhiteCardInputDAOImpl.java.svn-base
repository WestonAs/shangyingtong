package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WhiteCardInputDAO;

@Repository
public class WhiteCardInputDAOImpl extends BaseDAOIbatisImpl implements
		WhiteCardInputDAO {

	public String getNamespace() {
		return "WhiteCardInput";
	}

	public Paginater findWhiteCardInputPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findWhiteCardInputPage", params, pageNumber,
				pageSize);
	}
}