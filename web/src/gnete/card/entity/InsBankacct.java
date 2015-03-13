package gnete.card.entity;

import gnete.card.entity.type.AcctType;
import java.util.Date;

public class InsBankacct extends InsBankacctKey {
    private String bankNo;

    private String bankName;

    private String accNo;

    private String accName;

    private String accAreaCode;

    private String updateBy;

    private Date updateTime;

    private String insName;

    private String acctType;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccAreaCode() {
        return accAreaCode;
    }

    public void setAccAreaCode(String accAreaCode) {
        this.accAreaCode = accAreaCode;
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

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	
	public String getAcctTypeName() {
    	return AcctType.ALL.get(this.acctType) == null ? "" : AcctType.valueOf(this.acctType).getName();
    }
}