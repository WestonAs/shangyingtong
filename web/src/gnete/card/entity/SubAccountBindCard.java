package gnete.card.entity;

import java.util.Date;

public class SubAccountBindCard extends SubAccountBindCardKey {


	private Date cardBindTime;
    
    private String isDefault;

    private Date updateTime;
    private BankAcct bankAcct;
    public BankAcct getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(BankAcct bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

    public Date getCardBindTime() {
        return cardBindTime;
    }

    public void setCardBindTime(Date cardBindTime) {
        this.cardBindTime = cardBindTime;
    }
}