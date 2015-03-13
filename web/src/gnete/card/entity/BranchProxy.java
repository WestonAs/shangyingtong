package gnete.card.entity;

import gnete.card.entity.state.ProxyState;

import java.util.Date;

public class BranchProxy extends BranchProxyKey {

    private String status;

    private String updateBy;

    private Date updateTime;
    
    // 非表字段
    private String proxyName;
    private String respName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getStatusName(){
    	return ProxyState.ALL.get(this.status) == null? "" : ProxyState.valueOf(this.status).getName();
    }

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
	}
}