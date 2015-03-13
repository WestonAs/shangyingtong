package gnete.card.entity;

import java.math.BigDecimal;

public class ExchangeRate extends ExchangeRateKey {
    private String currCode2;

    private String msgrate;

    private String msgrevrate;

    private BigDecimal rate;

    private BigDecimal revrate;

    private String workDate;

    private String reserved;

    private String status;

    private String trancurr;

    private String holdercurr;

    public String getCurrCode2() {
        return currCode2;
    }

    public void setCurrCode2(String currCode2) {
        this.currCode2 = currCode2;
    }

    public String getMsgrate() {
        return msgrate;
    }

    public void setMsgrate(String msgrate) {
        this.msgrate = msgrate;
    }

    public String getMsgrevrate() {
        return msgrevrate;
    }

    public void setMsgrevrate(String msgrevrate) {
        this.msgrevrate = msgrevrate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRevrate() {
        return revrate;
    }

    public void setRevrate(BigDecimal revrate) {
        this.revrate = revrate;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrancurr() {
        return trancurr;
    }

    public void setTrancurr(String trancurr) {
        this.trancurr = trancurr;
    }

    public String getHoldercurr() {
        return holdercurr;
    }

    public void setHoldercurr(String holdercurr) {
        this.holdercurr = holdercurr;
    }
}