package gnete.card.entity;

import gnete.card.entity.state.CurrCodeState;

import java.util.Date;

public class CurrCode {
    private String currCode;

    private String currName;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getCurrName() {
        return currName;
    }

    public void setCurrName(String currName) {
        this.currName = currName;
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
    
    public String getStatusName(){
    	return CurrCodeState.ALL.get(this.status) == null? "" : CurrCodeState.valueOf(this.status).getName();
    }
}