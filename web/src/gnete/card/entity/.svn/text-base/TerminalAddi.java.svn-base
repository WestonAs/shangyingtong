package gnete.card.entity;

import gnete.card.entity.type.AcctMediaType;
import gnete.card.entity.type.AcctType;

import org.apache.commons.lang.StringUtils;


public class TerminalAddi {
    private String  termId;
    
    private String	termName;
    
	private String	bankNo;
	private String	bankName;
	private String	accNo;
	private String	accName;
	private String	accAreaCode;
	private String	acctType;
	private String	acctMediaType;
	
	public boolean isNotBlank() {
		return StringUtils.isNotBlank(this.termName) || StringUtils.isNotBlank(this.bankNo)
				|| StringUtils.isNotBlank(this.bankName) || StringUtils.isNotBlank(this.accNo)
				|| StringUtils.isNotBlank(this.accName) || StringUtils.isNotBlank(this.accAreaCode)
				|| StringUtils.isNotBlank(this.acctType) || StringUtils.isNotBlank(this.acctMediaType);
	}
	
	public String getAcctTypeName() {
		return AcctType.ALL.get(this.acctType) == null ? "" : AcctType.valueOf(this.acctType).getName();
	}
	
	/** 账户介质类型名  */
	public String getAcctMediaTypeName() {
		return AcctMediaType.ALL.get(acctMediaType) == null ? "" : AcctMediaType.valueOf(acctMediaType).getName();
	}
	
	/*
	 * =================== getters and setters following ===================
	 */
	
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

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

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctMediaType() {
		return acctMediaType;
	}

	public void setAcctMediaType(String acctMediaType) {
		this.acctMediaType = acctMediaType;
	}

}