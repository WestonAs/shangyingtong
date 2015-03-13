package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BranchSellAmtDAO;

@Repository
public class BranchSellAmtDAOImpl extends BaseDAOIbatisImpl implements BranchSellAmtDAO {

    public String getNamespace() {
        return "BranchSellAmt";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
}