package gnete.card.entity;

import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;
import gnete.card.entity.type.XferType;

import java.math.BigDecimal;
import java.util.Date;

public class MerchTransRemitAccount extends MerchTransRemitAccountKey {
    private String rmaSn;

    private String xferType;

    private String dayOfCycle;

    private BigDecimal ulMoney;

    private BigDecimal amount;

    private BigDecimal feeAmt;

    private BigDecimal lastAmt;

    private BigDecimal recvAmt;

    private BigDecimal nextAmt;

    private String chkId;

    private String chkStatus;

    private String updateBy;

    private Date updateTime;
    
    private String payName;
    
    private String recvName;
    
    private BigDecimal pyaAmt;
    
    private String remark;
    
    private String rmaFileName;

    public String getRmaSn() {
        return rmaSn;
    }

    public void setRmaSn(String rmaSn) {
        this.rmaSn = rmaSn;
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

    public BigDecimal getLastAmt() {
        return lastAmt;
    }

    public void setLastAmt(BigDecimal lastAmt) {
        this.lastAmt = lastAmt;
    }

    public BigDecimal getRecvAmt() {
        return recvAmt;
    }

    public void setRecvAmt(BigDecimal recvAmt) {
        this.recvAmt = recvAmt;
    }

    public BigDecimal getNextAmt() {
        return nextAmt;
    }

    public void setNextAmt(BigDecimal nextAmt) {
        this.nextAmt = nextAmt;
    }

    public String getChkId() {
        return chkId;
    }

    public void setChkId(String chkId) {
        this.chkId = chkId;
    }

    public String getChkStatus() {
        return chkStatus;
    }

    public void setChkStatus(String chkStatus) {
        this.chkStatus = chkStatus;
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
	
	public String getDayOfCycleName() {
		if("0".equals(this.dayOfCycle)){
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
		
	public String getXferTypeName() {
		return XferType.ALL.get(this.xferType) == null ? "" : XferType.valueOf(
				this.xferType).getName();
	}
	
	public String getChkStatusName() {
		return VerifyCheckState.ALL.get(this.chkStatus) == null ? "" : VerifyCheckState.valueOf(this.chkStatus).getName();
	}

	public BigDecimal getPyaAmt() {
		return pyaAmt;
	}

	public void setPyaAmt(BigDecimal pyaAmt) {
		this.pyaAmt = pyaAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRmaFileName() {
		return rmaFileName;
	}

	public void setRmaFileName(String rmaFileName) {
		this.rmaFileName = rmaFileName;
	}
}