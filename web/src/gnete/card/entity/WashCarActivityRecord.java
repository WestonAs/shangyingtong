package gnete.card.entity;

import java.util.Date;

public class WashCarActivityRecord extends WashCarActivityRecordKey {
    private Long availNum;

    private Date updateTime;

    private String updateUser;

    private String remark;

    private Date insertTime;
    
    private String cardIssuer;
    
    private String extId;

    public Long getAvailNum() {
        return availNum;
    }

    public void setAvailNum(Long availNum) {
        this.availNum = availNum;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}
}