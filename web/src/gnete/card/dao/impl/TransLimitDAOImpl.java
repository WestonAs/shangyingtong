package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.TransLimitDAO;
import gnete.card.entity.TransLimit;

@Repository
public class TransLimitDAOImpl extends BaseDAOIbatisImpl implements TransLimitDAO {

    public String getNamespace() {
        return "TransLimit";
    }

	public Paginater findTransLimit(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findTransLimit", params, pageNumber, pageSize);
	}

	public List<TransLimit> getTransLimitList(Map<String, Object> params) {
		return this.queryForList("getTransLimitList", params);
	}
}