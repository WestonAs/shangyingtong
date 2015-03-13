package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.FenZhiCardBinMgrDAO;

@Repository
public class FenZhiCardBinMgrDAOImpl extends BaseDAOIbatisImpl implements FenZhiCardBinMgrDAO {

    public String getNamespace() {
        return "FenZhiCardBinMgr";
    }
    
    public Paginater findFenzhiCardBinPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findFenzhiCardBinPage", params, pageNumber, pageSize);
    }
    
    public String findMinAbleCardBin(String currentBranch) {
    	return (String) this.queryForObject("findMinAbleCardBin", currentBranch);
    }
}