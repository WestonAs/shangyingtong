package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.ReleaseCardFeeDTotalDAO;

@Repository
public class ReleaseCardFeeDTotalDAOImpl extends BaseDAOIbatisImpl implements ReleaseCardFeeDTotalDAO {

    public String getNamespace() {
        return "ReleaseCardFeeDTotal";
    }
    
    public Paginater findReleaseCardFeeDTotal(Map<String, Object> params, int pageNumber, int pageSize){
    	return this.queryForPage("findReleaseCardFeeDTotal", params, pageNumber, pageSize);
    }

	public Paginater findBranchSharesInfo(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findBranchSharesInfo", params, pageNumber, pageSize);
	}

	public Paginater findCenterProxySharesInfo(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCenterProxySharesInfo", params, pageNumber, pageSize);
	}

	public Paginater findPosManageSharesInfo(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPosManageSharesInfo", params, pageNumber, pageSize);
	}

	public Paginater findPosProvSharesInfo(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPosProvSharesInfo", params, pageNumber, pageSize);
	}
	
}