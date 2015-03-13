package gnete.card.entity;

import gnete.card.entity.type.SubacctType;

public class SubAcctBalKey {
    private String acctId;

    private String subacctType;
    
    public SubAcctBalKey() {
	}
    
    public SubAcctBalKey(String acctId, String subacctType) {
		super();
		this.acctId = acctId;
		this.subacctType = subacctType;
	}

	public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getSubacctType() {
        return subacctType;
    }

    public void setSubacctType(String subacctType) {
        this.subacctType = subacctType;
    }
    
    public String getSubacctTypeName(){
    	return SubacctType.ALL.get(this.subacctType) == null ? "" : SubacctType.valueOf(this.subacctType).getName();
    }


}