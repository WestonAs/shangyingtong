package gnete.card.entity;

import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.AdjDirectionType;

import java.math.BigDecimal;
import java.util.Date;

public class RiskMarginReg {
    private Long earnestRegId;

    private String branchCode;

    private String branchName;

    private BigDecimal remainRiskAmt;

    private String adjDirection;

    private BigDecimal adjAmt;

    private String status;

    private String updateBy;

    private Date updateTime;

    public Long getEarnestRegId() {
        return earnestRegId;
    }

    public void setEarnestRegId(Long earnestRegId) {
        this.earnestRegId = earnestRegId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BigDecimal getRemainRiskAmt() {
        return remainRiskAmt;
    }

    public void setRemainRiskAmt(BigDecimal remainRiskAmt) {
        this.remainRiskAmt = remainRiskAmt;
    }

    public String getAdjDirection() {
        return adjDirection;
    }

    public void setAdjDirection(String adjDirection) {
        this.adjDirection = adjDirection;
    }

    public BigDecimal getAdjAmt() {
        return adjAmt;
    }

    public void setAdjAmt(BigDecimal adjAmt) {
        this.adjAmt = adjAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
		return CheckState.ALL.get(this.status) == null ? "" : CheckState.valueOf(this.status).getName();
	}
    
    public String getAdjDirectionName(){
		return AdjDirectionType.ALL.get(this.adjDirection) == null ? "" : AdjDirectionType.valueOf(this.adjDirection).getName();
	}
    
}