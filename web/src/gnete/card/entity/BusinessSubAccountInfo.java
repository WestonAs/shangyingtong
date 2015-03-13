package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

import flink.util.AmountUtil;

public class BusinessSubAccountInfo {
    private String accountId;

    private String custId;

    private String systemId;

    private String subAccountType;

    private String custName;

    private BigDecimal cashAmount;

    private BigDecimal freezeCashAmount;

    private String state;

    private Date createTime;

    private Date lastUpdateTime;

    private Date cancelTime;

    private String systemName;
    private String stateName;
    private String canBind;
    private String mySystem;
    private String[] bindAccts;
    private String defaultAcct;
    private String acctDesc;
    private String myAccount;
    public String getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(String myAccount) {
		this.myAccount = myAccount;
	}

	public String getAcctDesc() {
		return acctDesc;
	}

	public void setAcctDesc(String acctDesc) {
		this.acctDesc = acctDesc;
	}

	public String getDefaultAcct() {
		return defaultAcct;
	}

	public void setDefaultAcct(String defaultAcct) {
		this.defaultAcct = defaultAcct;
	}

	public String[] getBindAccts() {
		return bindAccts;
	}

	public void setBindAccts(String[] bindAccts) {
		this.bindAccts = bindAccts;
	}

	public String getMySystem() {
		return mySystem;
	}

	public void setMySystem(String mySystem) {
		this.mySystem = mySystem;
	}


	public String getCanBind() {
		return canBind;
	}

	public void setCanBind(String canBind) {
		this.canBind = canBind;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSubAccountType() {
        return subAccountType;
    }

    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getFreezeCashAmount() {
        return freezeCashAmount;
    }

    public void setFreezeCashAmount(BigDecimal freezeCashAmount) {
        this.freezeCashAmount = freezeCashAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }
    
    public BigDecimal getUsableBalance() {
		return AmountUtil.subtract(cashAmount, freezeCashAmount);
	}
}