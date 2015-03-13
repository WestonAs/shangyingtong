package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BranchParaDAO;

@Repository
public class BranchParaDAOImpl extends BaseDAOIbatisImpl implements BranchParaDAO {

    public String getNamespace() {
        return "BranchPara";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
}