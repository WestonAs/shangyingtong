package gnete.card.entity;

import gnete.card.entity.type.TransType;

public class TransLimitKey {
    private String cardBin;

    private String cardIssuer;

    private String merNo;

    private String transType;

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public String getTransTypeName() {
		return TransType.ALL.get(this.transType) == null ? "" : TransType
				.valueOf(this.transType).getName();
	}
}