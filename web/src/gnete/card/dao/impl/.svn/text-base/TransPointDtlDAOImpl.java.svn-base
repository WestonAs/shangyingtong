package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.TransPointDtlDAO;

@Repository
public class TransPointDtlDAOImpl extends BaseDAOIbatisImpl implements TransPointDtlDAO {

    public String getNamespace() {
        return "TransPointDtl";
    }

	public Paginater findTransPointDtl(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTransPointDtl", params, pageNumber, pageSize);
	}
}