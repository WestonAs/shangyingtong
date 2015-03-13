package gnete.card.entity;

import java.math.BigDecimal;

/**
 * 
 * @Project: Card
 * @File: Clear2PayFileInfo.java
 * @See:
 * @description： 
 *   <li>网银通对账文件实体</li> 
 *   <li>对应业务模板字典中的定义</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class Clear2PayFileInfo implements java.io.Serializable {
	private static final long serialVersionUID = -8699754698313986721L;

	private Long seqNo;

	private String busiOrderNo;

	private String payDate;

	private String payAccount;

	private String payAccName;
	
	private String payBankNo;

	private String payBankName;

	private String recAccount;

	private String recAccName;
	
	private String recBankNo;

	private String recBankName;

	private BigDecimal amount;
	
	private String payType;

    private String paySpeed;

	private String expectTime;

	private String payUse;

	private String summary;

	private String remarks;

	private String backup1;

	private String backup2;

	private String backup3;

	private String backup4;

	private String backup5;

	private String recBankProvince;

	private String recBankCity;

	private String reservField1;

	private String reservField3;

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public String getBusiOrderNo() {
		return busiOrderNo;
	}

	public void setBusiOrderNo(String busiOrderNo) {
		this.busiOrderNo = busiOrderNo;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayAccName() {
		return payAccName;
	}

	public void setPayAccName(String payAccName) {
		this.payAccName = payAccName;
	}

	public String getPayBankName() {
		return payBankName;
	}

	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	public String getRecAccount() {
		return recAccount;
	}

	public void setRecAccount(String recAccount) {
		this.recAccount = recAccount;
	}

	public String getRecAccName() {
		return recAccName;
	}

	public void setRecAccName(String recAccName) {
		this.recAccName = recAccName;
	}

	public String getRecBankName() {
		return recBankName;
	}

	public void setRecBankName(String recBankName) {
		this.recBankName = recBankName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaySpeed() {
		return paySpeed;
	}

	public void setPaySpeed(String paySpeed) {
		this.paySpeed = paySpeed;
	}

	public String getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}

	public String getPayUse() {
		return payUse;
	}

	public void setPayUse(String payUse) {
		this.payUse = payUse;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

	public String getBackup2() {
		return backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}

	public String getBackup3() {
		return backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}

	public String getBackup4() {
		return backup4;
	}

	public void setBackup4(String backup4) {
		this.backup4 = backup4;
	}

	public String getBackup5() {
		return backup5;
	}

	public void setBackup5(String backup5) {
		this.backup5 = backup5;
	}

	public String getRecBankProvince() {
		return recBankProvince;
	}

	public void setRecBankProvince(String recBankProvince) {
		this.recBankProvince = recBankProvince;
	}

	public String getRecBankCity() {
		return recBankCity;
	}

	public void setRecBankCity(String recBankCity) {
		this.recBankCity = recBankCity;
	}

	public String getReservField1() {
		return reservField1;
	}

	public void setReservField1(String reservField1) {
		this.reservField1 = reservField1;
	}

	public String getReservField3() {
		return reservField3;
	}

	public void setReservField3(String reservField3) {
		this.reservField3 = reservField3;
	}
	
	public String getPayBankNo() {
		return payBankNo;
	}

	public void setPayBankNo(String payBankNo) {
		this.payBankNo = payBankNo;
	}
	
	public String getRecBankNo() {
		return recBankNo;
	}

	public void setRecBankNo(String recBankNo) {
		this.recBankNo = recBankNo;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
