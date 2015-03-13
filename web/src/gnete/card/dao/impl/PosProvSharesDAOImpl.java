package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.PosProvSharesDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class PosProvSharesDAOImpl extends BaseDAOIbatisImpl implements PosProvSharesDAO {

    public String getNamespace() {
        return "PosProvShares";
    }
    
    public Paginater findPosProvShares(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPosProvShares", params, pageNumber, pageSize);
    }
}