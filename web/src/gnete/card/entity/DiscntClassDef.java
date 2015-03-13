package gnete.card.entity;

public class DiscntClassDef {
    private String discntClass;

    private String className;
    
    private String cardIssuer;
    
    private String branchName;
    
    /** 折扣特征码 */
    private String discntLabelCode;
    

    public String getDiscntClass() {
        return discntClass;
    }

    public void setDiscntClass(String discntClass) {
        this.discntClass = discntClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDiscntLabelCode() {
		return discntLabelCode;
	}

	public void setDiscntLabelCode(String discntLabelCode) {
		this.discntLabelCode = discntLabelCode;
	}
	
}