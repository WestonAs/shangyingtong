package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

public class CardExampleDef {
    private String cardExampleId;

    private String cardExampleName;

    private String cardExamplePath;

    private String branchCode;

    private String status;

    private String updateBy;

    private Date updateTime;
    
    private String cardIssuer;

    public String getCardExampleId() {
        return cardExampleId;
    }

    public void setCardExampleId(String cardExampleId) {
        this.cardExampleId = cardExampleId;
    }

    public String getCardExampleName() {
        return cardExampleName;
    }

    public void setCardExampleName(String cardExampleName) {
        this.cardExampleName = cardExampleName;
    }

    public String getCardExamplePath() {
        return cardExamplePath;
    }

    public void setCardExamplePath(String cardExamplePath) {
        this.cardExamplePath = cardExamplePath;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
    
    /**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return CommonState.ALL.get(this.status) == null ? "" : CommonState
				.valueOf(this.status).getName();
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}
}