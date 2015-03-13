package gnete.card.entity;

import gnete.card.entity.state.TransPointDtlProcState;
import gnete.card.entity.type.TransDtlType;

import java.math.BigDecimal;
import java.util.Date;

public class TransPointDtl extends TransPointDtlKey {
    private String transType;

    private BigDecimal beginPoint;

    private BigDecimal endPoint;

    private BigDecimal ptCur;

    private String procStatus;

    private BigDecimal ptCouponAmt;

    private Date updateTime;

    private String settDate;

    private BigDecimal beginInstmPoint;

    private BigDecimal endInstmPoint;

    private String acctId;

    private String ptClass;

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
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

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
    }
    
    public String getTransTypeName() {
		return TransDtlType.ALL.get(this.transType) == null ? "" : TransDtlType.valueOf(this.transType).getName();
	}
    
    public String getProcStatusName() {
    	return TransPointDtlProcState.ALL.get(this.procStatus) == null ? "" : TransPointDtlProcState.valueOf(this.procStatus).getName();
    }

	public BigDecimal getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(BigDecimal beginPoint) {
		this.beginPoint = beginPoint;
	}

	public BigDecimal getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(BigDecimal endPoint) {
		this.endPoint = endPoint;
	}

	public BigDecimal getPtCur() {
		return ptCur;
	}

	public void setPtCur(BigDecimal ptCur) {
		this.ptCur = ptCur;
	}

	public BigDecimal getPtCouponAmt() {
		return ptCouponAmt;
	}

	public void setPtCouponAmt(BigDecimal ptCouponAmt) {
		this.ptCouponAmt = ptCouponAmt;
	}

	public BigDecimal getBeginInstmPoint() {
		return beginInstmPoint;
	}

	public void setBeginInstmPoint(BigDecimal beginInstmPoint) {
		this.beginInstmPoint = beginInstmPoint;
	}

	public BigDecimal getEndInstmPoint() {
		return endInstmPoint;
	}

	public void setEndInstmPoint(BigDecimal endInstmPoint) {
		this.endInstmPoint = endInstmPoint;
	}
}