package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BusinessSubAccountInfoDAO;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class BusinessSubAccountInfoDAOImpl extends BaseDAOIbatisImpl implements BusinessSubAccountInfoDAO {

    public String getNamespace() {
        return "BusinessSubAccountInfo";
    }
    
    public Paginater findBusinessSubAccountInfo(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findBusinessSubAccountInfo", params, pageNumber, pageSize);
    }

	@Override
	public List findAcctInfo(Map<String, Object> params) {
		return this.queryForList("findBusinessSubAccountInfo", params);
	}
}