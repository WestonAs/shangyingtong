package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccountSystemInfo {
	private String systemId;

	private String custId;
	private String custName;
	// 体系创建者
	private String parentBranch;

	private Date createTime;
	private Date lastUpdateTime;
	private String acctNo;
	private String acctName;
	private String state;
	private String remark;

	private String bankNo;
	private String bankName;
	private String chnlMerNo;
	private String ftpAdd;
	private String ftpPort;
	private String ftpUser;
	private String ftpPwd;
	private String ftpPath;
	private BigDecimal unitFee;
	
	private BigDecimal freezeAmt;
	private BigDecimal usableAmt;
	private BigDecimal balanceAmt;
	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public BigDecimal getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(BigDecimal freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public BigDecimal getUsableAmt() {
		return usableAmt;
	}

	public void setUsableAmt(BigDecimal usableAmt) {
		this.usableAmt = usableAmt;
	}

	public BigDecimal getUnitFee() {
		return unitFee;
	}

	public void setUnitFee(BigDecimal unitFee) {
		this.unitFee = unitFee;
	}

	public String getFtpAdd() {
		return ftpAdd;
	}

	public void setFtpAdd(String ftpAdd) {
		this.ftpAdd = ftpAdd;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public String getChnlMerNo() {
		return chnlMerNo;
	}

	public void setChnlMerNo(String chnlMerNo) {
		this.chnlMerNo = chnlMerNo;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	private String systemName;

	public String getAcctName() {
		return acctName;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public String getBankNo() {
		return bankNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCustId() {
		return custId;
	}

	public String getCustName() {
		return custName;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public String getParentBranch() {
		return parentBranch;
	}

	public String getRemark() {
		return remark;
	}

	public String getState() {
		return state;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setParentBranch(String parentBranch) {
		this.parentBranch = parentBranch;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}