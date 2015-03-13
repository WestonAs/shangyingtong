package gnete.card.entity;

import gnete.card.entity.state.WxCashierState;
import gnete.card.entity.type.WxCashierPermisionType;

import java.util.Date;

public class WxCashierInfo extends WxCashierInfoKey {
    private String cashierName;

    private String status;

    private String remark;

    private Date updateTime;

    private String updateUser;

    private String permissions;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    
    public String getStatuName() {
    	return WxCashierState.ALL.get(this.getStatus()) == null ? "" : WxCashierState.valueOf(this.getStatus()).getName();
    }
    
    public String getPermissionTypeName(){
    	return WxCashierPermisionType.ALL.get(this.getPermissions()) == null ? "" : WxCashierPermisionType.valueOf(this.getPermissions()).getName();
    }
}