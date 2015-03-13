package gnete.card.entity;

import gnete.card.entity.state.RegisterState;

import java.util.Date;

public class PasswordResetReg {
    private Long passwordResetRegId;

    private String cardId;

    private String status;

    private String updateBy;

    private Date updateTime;

    private String remark;
    
    private String cardIssuer;
    
    private String saleOrgId;

    public Long getPasswordResetRegId() {
        return passwordResetRegId;
    }

    public void setPasswordResetRegId(Long passwordResetRegId) {
        this.passwordResetRegId = passwordResetRegId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getSaleOrgId() {
		return saleOrgId;
	}

	public void setSaleOrgId(String saleOrgId) {
		this.saleOrgId = saleOrgId;
	}
	
	public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null? "" : RegisterState.valueOf(this.status).getName();
	}
}