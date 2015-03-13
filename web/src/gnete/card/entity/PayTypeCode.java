package gnete.card.entity;

import gnete.card.entity.state.PayTypeCodeState;

import java.util.Date;

public class PayTypeCode {
    private String payCode;

    private String payName;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
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
    	return PayTypeCodeState.ALL.get(this.status) == null? "" : PayTypeCodeState.valueOf(this.status).getName();
    }
}