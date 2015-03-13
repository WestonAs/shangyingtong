package gnete.card.entity;

public class UserCertificateKey {
    private String dnNo;

    private String seqNo;

    private String startDate;

    private String userId;
    
    public UserCertificateKey() {
    	
	}
    
    public UserCertificateKey(String seqNo) {
    	this.seqNo = seqNo;
    }
    
    public UserCertificateKey(String dnNo, String seqNo) {
        super();
        this.dnNo = dnNo;
        this.seqNo = seqNo;
    }
    
    public UserCertificateKey(String dnNo, String seqNo, String startDate) {
    	this(dnNo,seqNo);
		this.startDate = startDate;
    }
    
    public UserCertificateKey(String dnNo, String seqNo, String startDate,
			String userId) {
		this(dnNo,seqNo,startDate);
		this.userId = userId;
	}

	public String getDnNo() {
        return dnNo;
    }

    public void setDnNo(String dnNo) {
        this.dnNo = dnNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
   
}