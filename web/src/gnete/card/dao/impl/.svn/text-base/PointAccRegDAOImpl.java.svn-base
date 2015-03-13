package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PointAccRegDAO;

@Repository
public class PointAccRegDAOImpl extends BaseDAOIbatisImpl implements PointAccRegDAO {

    public String getNamespace() {
        return "PointAccReg";
    }

	public Paginater findPointAccReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPointAccReg", params, pageNumber, pageSize);
	}
}