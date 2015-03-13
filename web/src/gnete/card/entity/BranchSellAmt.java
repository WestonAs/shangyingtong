package gnete.card.entity;

import gnete.card.entity.type.SellType;

import java.math.BigDecimal;

public class BranchSellAmt extends BranchSellAmtKey {
    private String sellType;

    private BigDecimal trustAmt;

    private BigDecimal riskAmt;

    private BigDecimal unsettleAmt;

    private BigDecimal availableAmt;

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public BigDecimal getTrustAmt() {
        return trustAmt;
    }

    public void setTrustAmt(BigDecimal trustAmt) {
        this.trustAmt = trustAmt;
    }

    public BigDecimal getRiskAmt() {
        return riskAmt;
    }

    public void setRiskAmt(BigDecimal riskAmt) {
        this.riskAmt = riskAmt;
    }

    public BigDecimal getUnsettleAmt() {
        return unsettleAmt;
    }

    public void setUnsettleAmt(BigDecimal unsettleAmt) {
        this.unsettleAmt = unsettleAmt;
    }

    public BigDecimal getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(BigDecimal availableAmt) {
        this.availableAmt = availableAmt;
    }
    
    public String getSellTypeName(){
		return SellType.ALL.get(this.sellType) == null ? "" : SellType.valueOf(this.sellType).getName();
	}
    
}