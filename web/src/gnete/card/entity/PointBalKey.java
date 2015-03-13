package gnete.card.entity;

public class PointBalKey {
    private String acctId;

    private String ptClass;
    
    //新增
    private String ptClassName;
    
    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
    }

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}
}