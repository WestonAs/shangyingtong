package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DiscntProtclDefDAO;

@Repository
public class DiscntProtclDefDAOImpl extends BaseDAOIbatisImpl implements
		DiscntProtclDefDAO {

	public String getNamespace() {
		return "DiscntProtclDef";
	}

	public Paginater findDiscntProtclDefPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return queryForPage("findDiscntProtclDefPage", params, pageNumber,
				pageSize);
	}
	
	public int updateStatus(Map<String, Object> params) {
		return super.update("updateStatus", params);
	}
}