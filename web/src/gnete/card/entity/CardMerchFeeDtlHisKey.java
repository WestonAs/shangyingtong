package gnete.card.entity;

import java.math.BigDecimal;

public class CardMerchFeeDtlHisKey {
    private Long feeRuleId;

    private Long id;

    private BigDecimal ulMoney;

    public Long getFeeRuleId() {
        return feeRuleId;
    }

    public void setFeeRuleId(Long feeRuleId) {
        this.feeRuleId = feeRuleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }
}