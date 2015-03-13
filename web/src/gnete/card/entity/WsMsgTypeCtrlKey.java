package gnete.card.entity;

public class WsMsgTypeCtrlKey {
	private String	branchCode;

	private String	msgType;

	private String	relatedIssNo;

	public WsMsgTypeCtrlKey() {
		super();
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getRelatedIssNo() {
		return relatedIssNo;
	}

	public void setRelatedIssNo(String relatedIssNo) {
		this.relatedIssNo = relatedIssNo;
	}
}