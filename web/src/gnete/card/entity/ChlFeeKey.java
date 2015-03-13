package gnete.card.entity;

import gnete.card.entity.type.ChlFeeContentType;
import java.math.BigDecimal;

public class ChlFeeKey {
    private String branchCode;

    private String feeContent;

    private BigDecimal ulMoney;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFeeContent() {
        return feeContent;
    }

    public void setFeeContent(String feeContent) {
        this.feeContent = feeContent;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }
    
    public String getFeeContentName() {
		return ChlFeeContentType.ALL.get(this.feeContent) == null ? "" : ChlFeeContentType.valueOf(this.feeContent).getName();
	}
}