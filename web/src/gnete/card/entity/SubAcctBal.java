package gnete.card.entity;

import java.math.BigDecimal;

public class SubAcctBal extends SubAcctBalKey {
    private String cardIssuer;

    private BigDecimal avlbBal;

    private BigDecimal fznAmt;

    private String effDate;

    private String expirDate;

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public BigDecimal getAvlbBal() {
        return avlbBal;
    }

    public void setAvlbBal(BigDecimal avlbBal) {
        this.avlbBal = avlbBal;
    }

    public BigDecimal getFznAmt() {
        return fznAmt;
    }

    public void setFznAmt(BigDecimal fznAmt) {
        this.fznAmt = fznAmt;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }
}