package gnete.card.entity;

import java.math.BigDecimal;

public class ReleaseCardFeeDtlHis extends ReleaseCardFeeDtlHisKey {
    private BigDecimal feeRate;

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }
}