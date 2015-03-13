package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.IcEcashDepositRegDAO;
import gnete.card.entity.IcEcashDepositReg;

@Repository
public class IcEcashDepositRegDAOImpl extends BaseDAOIbatisImpl implements IcEcashDepositRegDAO {

    public String getNamespace() {
        return "IcEcashDepositReg";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public IcEcashDepositReg findByRandomSessionid(String randomSessionId) {
    	return (IcEcashDepositReg) this.queryForObject("findByRandomSessionid", randomSessionId);
    }
}