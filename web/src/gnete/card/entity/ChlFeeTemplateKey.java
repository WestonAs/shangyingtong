package gnete.card.entity;

import gnete.card.entity.type.ChlFeeContentType;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;

public class ChlFeeTemplateKey{
    private Long templateId;

    private String feeContent;

    private BigDecimal ulMoney;


    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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