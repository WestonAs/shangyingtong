package gnete.card.entity;

import java.math.BigDecimal;

public class CenterProxySharesKey {
	private String branchCode;
	
    private String proxyId;

    private BigDecimal ulMoney;

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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}