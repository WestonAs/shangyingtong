package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.AwardRegDAO;

@Repository
public class AwardRegDAOImpl extends BaseDAOIbatisImpl implements AwardRegDAO {

    public String getNamespace() {
        return "AwardReg";
    }

	public Paginater findAwardRegCusCred(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findAwardRegCusCred", params, pageNumber, pageSize);
	}
	
	public Paginater findAwardReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findAwardReg", params, pageNumber, pageSize);
	}
}