package gnete.card.entity;

import gnete.card.entity.state.WxRecocitionState;
import gnete.card.entity.type.WxReconOpeType;

import java.math.BigDecimal;
import java.util.Date;

public class WxDepositReconReg {
    private Long seqId;

    private Long reconDetailId;

    private String opeType;

    private String cardId;

    private String issNo;

    private String extCardId;

    private String status;

    private BigDecimal transAmt;

    private String updateUser;

    private Date updateTime;

    private String remark;

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public Long getReconDetailId() {
        return reconDetailId;
    }

    public void setReconDetailId(Long reconDetailId) {
        this.reconDetailId = reconDetailId;
    }

    public String getOpeType() {
        return opeType;
    }

    public void setOpeType(String opeType) {
        this.opeType = opeType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIssNo() {
        return issNo;
    }

    public void setIssNo(String issNo) {
        this.issNo = issNo;
    }

    public String getExtCardId() {
        return extCardId;
    }

    public void setExtCardId(String extCardId) {
        this.extCardId = extCardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
    
    public String getOpeTypeName() {
    	return WxReconOpeType.ALL.get(this.opeType) == null ? "" : WxReconOpeType.valueOf(this.opeType).getName();
    }
    
    public String getStatusName() {
    	return WxRecocitionState.ALL.get(this.status) == null ? "" : WxRecocitionState.valueOf(this.status).getName();
    }
}