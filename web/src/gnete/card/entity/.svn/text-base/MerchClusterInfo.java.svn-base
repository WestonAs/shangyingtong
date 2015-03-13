package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

public class MerchClusterInfo {
    private Long merchClusterId;

    private String merchClusterName;

    private String cardIssuer;

    private String updateBy;

    private Date updateTime;

    private String status;

    private String remark;

    public Long getMerchClusterId() {
        return merchClusterId;
    }

    public void setMerchClusterId(Long merchClusterId) {
        this.merchClusterId = merchClusterId;
    }

    public String getMerchClusterName() {
        return merchClusterName;
    }

    public void setMerchClusterName(String merchClusterName) {
        this.merchClusterName = merchClusterName;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getStatusName() {
    	return CommonState.ALL.get(this.status) == null ? "" : CommonState.valueOf(this.status).getName();
    }
}