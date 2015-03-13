package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BranchBizConfigDAO;
import gnete.card.entity.BranchBizConfig;
import gnete.card.entity.BranchBizConfigKey;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BranchBizConfigDAOImpl extends BaseDAOIbatisImpl implements BranchBizConfigDAO {

    public String getNamespace() {
        return "BranchBizConfig";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }

	@Override
	public BranchBizConfig findByPk(String branchCode, String configType) {
		BranchBizConfigKey key = new BranchBizConfigKey();
		key.setBranchCode(branchCode);
		key.setConfigType(configType);
		return (BranchBizConfig)super.findByPk(key);
	}

	@Override
	public int delete(String branchCode, String configType) {
		BranchBizConfigKey key = new BranchBizConfigKey();
		key.setBranchCode(branchCode);
		key.setConfigType(configType);
		return super.delete(key);
		
	}
}