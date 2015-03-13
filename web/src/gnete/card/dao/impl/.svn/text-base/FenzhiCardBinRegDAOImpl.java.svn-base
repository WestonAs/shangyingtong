package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.FenzhiCardBinRegDAO;

@Repository
public class FenzhiCardBinRegDAOImpl extends BaseDAOIbatisImpl implements FenzhiCardBinRegDAO {

    public String getNamespace() {
        return "FenzhiCardBinReg";
    }
    
    public Paginater findFenzhiCardBinRegPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findFenzhiCardBinRegPage", params, pageNumber, pageSize);
    }
}