package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.IcCancelCardRegDAO;
import gnete.card.entity.IcCancelCardReg;

@Repository
public class IcCancelCardRegDAOImpl extends BaseDAOIbatisImpl implements IcCancelCardRegDAO {

    public String getNamespace() {
        return "IcCancelCardReg";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<IcCancelCardReg> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public IcCancelCardReg findByRandomSessionId(String randomSessionId) {
    	return (IcCancelCardReg) this.queryForObject("findByRandomSessionId", randomSessionId);
    }
}