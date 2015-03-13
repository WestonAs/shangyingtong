package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SignCardAccListDAO;

@Repository
public class SignCardAccListDAOImpl extends BaseDAOIbatisImpl implements SignCardAccListDAO {

    public String getNamespace() {
        return "SignCardAccList";
    }
    
    public Paginater findSignCardAccList(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findSignCardAccList", params, pageNumber, pageSize);
    }
}