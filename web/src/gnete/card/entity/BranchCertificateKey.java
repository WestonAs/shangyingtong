package gnete.card.entity;


/**
 * 
  * @Project: MyCard
  * @File: BranchCertificateKey.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class BranchCertificateKey {
	private String dnNo; // 证书DN编号

	private String seqNo; // 证书序列号(由系统seqNo生成算法)

	private String startDate; // 证书开通日期
	
    public BranchCertificateKey() {
		
	}
    
    public BranchCertificateKey(String dnNo,String seqNo) {
    	super();
    	this.dnNo = dnNo;
    	this.seqNo = seqNo;
    }
	
	public BranchCertificateKey(String dnNo,String seqNo, String startDate) {
		super();
		this.dnNo = dnNo;
		this.seqNo = seqNo;
		this.startDate = startDate;
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

}
