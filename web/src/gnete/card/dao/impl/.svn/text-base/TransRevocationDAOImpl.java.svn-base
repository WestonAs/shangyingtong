package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.TransRevocationDAO;

@Repository
public class TransRevocationDAOImpl extends BaseDAOIbatisImpl implements TransRevocationDAO {

    public String getNamespace() {
        return "TransRevocation";
    }

	public Paginater findTransRevocation(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTransRevocation", params, pageNumber, pageSize);
	}
}