package gnete.card.entity;

import gnete.card.entity.state.RegisterState;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CouponBatReg {
    private Long couponBatRegId;

    private String startCard;

    private String endCard;

    private BigDecimal faceValue;

    private String branchCode;

    private String updateBy;

    private Date updateTime;

    private String remark;
    
    private String status;
    
    private BigDecimal cardNum;
    
    private String merchId;
    
    private String cardIssuer;
    
    private String ticketNo;

    public Long getCouponBatRegId() {
        return couponBatRegId;
    }

    public void setCouponBatRegId(Long couponBatRegId) {
        this.couponBatRegId = couponBatRegId;
    }

    public String getStartCard() {
        return startCard;
    }

    public void setStartCard(String startCard) {
        this.startCard = startCard;
    }

    public String getEndCard() {
        return endCard;
    }

    public void setEndCard(String endCard) {
        this.endCard = endCard;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusName(){
    	return StringUtils.isEmpty(this.status) ? "" : RegisterState.valueOf(this.status).getName();
    }

	public BigDecimal getCardNum() {
		return cardNum;
	}

	public void setCardNum(BigDecimal cardNum) {
		this.cardNum = cardNum;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
}