package gnete.card.entity;

import gnete.card.entity.state.SignCardAccListState;

import java.math.BigDecimal;
import java.util.Date;

public class SignCardAccList extends SignCardAccListKey {
    private String accDate;

    private Long signCustId;

    private BigDecimal curAmt;

    private BigDecimal yearAmt;

    private BigDecimal useAmt;

    private BigDecimal feeAmt;

    private String strDate;

    private String endDate;

    private String expDate;

    private BigDecimal payAmt;

    private String payDate;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    //新增
    //过期标记
    private String expFlag;

    public String getAccDate() {
        return accDate;
    }

    public void setAccDate(String accDate) {
        this.accDate = accDate;
    }

    public Long getSignCustId() {
        return signCustId;
    }

    public void setSignCustId(Long signCustId) {
        this.signCustId = signCustId;
    }

    public BigDecimal getCurAmt() {
        return curAmt;
    }

    public void setCurAmt(BigDecimal curAmt) {
        this.curAmt = curAmt;
    }

    public BigDecimal getYearAmt() {
        return yearAmt;
    }

    public void setYearAmt(BigDecimal yearAmt) {
        this.yearAmt = yearAmt;
    }

    public BigDecimal getUseAmt() {
        return useAmt;
    }

    public void setUseAmt(BigDecimal useAmt) {
        this.useAmt = useAmt;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getExpFlag() {
		return expFlag;
	}

	public void setExpFlag(String expFlag) {
		this.expFlag = expFlag;
	}
    
	public String getStateName(){
		return SignCardAccListState.valueOf(this.status).getName();
	}
}