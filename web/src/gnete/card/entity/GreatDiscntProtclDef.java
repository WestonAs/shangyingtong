package gnete.card.entity;

import gnete.card.entity.state.GreatDiscntProtclDefState;
import gnete.card.entity.type.PayWayType;

import java.math.BigDecimal;
import java.util.Date;

public class GreatDiscntProtclDef {
    private String greatDiscntProtclId;

    private String greatDiscntProtclName;

    private String cardIssuer;

    private String merchNo;

    private String cardBinScope;

    private BigDecimal issuerHolderDiscntRate;

    private BigDecimal issuerMerchDiscntRate;

    private String payWay;

    private String discntExclusiveFlag;

    private String expirDate;

    private String effDate;

    private String protclPaperNo;

    private BigDecimal chlIncomeFee;

    private String ruleStatus;

    private Date insertTime;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public String getGreatDiscntProtclId() {
        return greatDiscntProtclId;
    }

    public void setGreatDiscntProtclId(String greatDiscntProtclId) {
        this.greatDiscntProtclId = greatDiscntProtclId;
    }

    public String getGreatDiscntProtclName() {
        return greatDiscntProtclName;
    }

    public void setGreatDiscntProtclName(String greatDiscntProtclName) {
        this.greatDiscntProtclName = greatDiscntProtclName;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMerchNo() {
        return merchNo;
    }

    public void setMerchNo(String merchNo) {
        this.merchNo = merchNo;
    }

    public String getCardBinScope() {
        return cardBinScope;
    }

    public void setCardBinScope(String cardBinScope) {
        this.cardBinScope = cardBinScope;
    }

    public BigDecimal getIssuerHolderDiscntRate() {
        return issuerHolderDiscntRate;
    }

    public void setIssuerHolderDiscntRate(BigDecimal issuerHolderDiscntRate) {
        this.issuerHolderDiscntRate = issuerHolderDiscntRate;
    }

    public BigDecimal getIssuerMerchDiscntRate() {
        return issuerMerchDiscntRate;
    }

    public void setIssuerMerchDiscntRate(BigDecimal issuerMerchDiscntRate) {
        this.issuerMerchDiscntRate = issuerMerchDiscntRate;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getDiscntExclusiveFlag() {
        return discntExclusiveFlag;
    }

    public void setDiscntExclusiveFlag(String discntExclusiveFlag) {
        this.discntExclusiveFlag = discntExclusiveFlag;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getProtclPaperNo() {
        return protclPaperNo;
    }

    public void setProtclPaperNo(String protclPaperNo) {
        this.protclPaperNo = protclPaperNo;
    }

    public BigDecimal getChlIncomeFee() {
        return chlIncomeFee;
    }

    public void setChlIncomeFee(BigDecimal chlIncomeFee) {
        this.chlIncomeFee = chlIncomeFee;
    }

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
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
    
    public String getRuleStatusName() {
        return ruleStatus == null ? "":GreatDiscntProtclDefState.valueOf(ruleStatus).getName();
    }
    
    public String getPayWayName() {
        return payWay == null ? "":PayWayType.valueOf(payWay).getName();
    }
}