package gnete.card.entity;

import gnete.card.entity.type.RebateFromType;

import java.math.BigDecimal;

public class RebateCardReg {
    private Long regId;

    private String rebateFrom;

    private Long batchId;

    private String cardId;

    private BigDecimal rebateAmt;

    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    public String getRebateFrom() {
        return rebateFrom;
    }

    public void setRebateFrom(String rebateFrom) {
        this.rebateFrom = rebateFrom;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getRebateAmt() {
        return rebateAmt;
    }

    public void setRebateAmt(BigDecimal rebateAmt) {
        this.rebateAmt = rebateAmt;
    }
    
    public String getRebateFromName() {
    	return RebateFromType.ALL.get(this.rebateFrom) == null ? "" : RebateFromType.valueOf(this.rebateFrom).getName();
    }
}