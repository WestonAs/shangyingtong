package gnete.card.entity;

import gnete.card.entity.state.MerchTypeState;

import java.util.Date;

public class MerchType {
    private String merchType;

    private String typeName;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getMerchType() {
        return merchType;
    }

    public void setMerchType(String merchType) {
        this.merchType = merchType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
    	return MerchTypeState.ALL.get(this.status) == null? "" : MerchTypeState.valueOf(this.status).getName();
    }
    
}