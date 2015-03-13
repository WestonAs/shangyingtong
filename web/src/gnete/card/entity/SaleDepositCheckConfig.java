package gnete.card.entity;

import gnete.card.entity.flag.YesOrNoFlag;

import java.util.Date;

public class SaleDepositCheckConfig {
    private String cardBranch;

    private String sellCheck;

    private String depositCheck;

    private String lossCardCheck;

    private String transAccCheck;

    private String cancelCardCheck;
    
    /** 卡延期是否需要审核 */
    private String carddeferCheck;
    
    /** 换卡是否需要审核 */
    private String renewCardCheck;

    private String updateBy;

    private Date updateTime;

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getSellCheck() {
        return sellCheck;
    }

    public void setSellCheck(String sellCheck) {
        this.sellCheck = sellCheck;
    }

    public String getDepositCheck() {
        return depositCheck;
    }

    public void setDepositCheck(String depositCheck) {
        this.depositCheck = depositCheck;
    }

    public String getLossCardCheck() {
        return lossCardCheck;
    }

    public void setLossCardCheck(String lossCardCheck) {
        this.lossCardCheck = lossCardCheck;
    }

    public String getTransAccCheck() {
        return transAccCheck;
    }

    public void setTransAccCheck(String transAccCheck) {
        this.transAccCheck = transAccCheck;
    }

    public String getCancelCardCheck() {
        return cancelCardCheck;
    }

    public void setCancelCardCheck(String cancelCardCheck) {
        this.cancelCardCheck = cancelCardCheck;
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
    
    //==================================== 显示名字 ===================================================================
    public String getSellCheckName() {
        return YesOrNoFlag.ALL.get(this.sellCheck) == null ? "" : YesOrNoFlag.valueOf(this.sellCheck).getName();
    }
    
    public String getDepositCheckName() {
        return YesOrNoFlag.ALL.get(this.depositCheck) == null ? "" : YesOrNoFlag.valueOf(this.depositCheck).getName();
    }

    public String getLossCardCheckName() {
        return YesOrNoFlag.ALL.get(this.lossCardCheck) == null ? "" : YesOrNoFlag.valueOf(this.lossCardCheck).getName();
    }

    public String getTransAccCheckName() {
        return YesOrNoFlag.ALL.get(this.transAccCheck) == null ? "" : YesOrNoFlag.valueOf(this.transAccCheck).getName();
    }

    public String getCancelCardCheckName() {
        return YesOrNoFlag.ALL.get(this.cancelCardCheck) == null ? "" : YesOrNoFlag.valueOf(this.cancelCardCheck).getName();
    }
    
    public String getCarddeferCheckName() {
    	return YesOrNoFlag.ALL.get(this.carddeferCheck) == null ? "" : YesOrNoFlag.valueOf(this.carddeferCheck).getName();
    }

	public String getCarddeferCheck() {
		return carddeferCheck;
	}

	public void setCarddeferCheck(String carddeferCheck) {
		this.carddeferCheck = carddeferCheck;
	}

	public String getRenewCardCheckName() {
		return YesOrNoFlag.ALL.get(this.renewCardCheck) == null ? "" : YesOrNoFlag.valueOf(this.renewCardCheck).getName();
	}
	
	public String getRenewCardCheck() {
		return renewCardCheck;
	}

	public void setRenewCardCheck(String renewCardCheck) {
		this.renewCardCheck = renewCardCheck;
	}
}