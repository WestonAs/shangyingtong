package gnete.card.entity;

import gnete.card.entity.state.CardMerchState;

import java.util.Date;

public class CardToMerch extends CardToMerchKey {
    private String proxyId;

    private String status;

    private String updateBy;

    private Date updateTime;
    
    private String proxyName;
    
    private String merchName;
   
    private String branchName;

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

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
    	return CardMerchState.ALL.get(this.status) == null? "" : CardMerchState.valueOf(this.status).getName();
    }

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}