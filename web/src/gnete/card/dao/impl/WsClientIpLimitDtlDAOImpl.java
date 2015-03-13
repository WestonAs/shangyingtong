package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.WsClientIpLimitDtlDAO;
import gnete.card.entity.WsClientIpLimitDtl;
import gnete.card.entity.WsClientIpLimitDtlKey;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WsClientIpLimitDtlDAOImpl extends BaseDAOIbatisImpl implements WsClientIpLimitDtlDAO {

    public String getNamespace() {
        return "WsClientIpLimitDtl";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }

	@Override
	public WsClientIpLimitDtl findByPk(String branchCode, String ip) {
		WsClientIpLimitDtlKey key = new WsClientIpLimitDtlKey();
		key.setBranchCode(branchCode);
		key.setIp(ip);
		return (WsClientIpLimitDtl)super.findByPk(key);
	}

	@Override
	public void delete(String branchCode, String ip) {
		WsClientIpLimitDtlKey key = new WsClientIpLimitDtlKey();
		key.setBranchCode(branchCode);
		key.setIp(ip);
		super.delete(key);
		
	}
}