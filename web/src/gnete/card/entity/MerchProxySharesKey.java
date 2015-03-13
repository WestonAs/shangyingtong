package gnete.card.entity;

import java.math.BigDecimal;

public class MerchProxySharesKey {
	 private String branchCode;

    private String merchId;

    private String proxyId;

    private BigDecimal ulMoney;
    
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
}