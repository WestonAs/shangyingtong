package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PosManageSharesDAO;
@Repository
public class PosManageSharesDAOImpl extends BaseDAOIbatisImpl implements PosManageSharesDAO {

    public String getNamespace() {
        return "PosManageShares";
    }
    
    public Paginater findPosManageShares(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPosManageShares", params, pageNumber, pageSize);
    }
}