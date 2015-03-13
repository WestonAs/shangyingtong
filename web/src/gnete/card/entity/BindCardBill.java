package gnete.card.entity;

import java.util.Date;

public class BindCardBill {
    private String no;

    private String cardNo;

    private String custId;

    private String accountId;

    private String bindType;

    private String changeType;

    private Date cardBindTime;

    private String note;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Date getCardBindTime() {
        return cardBindTime;
    }

    public void setCardBindTime(Date cardBindTime) {
        this.cardBindTime = cardBindTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}