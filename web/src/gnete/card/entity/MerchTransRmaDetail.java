package gnete.card.entity;

import gnete.card.entity.state.RmaState;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;
import gnete.card.entity.type.XferType;

import java.math.BigDecimal;
import java.util.Date;


public class MerchTransRmaDetail extends MerchTransRmaDetailKey {
    private String recvAccNo;

    private String recvAccName;

    private String recvBankNo;

    private String recvBankName;

    private String xferType;

    private String dayOfCycle;

    private BigDecimal ulMoney;

    private Long transNum;

    private BigDecimal amount;

    private BigDecimal feeAmt;

    private BigDecimal rmaAmt;

    private Date updateTime;

    private String remark;

    private String rmaState;

    private String rmaSn;

    private String payAccNo;

    private String payAccName;

    private String payBankNo;

    private String payBankName;

    private String recvCodeAreaCode;
    
    private String payName;
    
    private String recvName;

    public String getRecvAccNo() {
        return recvAccNo;
    }

    public void setRecvAccNo(String recvAccNo) {
        this.recvAccNo = recvAccNo;
    }

    public String getRecvAccName() {
        return recvAccName;
    }

    public void setRecvAccName(String recvAccName) {
        this.recvAccName = recvAccName;
    }

    public String getRecvBankNo() {
        return recvBankNo;
    }

    public void setRecvBankNo(String recvBankNo) {
        this.recvBankNo = recvBankNo;
    }

    public String getRecvBankName() {
        return recvBankName;
    }

    public void setRecvBankName(String recvBankName) {
        this.recvBankName = recvBankName;
    }

    public String getXferType() {
        return xferType;
    }

    public void setXferType(String xferType) {
        this.xferType = xferType;
    }

    public String getDayOfCycle() {
        return dayOfCycle;
    }

    public void setDayOfCycle(String dayOfCycle) {
        this.dayOfCycle = dayOfCycle;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }

    public Long getTransNum() {
        return transNum;
    }

    public void setTransNum(Long transNum) {
        this.transNum = transNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public BigDecimal getRmaAmt() {
        return rmaAmt;
    }

    public void setRmaAmt(BigDecimal rmaAmt) {
        this.rmaAmt = rmaAmt;
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

    public String getRmaState() {
        return rmaState;
    }

    public void setRmaState(String rmaState) {
        this.rmaState = rmaState;
    }

    public String getRmaSn() {
        return rmaSn;
    }

    public void setRmaSn(String rmaSn) {
        this.rmaSn = rmaSn;
    }

    public String getPayAccNo() {
        return payAccNo;
    }

    public void setPayAccNo(String payAccNo) {
        this.payAccNo = payAccNo;
    }

    public String getPayAccName() {
        return payAccName;
    }

    public void setPayAccName(String payAccName) {
        this.payAccName = payAccName;
    }

    public String getPayBankNo() {
        return payBankNo;
    }

    public void setPayBankNo(String payBankNo) {
        this.payBankNo = payBankNo;
    }

    public String getPayBankName() {
        return payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public String getRecvCodeAreaCode() {
        return recvCodeAreaCode;
    }

    public void setRecvCodeAreaCode(String recvCodeAreaCode) {
        this.recvCodeAreaCode = recvCodeAreaCode;
    }

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getRecvName() {
		return recvName;
	}

	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	
	public String getXferTypeName() {
		return XferType.ALL.get(this.xferType) == null ? "" : XferType.valueOf(
				this.xferType).getName();
	}

	public String getRmaStateName() {
		return RmaState.ALL.get(this.rmaState) == null ? "" : RmaState.valueOf(
				this.rmaState).getName();
	}
	
	public String getDayOfCycleName() {
		if("0".equals(this.dayOfCycle) || dayOfCycle == null){
			return "";
		}
		else{
			String newDayOfCycle = this.dayOfCycle.substring(0, this.dayOfCycle.length()-1);
			String[] str = newDayOfCycle.split(",");
			String newStr = "";
			
			if(str.length==1){
				if(this.dayOfCycle.contains("M")||this.dayOfCycle.length()==3){
					newStr = (DayOfMonthType.ALL.get(newDayOfCycle) == null ? "" : DayOfMonthType
						.valueOf(newDayOfCycle).getName());
				}
				else{
					newStr = (DayOfWeekType.ALL.get(newDayOfCycle) == null ? "" : DayOfWeekType
							.valueOf(newDayOfCycle).getName());
				}
			}else{
				for(int i=0; i<str.length; i++){
					if(str[i].contains("M")||str[i].length()==2){
						newStr = newStr + (DayOfMonthType.ALL.get(str[i]) == null ? "" : DayOfMonthType
								.valueOf(str[i]).getName())+",";
					}
					else{
						newStr = newStr + (DayOfWeekType.ALL.get(str[i]) == null ? "" : DayOfWeekType
								.valueOf(str[i]).getName())+",";
					}
				}
			}
			return newStr;
		}
	}
}