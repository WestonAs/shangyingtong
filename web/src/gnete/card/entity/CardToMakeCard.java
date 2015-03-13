package gnete.card.entity;

import gnete.card.entity.state.CardTypeState;

import java.util.Date;

public class CardToMakeCard extends CardToMakeCardKey {
    private String status;

    private String updateBy;

    private Date updateTime;
    
    private String branchName;
    
    private String makeCardName;

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
    
    public String getStatusName() {
		return CardTypeState.ALL.get(this.status) == null ? "" : CardTypeState
				.valueOf(this.status).getName();
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMakeCardName() {
		return makeCardName;
	}

	public void setMakeCardName(String makeCardName) {
		this.makeCardName = makeCardName;
	}
}