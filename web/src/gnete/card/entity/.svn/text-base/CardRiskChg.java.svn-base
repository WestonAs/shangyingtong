package gnete.card.entity;

import gnete.card.entity.type.AdjType;

import java.math.BigDecimal;
import java.util.Date;

public class CardRiskChg {
    private Long id;

    private String branchCode;

    private String adjType;

    private BigDecimal amt;

    private BigDecimal availableAmt;

    private String refid;

    private Date changeDate;

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

    public BigDecimal getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(BigDecimal availableAmt) {
        this.availableAmt = availableAmt;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
    
    public String getAdjTypeName(){
		return AdjType.ALL.get(this.adjType) == null ? "" : AdjType.valueOf(this.adjType).getName();
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}
}