package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BranchSharesDAO;

@Repository
public class BranchSharesDAOImpl extends BaseDAOIbatisImpl implements BranchSharesDAO {

    public String getNamespace() {
        return "BranchShares";
    }
    
    public Paginater findBranchShares(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findBranchShares", params, pageNumber, pageSize);
    }
}