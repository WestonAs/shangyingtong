package gnete.card.entity;

public class BranchHasTypeKey {
    private String branchCode;

    private String typeCode;

    public BranchHasTypeKey() {
	}

    public BranchHasTypeKey(String branchCode, String typeCode) {
		super();
		this.branchCode = branchCode;
		this.typeCode = typeCode;
	}
    
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}