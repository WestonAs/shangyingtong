package gnete.card.entity;

import java.util.Date;

public class SubAccountBindCzCard extends SubAccountBindCzCardKey {
    private String custId;

    private Date cardBindTime;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getCardBindTime() {
        return cardBindTime;
    }

    public void setCardBindTime(Date cardBindTime) {
        this.cardBindTime = cardBindTime;
    }
}