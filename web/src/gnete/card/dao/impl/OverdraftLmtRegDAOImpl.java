package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.OverdraftLmtRegDAO;

@Repository
public class OverdraftLmtRegDAOImpl extends BaseDAOIbatisImpl implements OverdraftLmtRegDAO {

    public String getNamespace() {
        return "OverdraftLmtReg";
    }

	public Paginater findOverdraftLmtReg(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findOverdraftLmtReg", params, pageNumber, pageSize);
	}
}