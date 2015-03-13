package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;
import java.util.Date;

public class CouponAwardReg {
    private Long couponAwardRegId;

    private String cardBin;

    private String insId;

    private String insType;

    private String couponClass;

    private String couponName;

    private String status;

    private String updateBy;

    private Date updateTime;

    private String remark;
    
    private String insName;

    public Long getCouponAwardRegId() {
        return couponAwardRegId;
    }

    public void setCouponAwardRegId(Long couponAwardRegId) {
        this.couponAwardRegId = couponAwardRegId;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getInsType() {
        return insType;
    }

    public void setInsType(String insType) {
        this.insType = insType;
    }

    public String getCouponClass() {
        return couponClass;
    }

    public void setCouponClass(String couponClass) {
        this.couponClass = couponClass;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}
    
    public String getInsTypeName(){
		return IssType.ALL.get(this.insType) == null ? "" : IssType.valueOf(this.insType).getName();
	}

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}
}