package gnete.card.entity;

import gnete.card.entity.state.CouponUseState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.PlatformType;
import java.math.BigDecimal;
import java.util.Date;

public class RetransCardReg {
    private Long retransCardId;

    private String acctId;

    private String cardId;

    private String merchId;

    private String termId;

    private String platform;

    private BigDecimal amt;

    private String coupon;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    // 表中新增字段
    /** 发卡机构编号 */
    private String cardBranch;
    
    /** 发起方编号 */
    private String initiator;
    
    // 添加非表字段-商户名称
    private String merchName;

    
	// 获得‘状态’名
	public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null? "" : RegisterState.valueOf(this.status).getName();
	}
	
	// 获得‘发起平台’名
	public String getPlatformName(){
		return PlatformType.ALL.get(this.platform) == null? "" : PlatformType.valueOf(this.platform).getName();
	}
	
	public String getCouponName(){
		return CouponUseState.ALL.get(this.coupon) == null? "" : CouponUseState.valueOf(this.coupon).getName();
	}
	
	// ------------------------------- getter and setter followed------------------------
    
    public Long getRetransCardId() {
        return retransCardId;
    }

    public void setRetransCardId(Long retransCardId) {
        this.retransCardId = retransCardId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	
	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	
	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

}