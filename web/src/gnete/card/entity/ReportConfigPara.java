package gnete.card.entity;

import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.CycleType;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;

import java.util.Date;

public class ReportConfigPara extends ReportConfigParaKey {
    private String cycleType;

    private String cycleOfWeek;

    private String cycleOfMonth;

    private String updateBy;

    private Date updateTime;

    private String status;
    
    private String insName;

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getCycleOfWeek() {
        return cycleOfWeek;
    }

    public void setCycleOfWeek(String cycleOfWeek) {
        this.cycleOfWeek = cycleOfWeek;
    }

    public String getCycleOfMonth() {
        return cycleOfMonth;
    }

    public void setCycleOfMonth(String cycleOfMonth) {
        this.cycleOfMonth = cycleOfMonth;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

	public String getCycleTypeName() {
		return CycleType.ALL.get(this.cycleType) == null ? "" : CycleType
				.valueOf(this.cycleType).getName();
	}

	public String getCycleOfWeekName() {
		return DayOfWeekType.ALL.get(this.cycleOfWeek) == null ? "" : DayOfWeekType
				.valueOf(this.cycleOfWeek).getName();
	}

	public String getCycleOfMonthName() {
		return DayOfMonthType.ALL.get(this.cycleOfMonth) == null ? "" : DayOfMonthType
				.valueOf(this.cycleOfMonth).getName();
	}
}