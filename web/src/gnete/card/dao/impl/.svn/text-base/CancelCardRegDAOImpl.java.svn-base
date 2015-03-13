package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CancelCardRegDAO;

@Repository
public class CancelCardRegDAOImpl extends BaseDAOIbatisImpl implements CancelCardRegDAO {

    public String getNamespace() {
        return "CancelCardReg";
    }

	public Paginater findCancelCardReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCancelCardReg", params, pageNumber, pageSize);
	}
}