package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BranchSharesMSetDAO;

@Repository
public class BranchSharesMSetDAOImpl extends BaseDAOIbatisImpl implements BranchSharesMSetDAO {

    public String getNamespace() {
        return "BranchSharesMSet";
    }
    
    public Paginater findBranchSharesMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findBranchSharesMSet", params, pageNumber, pageSize);
    }
}