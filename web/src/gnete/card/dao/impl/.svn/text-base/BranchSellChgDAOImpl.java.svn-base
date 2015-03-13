package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BranchSellChgDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BranchSellChgDAOImpl extends BaseDAOIbatisImpl implements BranchSellChgDAO {

    public String getNamespace() {
        return "BranchSellChg";
    }
    
    public Paginater findByBranch(Map<String, Object> params, int pageNumber, int pageSize) {
    	return this.queryForPage("findByBranch", params, pageNumber, pageSize);
    }
}