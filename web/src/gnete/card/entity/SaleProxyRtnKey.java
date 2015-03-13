package gnete.card.entity;

import gnete.card.entity.type.SaleProxyRtnTransType;

import java.math.BigDecimal;

public class SaleProxyRtnKey {
	private String branchCode;

    private String proxyId;

    private String transType;

    private BigDecimal ulMoney;
    
    private String cardBin;
    
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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getTransTypeName() {
		return SaleProxyRtnTransType.valueOf(transType).getName();
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
}