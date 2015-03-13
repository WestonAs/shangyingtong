package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelTrade {

	public static final String CURRENCY  = "CNY";
	public static final String BUSI_CODE  = "04900";
	
	private String id;
	private String tradeType;
	private BigDecimal amount;
	private String tradeFileName;
	private byte[] tradeFileContent;
	private String returnFileName;
	private byte[] returnFileContent;
	private Date createTime;
	private Date returnTime;
	private String refId;
	private String srcCustId;
	private String srcCustName;
	private String busiCustId;
	private String busiCustName;
	private String bankType;
	private String bankCode;
	private String bankName;
	private String acctNo;
	private String acctName;
	private String chnlMerNo;
	private String remark;
	private String province;
	private String city;
	private String srcSystemId;
	//账户属性0:私人,1:公司
	private String acctProType;
	
	private String returnCode;
	private String returnInfo;
	private String returnState;
	private String startCreateDate;
	private String endCreateDate;
	private String tradeTypeName;
	private String bankTypeName;
	
	private String fileType;
	
	private String result;
	
	private String returnStateName;
	
	private String resultName;
	
	public String getReturnStateName() {
		return returnStateName;
	}

	public void setReturnStateName(String returnStateName) {
		this.returnStateName = returnStateName;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}

	public String getTradeTypeName() {
		return tradeTypeName;
	}

	public void setTradeTypeName(String tradeTypeName) {
		this.tradeTypeName = tradeTypeName;
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

	public String getReturnState() {
		return returnState;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getAcctProType() {
		return acctProType;
	}

	public void setAcctProType(String acctProType) {
		this.acctProType = acctProType;
	}
	
	public String getSrcSystemId() {
		return srcSystemId;
	}

	public void setSrcSystemId(String srcSystemId) {
		this.srcSystemId = srcSystemId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSrcCustId() {
		return srcCustId;
	}

	public void setSrcCustId(String srcCustId) {
		this.srcCustId = srcCustId;
	}

	public String getSrcCustName() {
		return srcCustName;
	}

	public void setSrcCustName(String srcCustName) {
		this.srcCustName = srcCustName;
	}

	public String getBusiCustId() {
		return busiCustId;
	}

	public void setBusiCustId(String busiCustId) {
		this.busiCustId = busiCustId;
	}

	public String getBusiCustName() {
		return busiCustName;
	}

	public void setBusiCustName(String busiCustName) {
		this.busiCustName = busiCustName;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getChnlMerNo() {
		return chnlMerNo;
	}

	public void setChnlMerNo(String chnlMerNo) {
		this.chnlMerNo = chnlMerNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}


	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTradeFileName() {
		return tradeFileName;
	}

	public void setTradeFileName(String tradeFileName) {
		this.tradeFileName = tradeFileName;
	}

	public byte[] getTradeFileContent() {
		return tradeFileContent;
	}

	public void setTradeFileContent(byte[] tradeFileContent) {
		this.tradeFileContent = tradeFileContent;
	}

	public String getReturnFileName() {
		return returnFileName;
	}

	public void setReturnFileName(String returnFileName) {
		this.returnFileName = returnFileName;
	}

	public byte[] getReturnFileContent() {
		return returnFileContent;
	}

	public void setReturnFileContent(byte[] returnFileContent) {
		this.returnFileContent = returnFileContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
