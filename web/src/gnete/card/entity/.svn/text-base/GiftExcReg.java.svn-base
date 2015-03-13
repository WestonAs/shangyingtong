package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;

import java.math.BigDecimal;
import java.util.Date;

public class GiftExcReg {
    private Long giftExcId;

    private String giftId;

    private String giftName;
    
    private String jinstType;

	private String jinstId;

    private String ptClass;

    private BigDecimal ptValue;

    private String acctId;

    private String cardId;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    private String branchCode;

    public Long getGiftExcId() {
        return giftExcId;
    }

    public void setGiftExcId(Long giftExcId) {
        this.giftExcId = giftExcId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
    }

    public BigDecimal getPtValue() {
        return ptValue;
    }

    public void setPtValue(BigDecimal ptValue) {
        this.ptValue = ptValue;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    
    public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState
				.valueOf(this.status).getName();
	}
    
    public String getJinstTypeName() {
		return IssType.ALL.get(this.jinstType) == null ? "" : IssType
				.valueOf(this.jinstType).getName();
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
}