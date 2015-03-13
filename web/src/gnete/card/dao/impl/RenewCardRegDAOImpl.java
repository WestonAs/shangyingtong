package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.entity.RenewCardReg;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RenewCardRegDAOImpl extends BaseDAOIbatisImpl implements RenewCardRegDAO {

    public String getNamespace() {
		return "RenewCardReg";
	}
    
    public Paginater findRenewCard(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findRenewCard", params, pageNumber, pageSize);
    }

	public List<RenewCardReg> findRenewCardList(Map<String, Object> params) {
		return this.queryForList("findRenewCard", params);
	}
}