package gnete.card.entity;

public class LogIpGuard {
	private String	statDate;

	private String	loginIp;

	private String	branchNo;

	private String	branchName;

	private String	merchName;

	private String	logcnt;

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getLogcnt() {
		return logcnt;
	}

	public void setLogcnt(String logcnt) {
		this.logcnt = logcnt;
	}
}