package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;

import java.math.BigDecimal;

public class CardRiskReg {
    private Long id;

    private String branchCode;

    private String adjType;

    private BigDecimal amt;

    private BigDecimal orgAmt;

    private String status;

    private String effectiveDate;

    private String branchName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAdjType() {
        return adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getOrgAmt() {
        return orgAmt;
    }

    public void setOrgAmt(BigDecimal orgAmt) {
        this.orgAmt = orgAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public String getAdjTypeName(){
		return AdjType.ALL.get(this.adjType) == null ? "" : AdjType.valueOf(this.adjType).getName();
	}
    
    public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}