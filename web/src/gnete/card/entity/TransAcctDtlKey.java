package gnete.card.entity;

import gnete.card.entity.type.SubacctType;

public class TransAcctDtlKey {
    private String acctId;

    private String classId;

    private String subacctKind;

    private String transSn;

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubacctKind() {
        return subacctKind;
    }

    public void setSubacctKind(String subacctKind) {
        this.subacctKind = subacctKind;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
    }
    
    public String getSubacctKindName() {
    	return SubacctType.ALL.get(this.subacctKind) == null ? "" : SubacctType.valueOf(this.subacctKind).getName();
    }
}