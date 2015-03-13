package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeBill {
	private String no;

	private String accountId;

	private String custId;

	private String custName;

	private String bankCode;

	private String bankName;

	private String bankCardNo;

	private String bankAddNo;
	private String bankCardName;

	private BigDecimal amount;

	private String note;

	private Date createTime;

	private Date checkTime;

	private Date remitTime;

	private String voucherNo;

	private String state;

	private String startCreateDate;
	private String endCreateDate;
	private String recAcctNo;
	private String recAcctName;

	private String branchNo;
	private String canCheck;
	private String checkNote;
	private String remitTimeStr;
	public String getRemitTimeStr() {
		return remitTimeStr;
	}

	public void setRemitTimeStr(String remitTimeStr) {
		this.remitTimeStr = remitTimeStr;
	}

	public String getCheckNote() {
		return checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}

	public String getCanCheck() {
		return canCheck;
	}

	public void setCanCheck(String canCheck) {
		this.canCheck = canCheck;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	private String stateName;
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getRecAcctNo() {
		return recAcctNo;
	}

	public void setRecAcctNo(String recAcctNo) {
		this.recAcctNo = recAcctNo;
	}

	public String getRecAcctName() {
		return recAcctName;
	}

	public void setRecAcctName(String recAcctName) {
		this.recAcctName = recAcctName;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getBankAddNo() {
		return bankAddNo;
	}

	public String getBankCardName() {
		return bankCardName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public Date getCheckTime() {
		return checkTime;
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

	public String getNo() {
		return no;
	}

	public String getNote() {
		if (note == null) {
			note = "";
		}
		return note;
	}

	public Date getRemitTime() {
		return remitTime;
	}

	public String getState() {
		return state;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setBankAddNo(String bankAddNo) {
		this.bankAddNo = bankAddNo;
	}

	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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

	public void setNo(String no) {
		this.no = no;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setRemitTime(Date remitTime) {
		this.remitTime = remitTime;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
}