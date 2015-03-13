package gnete.card.entity;

import gnete.card.entity.type.ReleaseCardFeeTransType;

public class ReleaseCardFeeDTotalKey {
    private String branchCode;

    private String cardBin;

    private String feeDate;

    private String merchId;

    private String posManageId;

    private String posProvId;

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

    public String getPosManageId() {
        return posManageId;
    }

    public void setPosManageId(String posManageId) {
        this.posManageId = posManageId;
    }

    public String getPosProvId() {
        return posProvId;
    }

    public void setPosProvId(String posProvId) {
        this.posProvId = posProvId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public String getTransTypeName() {
    	return ReleaseCardFeeTransType.ALL.get(this.transType) == null ? "" : ReleaseCardFeeTransType.valueOf(this.transType).getName();
//		return CardMerchFeeTransType.ALL.get(this.transType) == null ? "" : CardMerchFeeTransType.valueOf(this.transType).getName();
	}
}