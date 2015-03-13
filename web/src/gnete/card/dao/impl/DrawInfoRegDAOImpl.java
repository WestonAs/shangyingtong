package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.DrawInfoRegDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class DrawInfoRegDAOImpl extends BaseDAOIbatisImpl implements DrawInfoRegDAO {

    public String getNamespace() {
        return "DrawInfoReg";
    }

	public Paginater findDrawInfoReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findDrawInfoReg", params, pageNumber, pageSize);
	}
}