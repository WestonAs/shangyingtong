package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SubAccountTypeDAO;
@Repository
public class SubAccountTypeDAOImpl extends BaseDAOIbatisImpl implements SubAccountTypeDAO {

    public String getNamespace() {
        return "SubAccountType";
    }
    
    public Paginater findSubAccountType(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findSubAccountType", params, pageNumber, pageSize);
    }
}