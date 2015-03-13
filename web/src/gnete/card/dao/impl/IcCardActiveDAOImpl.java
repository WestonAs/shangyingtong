package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.IcCardActiveDAO;
import gnete.card.entity.IcCardActive;

@Repository
public class IcCardActiveDAOImpl extends BaseDAOIbatisImpl implements IcCardActiveDAO {

    public String getNamespace() {
        return "IcCardActive";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public IcCardActive findByRandomSessionid(String randomSessionid) {
    	return (IcCardActive) this.queryForObject("findByRandomSessionid", randomSessionid);
    }
}