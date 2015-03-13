package gnete.card.entity;

import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.SellType;

import java.math.BigDecimal;
import java.util.Date;

public class BranchSellChg {
    private Long id;

    private String cardBranch;

    private String sellBranch;

    private String sellType;

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
    
    public String getSellTypeName(){
		return SellType.ALL.get(this.sellType) == null ? "" : SellType.valueOf(this.sellType).getName();
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}
}