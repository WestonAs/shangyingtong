package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PointExcRegDAO;

@Repository
public class PointExcRegDAOImpl extends BaseDAOIbatisImpl implements PointExcRegDAO {

    public String getNamespace() {
        return "PointExcReg";
    }

	public Paginater findPointExcReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPointExcReg", params, pageNumber, pageSize);
	}
}