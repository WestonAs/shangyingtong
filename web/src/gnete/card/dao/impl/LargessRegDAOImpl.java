package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.LargessRegDAO;
@Repository
public class LargessRegDAOImpl extends BaseDAOIbatisImpl implements LargessRegDAO {

    public String getNamespace() {
        return "LargessReg";
    }

	public Paginater findLargessReg(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findLargessReg", params, pageNumber, pageSize);
	}
}