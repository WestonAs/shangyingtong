package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.IcRenewCardRegDAO;
import gnete.card.entity.IcRenewCardReg;

@Repository
public class IcRenewCardRegDAOImpl extends BaseDAOIbatisImpl implements IcRenewCardRegDAO {

    public String getNamespace() {
        return "IcRenewCardReg";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<IcRenewCardReg> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public IcRenewCardReg findByRandomSessionId(String randomSessionId) {
    	return (IcRenewCardReg) this.queryForObject("findByRandomSessionId", randomSessionId);
    }
}