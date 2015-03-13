package gnete.card.entity;

import gnete.card.entity.state.TransAcctDtlProcState;
import gnete.card.entity.type.TransDtlType;
import java.math.BigDecimal;
import java.util.Date;

public class TransAcctDtl extends TransAcctDtlKey {
    private String transType;

    private BigDecimal beginBal;

    private BigDecimal dedAmt;

    private BigDecimal endBal;

    private String procStatus;

    private Date updateTime;

    private String settDate;

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public BigDecimal getBeginBal() {
        return beginBal;
    }

    public void setBeginBal(BigDecimal beginBal) {
        this.beginBal = beginBal;
    }

    public BigDecimal getDedAmt() {
        return dedAmt;
    }

    public void setDedAmt(BigDecimal dedAmt) {
        this.dedAmt = dedAmt;
    }

    public BigDecimal getEndBal() {
        return endBal;
    }

    public void setEndBal(BigDecimal endBal) {
        this.endBal = endBal;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }
    
    public String getTransTypeName() {
		return TransDtlType.ALL.get(this.transType) == null ? this.transType : TransDtlType.valueOf(this.transType).getName();
	}
    
    public String getProcStatusName() {
    	return TransAcctDtlProcState.ALL.get(this.procStatus) == null ? "" : TransAcctDtlProcState.valueOf(this.procStatus).getName();
    }
}