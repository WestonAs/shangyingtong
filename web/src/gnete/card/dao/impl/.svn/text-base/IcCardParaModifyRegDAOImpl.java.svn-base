package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.IcCardParaModifyRegDAO;
import gnete.card.entity.IcCardParaModifyReg;

@Repository
public class IcCardParaModifyRegDAOImpl extends BaseDAOIbatisImpl implements IcCardParaModifyRegDAO {

    public String getNamespace() {
        return "IcCardParaModifyReg";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<IcCardParaModifyReg> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public IcCardParaModifyReg findByRandomSessionId(String randomSessionId) {
    	return (IcCardParaModifyReg) this.queryForObject("findByRandomSessionId", randomSessionId);
    }
}