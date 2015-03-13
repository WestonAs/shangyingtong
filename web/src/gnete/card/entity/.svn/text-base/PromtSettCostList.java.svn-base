package gnete.card.entity;

import gnete.card.entity.state.ProcState;
import gnete.card.entity.type.TransType;

import java.math.BigDecimal;
import java.lang.String;
import java.util.Date;

public class PromtSettCostList extends PromtSettCostListKey{
    private String transType;

    private String promtDonateId;

    private String cardIssuer;

    private String merchId;

    private BigDecimal issuerPointSettAmt;

    private BigDecimal merchPointSettAmt;

    private BigDecimal issuerCouponAmt;

    private BigDecimal merchCouponAmt;

    private String settDate;

    private String status;

    private Date insertTime;

    private String remark;


    //一些为新家字段
    private String groupName;

	public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPromtDonateId() {
        return promtDonateId;
    }

    public void setPromtDonateId(String promtDonateId) {
        this.promtDonateId = promtDonateId;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public BigDecimal getIssuerPointSettAmt() {
        return issuerPointSettAmt;
    }

    public void setIssuerPointSettAmt(BigDecimal issuerPointSettAmt) {
        this.issuerPointSettAmt = issuerPointSettAmt;
    }

    public BigDecimal getMerchPointSettAmt() {
        return merchPointSettAmt;
    }

    public void setMerchPointSettAmt(BigDecimal merchPointSettAmt) {
        this.merchPointSettAmt = merchPointSettAmt;
    }

    public BigDecimal getIssuerCouponAmt() {
        return issuerCouponAmt;
    }

    public void setIssuerCouponAmt(BigDecimal issuerCouponAmt) {
        this.issuerCouponAmt = issuerCouponAmt;
    }

    public BigDecimal getMerchCouponAmt() {
        return merchCouponAmt;
    }

    public void setMerchCouponAmt(BigDecimal merchCouponAmt) {
        this.merchCouponAmt = merchCouponAmt;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

    public String getTransTypeName() {
        return transType == null ? "":TransType.valueOf(transType).getName();
    }

    public String getStatusName() {
    	return status == null ? "":ProcState.valueOf(status).getName();
    }
}