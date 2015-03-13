package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.MerchInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MerchInfoDAOImpl extends BaseDAOIbatisImpl implements MerchInfoDAO {

    public String getNamespace() {
        return "MerchInfo";
    }
    
    public String getMerchId(String merchIdPrev) {
    	return (String) this.queryForObject("getMerchId", merchIdPrev + '%');
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<MerchInfo> find(Map<String, Object> params) {
    	return this.queryForList("find", params);
    }
    
    public List<MerchInfo> findByManage(String branchNo) {
    	return this.queryForList("findByManage", branchNo);
    }

	public List<MerchInfo> findFranchMerchList(String branchCode) {
		return this.queryForList("findFranchMerchList", branchCode);
	}

	@Override
	public boolean isDirectManagedBy(String merchId, String manageBranchCode) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("merchId", merchId);
    	params.put("manageBranchCode", manageBranchCode);
    	return (Long) this.queryForObject("isDirectManagedBy", params) > 0;
	}
}