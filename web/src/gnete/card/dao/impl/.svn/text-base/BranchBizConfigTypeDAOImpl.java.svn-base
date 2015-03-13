package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BranchBizConfigTypeDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BranchBizConfigTypeDAOImpl extends BaseDAOIbatisImpl implements BranchBizConfigTypeDAO {

    public String getNamespace() {
        return "BranchBizConfigType";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
}