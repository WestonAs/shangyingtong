package gnete.card.entity;

import java.util.Date;

public class AccountFreezeBill {
    private String no;

    private String systemId;

    private String custId;

    private String accountId;

    private String freezeNote;

    private String state;

    private Date freezeDate;

    private Date unfreezeDate;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public String getFreezeNote() {
        return freezeNote;
    }

    public void setFreezeNote(String freezeNote) {
        this.freezeNote = freezeNote;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    public Date getUnfreezeDate() {
        return unfreezeDate;
    }

    public void setUnfreezeDate(Date unfreezeDate) {
        this.unfreezeDate = unfreezeDate;
    }
}