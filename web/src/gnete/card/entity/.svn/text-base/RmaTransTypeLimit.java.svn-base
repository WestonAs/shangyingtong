package gnete.card.entity;

import gnete.card.entity.state.CardTypeState;

import java.util.Date;

public class RmaTransTypeLimit extends RmaTransTypeLimitKey {
    private String status;

    private String updateBy;

    private Date updateTime;

    private String insName;

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

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}
	
	public String getStatusName() {
		return CardTypeState.ALL.get(this.status) == null ? "" : CardTypeState
				.valueOf(this.status).getName();
	}
}