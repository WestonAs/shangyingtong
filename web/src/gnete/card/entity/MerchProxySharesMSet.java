package gnete.card.entity;


public class MerchProxySharesMSet extends BaseSharesMSet {
    
	private String branchCode;

    private String feeMonth;

    private String merchId;

    private String merchProxy;
    
    //新增
    private String branchName;
    private String proxyName;
    private String merchName;
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
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getMerchProxy() {
		return merchProxy;
	}
	public void setMerchProxy(String merchProxy) {
		this.merchProxy = merchProxy;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

    
}