package gnete.card.entity;

import gnete.card.entity.flag.ConsumeFlag;
import gnete.card.entity.flag.SendFlag;
import gnete.card.entity.state.CommonState;

import java.lang.String;
import java.util.Date;

public class MerchGroupPointCouponLimit extends MerchGroupPointCouponLimitKey{
    private String sendFlag;

    private String consumeFlag;

    private String status;

    private Date insertTime;

    private Date updateTime;

    private String updateBy;

    private String remark;

    /*
     * 以下为新增字段
     */
    private String coupnName;
    private String ptName;
    private String groupName;
    private String merchName;

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getConsumeFlag() {
        return consumeFlag;
    }

    public void setConsumeFlag(String consumeFlag) {
        this.consumeFlag = consumeFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCoupnName() {
 		return coupnName;
 	}

 	public void setCoupnName(String coupnName) {
 		this.coupnName = coupnName;
 	}

 	public String getPtName() {
 		return ptName;
 	}

 	public void setPtName(String ptName) {
 		this.ptName = ptName;
 	}

 	public String getGroupName() {
 		return groupName;
 	}
 	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
    public String getStatusName() {
        return status == null?"":CommonState.valueOf(status).getName();
    }
    
    public String getSendFlagName() {
    	return sendFlag == null?"":SendFlag.valueOf(sendFlag).getName();
    }
    public String getConsumeFlagName() {
    	return consumeFlag == null?"":ConsumeFlag.valueOf(consumeFlag).getName();
    }
}