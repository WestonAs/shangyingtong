package gnete.card.entity;

import gnete.card.entity.type.CardMerchFeeTransType;
import java.math.BigDecimal;

public class MerchFeeTemplateDetailKey {
    private String cardBin;

    private Long templateId;

    private String transType;

    private BigDecimal ulMoney;

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }
    
    public String getTransTypeName(){
    	return CardMerchFeeTransType.ALL.get(this.transType) == null ? "" : CardMerchFeeTransType
				.valueOf(this.transType).getName();
	}
}