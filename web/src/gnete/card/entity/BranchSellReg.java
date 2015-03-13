package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.SellType;

import java.math.BigDecimal;

public class BranchSellReg {
    private Long id;

    private String cardBranch;

    private String sellBranch;

    private String sellType;

    private String adjType;

    private BigDecimal amt;

    private BigDecimal orgAmt;

    private String status;

    private String effectiveDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getSellBranch() {
        return sellBranch;
    }

    public void setSellBranch(String sellBranch) {
        this.sellBranch = sellBranch;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
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
    
    public String getSellTypeName(){
		return SellType.ALL.get(this.sellType) == null ? "" : SellType.valueOf(this.sellType).getName();
	}
}