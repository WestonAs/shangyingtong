package gnete.card.entity;

import gnete.card.entity.flag.SMSFlag;
import gnete.card.entity.type.CertType;

import java.util.Date;

public class CardExtraInfo {
    private String cardId;

    private String custName;

    private String credType;

    private String credNo;

    private String address;

    private String telNo;

    private String mobileNo;

    private String email;

    private String smsFlag;

    private String birthday;

    private String updateBy;

    private Date updateTime;
    
    private String password;
    
    private Date pwdExpirTime;
    
    /** 购卡客户ID */
    private Long cardCustomerId;
    
    /** 售卡机构号 */
    private String saleOrgId;
    
    /** 发卡机构号 */
    private String cardBranch;
    
    /** 备注 */
    private String remark;
    
	private String credNoExpiryDate;
	private String career;
	private String nationality;
    
    public String getCredTypeName(){
		return CertType.ALL.get(this.credType) == null ? "" : CertType.valueOf(this.credType).getName();
	}
    
    public String getSmsFlagName(){
		return SMSFlag.ALL.get(this.smsFlag) == null ? "" : SMSFlag.valueOf(this.smsFlag).getName();
	}
    
    /** 是否开通短信通知 */
    public boolean isOpenSms(){
    	return SMSFlag.OPEN.getValue().equals(this.smsFlag);
    }
    
    
	// ------------------------------- getter and setter followed------------------------
    
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCredType() {
        return credType;
    }

    public void setCredType(String credType) {
        this.credType = credType;
    }

    public String getCredNo() {
        return credNo;
    }

    public void setCredNo(String credNo) {
        this.credNo = credNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(String smsFlag) {
        this.smsFlag = smsFlag;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPwdExpirTime() {
		return pwdExpirTime;
	}

	public void setPwdExpirTime(Date pwdExpirTime) {
		this.pwdExpirTime = pwdExpirTime;
	}

	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getSaleOrgId() {
		return saleOrgId;
	}

	public void setSaleOrgId(String saleOrgId) {
		this.saleOrgId = saleOrgId;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCredNoExpiryDate() {
		return credNoExpiryDate;
	}

	public void setCredNoExpiryDate(String credNoExpiryDate) {
		this.credNoExpiryDate = credNoExpiryDate;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}