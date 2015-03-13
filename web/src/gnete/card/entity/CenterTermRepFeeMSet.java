package gnete.card.entity;

public class CenterTermRepFeeMSet extends BaseSharesMSet {
	
	private String branchCode;

	private String feeMonth;

	private String termCode;

	// 新增
	private String branchName;
	
	private String termName;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getFeeMonth() {
		return feeMonth;
	}

	public void setFeeMonth(String feeMonth) {
		this.feeMonth = feeMonth;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}
}