package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.AdjAccRegDAO;

@Repository
public class AdjAccRegDAOImpl extends BaseDAOIbatisImpl implements AdjAccRegDAO {

    public String getNamespace() {
        return "AdjAccReg";
    }

	public Paginater findAdjAccReg(Map<String, Object> params, int pageNumber,
			int pageSize) {
		
		return this.queryForPage("findAdjAccReg", params, pageNumber, pageSize);
	}
}