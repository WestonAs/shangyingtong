package gnete.card.entity;

import gnete.card.entity.flag.YesOrNoFlag;

import java.math.BigDecimal;
import java.util.Date;

public class IcCardExtroInfo {
 
	private String cardId;

    private String cardSubclass;

    private String autoDepositFlag;

    private BigDecimal autoDepositAmt;

    private BigDecimal balanceLimit;

    private BigDecimal amountLimit;

    private String cardBranch;

    private String appOrgId;

    private String updateBy;

    private Date updateTime;

    private String remark;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardSubclass() {
        return cardSubclass;
    }

    public void setCardSubclass(String cardSubclass) {
        this.cardSubclass = cardSubclass;
    }

    public String getAutoDepositFlag() {
        return autoDepositFlag;
    }

    public void setAutoDepositFlag(String autoDepositFlag) {
        this.autoDepositFlag = autoDepositFlag;
    }

    public BigDecimal getAutoDepositAmt() {
        return autoDepositAmt;
    }

    public void setAutoDepositAmt(BigDecimal autoDepositAmt) {
        this.autoDepositAmt = autoDepositAmt;
    }

    public BigDecimal getBalanceLimit() {
        return balanceLimit;
    }

    public void setBalanceLimit(BigDecimal balanceLimit) {
        this.balanceLimit = balanceLimit;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(String appOrgId) {
        this.appOrgId = appOrgId;
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
    
    public String getAutoDepositFlagName() {
        return YesOrNoFlag.ALL.get(autoDepositFlag) == null ? "" : YesOrNoFlag.valueOf(autoDepositFlag).getName();
    }
}