package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.entity.SaleProxyPrivilege;

import org.springframework.stereotype.Repository;

@Repository
public class SaleProxyPrivilegeDAOImpl extends BaseDAOIbatisImpl implements SaleProxyPrivilegeDAO {

    public String getNamespace() {
        return "SaleProxyPrivilege";
    }
    
    public int deleteByBranch(String proxyBranch, String cardBranch) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("proxyBranch", proxyBranch);
    	params.put("cardBranch", cardBranch);
    	return this.delete("deleteByBranch", params);
    }

	public List<SaleProxyPrivilege> findSaleProxyPrivilege(
			Map<String, Object> params) {
		return this.queryForList("findSaleProxyPrivilege", params);
	}
	
	public List<HashMap> findSaleProxy(String proxyId, String cardBranch, String limitId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("proxyBranch", proxyId);
    	params.put("cardBranch", cardBranch);
    	params.put("limitId", limitId);
		return this.queryForList("findSaleProxy", params);
	}
    
}