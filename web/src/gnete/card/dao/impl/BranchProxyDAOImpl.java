package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.BranchProxyDAO;

@Repository
public class BranchProxyDAOImpl extends BaseDAOIbatisImpl implements BranchProxyDAO {

    public String getNamespace() {
        return "BranchProxy";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }

}