package gnete.card.entity;

import gnete.card.entity.type.CardMerchFeeTransType;

public class MerchFeeDTotalKey {
    private String branchCode;

    private String cardBin;

    private String feeDate;

    private String merchId;

    private String transType;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public String getTransTypeName() {
		return CardMerchFeeTransType.ALL.get(this.transType) == null ? "" : CardMerchFeeTransType.valueOf(this.transType).getName();
	}
}