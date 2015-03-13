package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.RetransCardRegDAO;
import gnete.card.entity.RetransCardReg;

@Repository
public class RetransCardRegDAOImpl extends BaseDAOIbatisImpl implements RetransCardRegDAO {

    public String getNamespace() {
        return "RetransCardReg";
    }

	public Paginater findRetransCardReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		
		return this.queryForPage("findRetransCardReg", params, pageNumber, pageSize);
	}
	
	public List<RetransCardReg> findRetransCardReg(Map<String, Object> params) {
		
		return this.queryForList("findRetransCardReg", params);
	}
}