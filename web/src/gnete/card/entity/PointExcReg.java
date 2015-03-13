package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;
import java.math.BigDecimal;
import java.util.Date;

public class PointExcReg {
    private Long pointExcId;

    private String acctId;
    
    private String cardId;
    
    private String jinstType;

	private String jinstId;

    private String ptClass;

    private BigDecimal ptRef;

    private BigDecimal excPoint;

    private BigDecimal excAmt;

    private BigDecimal ptDiscntRate;

    private String status;

    private String remark;

    private String updateBy;

    private Date updateTime;
    
    private String branchCode;
    
    private String ptClassName;
    
    public Long getPointExcId() {
        return pointExcId;
    }

    public void setPointExcId(Long pointExcId) {
        this.pointExcId = pointExcId;
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

    public BigDecimal getPtRef() {
        return ptRef;
    }

    public void setPtRef(BigDecimal ptRef) {
        this.ptRef = ptRef;
    }

    public BigDecimal getExcPoint() {
        return excPoint;
    }

    public void setExcPoint(BigDecimal excPoint) {
        this.excPoint = excPoint;
    }

    public BigDecimal getExcAmt() {
        return excAmt;
    }

    public void setExcAmt(BigDecimal excAmt) {
        this.excAmt = excAmt;
    }

    public BigDecimal getPtDiscntRate() {
        return ptDiscntRate;
    }

    public void setPtDiscntRate(BigDecimal ptDiscntRate) {
        this.ptDiscntRate = ptDiscntRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    
    public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}
    
    public String getJinstTypeName(){
		return IssType.ALL.get(this.jinstType) == null ? "" : IssType.valueOf(this.jinstType).getName();
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getJinstType() {
		return jinstType;
	}

	public void setJinstType(String jinstType) {
		this.jinstType = jinstType;
	}

	public String getJinstId() {
		return jinstId;
	}

	public void setJinstId(String jinstId) {
		this.jinstId = jinstId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}

}