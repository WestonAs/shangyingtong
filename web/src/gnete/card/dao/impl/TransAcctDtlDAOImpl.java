package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.TransAcctDtlDAO;

@Repository
public class TransAcctDtlDAOImpl extends BaseDAOIbatisImpl implements TransAcctDtlDAO {

    public String getNamespace() {
        return "TransAcctDtl";
    }

	public Paginater findTransAcctDtl(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTransAcctDtl", params, pageNumber, pageSize);
	}
}