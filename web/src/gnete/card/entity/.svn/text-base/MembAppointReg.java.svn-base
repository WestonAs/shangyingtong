package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.SingleBatchType;

import java.util.Date;

public class MembAppointReg {
    private Long membAppointRegId;

    private Long membInfoRegId;

    private String appointType;

    private String cardId;

    private String startCardId;

    private String endCardId;

    private String status;

    private Long saleBatchId;

    private String cardIssuer;

    private String updateBy;

    private Date updateTime;

    private String remark;

    public Long getMembAppointRegId() {
        return membAppointRegId;
    }

    public void setMembAppointRegId(Long membAppointRegId) {
        this.membAppointRegId = membAppointRegId;
    }

    public Long getMembInfoRegId() {
        return membInfoRegId;
    }

    public void setMembInfoRegId(Long membInfoRegId) {
        this.membInfoRegId = membInfoRegId;
    }

    public String getAppointType() {
        return appointType;
    }

    public void setAppointType(String appointType) {
        this.appointType = appointType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getStartCardId() {
        return startCardId;
    }

    public void setStartCardId(String startCardId) {
        this.startCardId = startCardId;
    }

    public String getEndCardId() {
        return endCardId;
    }

    public void setEndCardId(String endCardId) {
        this.endCardId = endCardId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getAppointTypeName(){
    	 return this.appointType == null ? "":SingleBatchType.valueOf(this.appointType).getName();
    }
    
    public String getStatusName() {
        return this.status == null ? "":RegisterState.valueOf(this.status).getName();
    }
}