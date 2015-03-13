package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MerchProxySharesMTotal extends MerchProxySharesMTotalKey {
    private String feeDate;

    private BigDecimal feeRate;

    private BigDecimal amount;

    private BigDecimal merchPayFee;

    private BigDecimal lastFee;

    private BigDecimal merchProxyFee;

    private BigDecimal nextFee;

    private String chkStatus;

    private String updateBy;

    private Date updateTime;

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMerchPayFee() {
        return merchPayFee;
    }

    public void setMerchPayFee(BigDecimal merchPayFee) {
        this.merchPayFee = merchPayFee;
    }

    public BigDecimal getLastFee() {
        return lastFee;
    }

    public void setLastFee(BigDecimal lastFee) {
        this.lastFee = lastFee;
    }

    public BigDecimal getMerchProxyFee() {
        return merchProxyFee;
    }

    public void setMerchProxyFee(BigDecimal merchProxyFee) {
        this.merchProxyFee = merchProxyFee;
    }

    public BigDecimal getNextFee() {
        return nextFee;
    }

    public void setNextFee(BigDecimal nextFee) {
        this.nextFee = nextFee;
    }

    public String getChkStatus() {
        return chkStatus;
    }

    public void setChkStatus(String chkStatus) {
        this.chkStatus = chkStatus;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}