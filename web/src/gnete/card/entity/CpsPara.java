package gnete.card.entity;

import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.IssType;

import java.util.Date;

public class CpsPara extends CpsParaKey {
    private String issType;

    private String status;

    private String chlCode;

    private String updateBy;

    private Date updateTime;
    
    private String chlName;

    private String issName;

    public String getIssType() {
        return issType;
    }

    public void setIssType(String issType) {
        this.issType = issType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChlCode() {
        return chlCode;
    }

    public void setChlCode(String chlCode) {
        this.chlCode = chlCode;
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
    
    public String getStatusName() {
		return CardTypeState.ALL.get(this.status) == null ? "" : CardTypeState
				.valueOf(this.status).getName();
	}

    public String getIssTypeName() {
    	return IssType.ALL.get(this.issType) == null ? "" : IssType
    			.valueOf(this.issType).getName();
    }

	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	public String getIssName() {
		return issName;
	}

	public void setIssName(String issName) {
		this.issName = issName;
	}
}