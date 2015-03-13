package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PayBill {
	private String id;

	private String payerCustId;

	private String payerCustName;
	
	private String payerAccountId;

	private String systemCustId;

	private String payeeBankType;

	private String payeeBankCode;

	private String payeeBankName;

	private String payeeAcctNo;

	private String payeeAcctName;

	private BigDecimal amount;

	private BigDecimal fee;

	private String remark;

	private String tradeNo;

	private String returnCode;

	private String returnInfo;
	
	private Date createTime;

	private String state;

	private Date checkTime;

	private Date finishTime;
	
	private String startCreateDate;
	
	private String endCreateDate;
	
	private String checkPass;
	
	private String stateName;
	
	private String payeeBankTypeName;
	
	private String canCheck;
	
	
	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getCanCheck() {
		return canCheck;
	}

	public void setCanCheck(String canCheck) {
		this.canCheck = canCheck;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPayeeBankTypeName() {
		return payeeBankTypeName;
	}

	public void setPayeeBankTypeName(String payeeBankTypeName) {
		this.payeeBankTypeName = payeeBankTypeName;
	}

	public String getCheckPass() {
		return checkPass;
	}

	public void setCheckPass(String checkPass) {
		this.checkPass = checkPass;
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

	private String[] ids;
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayerCustId() {
		return payerCustId;
	}

	public void setPayerCustId(String payerCustId) {
		this.payerCustId = payerCustId;
	}

	public String getPayerCustName() {
		return payerCustName;
	}

	public void setPayerCustName(String payerCustName) {
		this.payerCustName = payerCustName;
	}

	public String getPayerAccountId() {
		return payerAccountId;
	}

	public void setPayerAccountId(String payerAccountId) {
		this.payerAccountId = payerAccountId;
	}

	public String getSystemCustId() {
		return systemCustId;
	}

	public void setSystemCustId(String systemCustId) {
		this.systemCustId = systemCustId;
	}

	public String getPayeeBankType() {
		return payeeBankType;
	}

	public void setPayeeBankType(String payeeBankType) {
		this.payeeBankType = payeeBankType;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeAcctNo() {
		return payeeAcctNo;
	}

	public void setPayeeAcctNo(String payeeAcctNo) {
		this.payeeAcctNo = payeeAcctNo;
	}

	public String getPayeeAcctName() {
		return payeeAcctName;
	}

	public void setPayeeAcctName(String payeeAcctName) {
		this.payeeAcctName = payeeAcctName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getRemark() {
		if (remark == null) {
			remark = "";
		}
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	

}