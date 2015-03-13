package gnete.card.entity;

import gnete.card.entity.type.BankAcctType;
import gnete.card.entity.type.IssType;

public class InsBankacctKey {
    private String bankAcctType;

    private String insCode;

    private String type;

    public String getBankAcctType() {
        return bankAcctType;
    }

    public void setBankAcctType(String bankAcctType) {
        this.bankAcctType = bankAcctType;
    }

    public String getInsCode() {
        return insCode;
    }

    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getTypeName() {
    	return IssType.ALL.get(this.type) == null ? "" : IssType.valueOf(this.type).getName();
    }

    public String getBankAcctTypeName() {
    	return BankAcctType.ALL.get(this.bankAcctType) == null ? "" : 
    				BankAcctType.valueOf(this.bankAcctType).getName();
    }
}