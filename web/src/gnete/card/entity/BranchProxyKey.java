package gnete.card.entity;

import gnete.card.entity.type.ProxyType;

public class BranchProxyKey {
    private String proxyId;

    private String respOrg;
    
    private String proxyType;
    
    public BranchProxyKey(String proxyId, String respOrg, String proxyType) {
		super();
		this.proxyId = proxyId;
		this.respOrg = respOrg;
		this.proxyType = proxyType;
	}

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

	public BranchProxyKey() {
	}

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    public String getRespOrg() {
        return respOrg;
    }

    public void setRespOrg(String respOrg) {
        this.respOrg = respOrg;
    }

    public String getProxyTypeName(){
    	return ProxyType.ALL.get(this.proxyType) == null? "" : ProxyType.valueOf(this.proxyType).getName();
    }
}