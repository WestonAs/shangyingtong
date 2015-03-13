package gnete.card.entity;

import gnete.card.entity.state.RegisterState;

import java.util.Date;

public class MembAppointDetailReg {
    private Long membAppointDetailRegId;

    private Long membAppointRegId;

    private Long membInfoId;

    private String cardId;

    private String status;

    private Long saleBatchId;

    private String cardIssuer;

    private String updateBy;

    private Date updateTime;

    public Long getMembAppointDetailRegId() {
        return membAppointDetailRegId;
    }

    public void setMembAppointDetailRegId(Long membAppointDetailRegId) {
        this.membAppointDetailRegId = membAppointDetailRegId;
    }

    public Long getMembAppointRegId() {
        return membAppointRegId;
    }

    public void setMembAppointRegId(Long membAppointRegId) {
        this.membAppointRegId = membAppointRegId;
    }

    public Long getMembInfoId() {
        return membInfoId;
    }

    public void setMembInfoId(Long membInfoId) {
        this.membInfoId = membInfoId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSaleBatchId() {
        return saleBatchId;
    }

    public void setSaleBatchId(Long saleBatchId) {
        this.saleBatchId = saleBatchId;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
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
    
    public String getStatusName() {
        return this.status == null ? "":RegisterState.valueOf(this.status).getName();
    }
}