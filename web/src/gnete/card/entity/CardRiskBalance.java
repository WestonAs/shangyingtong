package gnete.card.entity;

import java.math.BigDecimal;

public class CardRiskBalance {
    private String branchCode;

    private BigDecimal trustAmt;

    private BigDecimal riskAmt;

    private BigDecimal sellAmt;

    private BigDecimal settleAmt;

    private BigDecimal availableAmt;
    
    private String branchName;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public BigDecimal getSellAmt() {
        return sellAmt;
    }

    public void setSellAmt(BigDecimal sellAmt) {
        this.sellAmt = sellAmt;
    }

    public BigDecimal getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(BigDecimal settleAmt) {
        this.settleAmt = settleAmt;
    }

    public BigDecimal getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(BigDecimal availableAmt) {
        this.availableAmt = availableAmt;
    }

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}