package gnete.card.entity;

import gnete.card.entity.state.EarnestMoneyState;
import java.math.BigDecimal;
import java.util.Date;

public class EarnestMoney {
    private String branchCode;

    private String respOrg;

    private BigDecimal property;

    private BigDecimal debt;

    private String riskLevel;

    private BigDecimal warnAmt;

    private BigDecimal sumRiskAmt;

    private BigDecimal sumCardAmt;

    private BigDecimal datumAmt;

    private BigDecimal remainRiskAmt;

    private BigDecimal riskRate;

    private BigDecimal remainUseAmt;

    private BigDecimal usedAmt;

    private BigDecimal noSetAmt;

    private String status;

    private String memo;

    private String updateBy;

    private Date updateTime;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getRespOrg() {
        return respOrg;
    }

    public void setRespOrg(String respOrg) {
        this.respOrg = respOrg;
    }

    public BigDecimal getProperty() {
        return property;
    }

    public void setProperty(BigDecimal property) {
        this.property = property;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public BigDecimal getWarnAmt() {
        return warnAmt;
    }

    public void setWarnAmt(BigDecimal warnAmt) {
        this.warnAmt = warnAmt;
    }

    public BigDecimal getSumRiskAmt() {
        return sumRiskAmt;
    }

    public void setSumRiskAmt(BigDecimal sumRiskAmt) {
        this.sumRiskAmt = sumRiskAmt;
    }

    public BigDecimal getSumCardAmt() {
        return sumCardAmt;
    }

    public void setSumCardAmt(BigDecimal sumCardAmt) {
        this.sumCardAmt = sumCardAmt;
    }

    public BigDecimal getDatumAmt() {
        return datumAmt;
    }

    public void setDatumAmt(BigDecimal datumAmt) {
        this.datumAmt = datumAmt;
    }

    public BigDecimal getRemainRiskAmt() {
        return remainRiskAmt;
    }

    public void setRemainRiskAmt(BigDecimal remainRiskAmt) {
        this.remainRiskAmt = remainRiskAmt;
    }

    public BigDecimal getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(BigDecimal riskRate) {
        this.riskRate = riskRate;
    }

    public BigDecimal getRemainUseAmt() {
        return remainUseAmt;
    }

    public void setRemainUseAmt(BigDecimal remainUseAmt) {
        this.remainUseAmt = remainUseAmt;
    }

    public BigDecimal getUsedAmt() {
        return usedAmt;
    }

    public void setUsedAmt(BigDecimal usedAmt) {
        this.usedAmt = usedAmt;
    }

    public BigDecimal getNoSetAmt() {
        return noSetAmt;
    }

    public void setNoSetAmt(BigDecimal noSetAmt) {
        this.noSetAmt = noSetAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
    
    public String getStatusName(){
		return EarnestMoneyState.ALL.get(this.status) == null ? "" : EarnestMoneyState.valueOf(this.status).getName();
	}
}