package gnete.card.entity;

import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;
import gnete.card.entity.type.XferType;

import java.math.BigDecimal;
import java.util.Date;

public class CardMerchRemitAccount extends CardMerchRemitAccountKey {
    private String curCode;

    private String xferType;

    private String dayOfCycle;

    private BigDecimal ulMoney;

    private String effDate;

    private String updateBy;

    private Date updateTime;
    
    private String branchName;
    
    private String merchName;

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
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

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	
	public String getDayOfCycleName() {
		/*if("0".equals(this.dayOfCycle)){
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
		}*/
		
		if("0".equals(this.dayOfCycle)){
			return "";
		}
		
		String newStr = "";
		
		if(this.dayOfCycle.contains("M")||this.dayOfCycle.length()==2){
			newStr = (DayOfMonthType.ALL.get(this.dayOfCycle) == null ? "" : DayOfMonthType
				.valueOf(this.dayOfCycle).getName());
		}
		else{
			newStr = (DayOfWeekType.ALL.get(this.dayOfCycle) == null ? "" : DayOfWeekType
					.valueOf(this.dayOfCycle).getName());
		}
		return newStr;
	}
		
	public String getXferTypeName() {
		return XferType.ALL.get(this.xferType) == null ? "" : XferType.valueOf(
				this.xferType).getName();
	}
}