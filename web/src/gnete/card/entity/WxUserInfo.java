package gnete.card.entity;

import gnete.card.entity.type.CertType;
import gnete.card.entity.type.WxUserType;

import java.util.Date;

public class WxUserInfo {
    private String userId;

    private String accessNo;

    private String externalCardId;

    private String userType;

    private String insId;

    private String pwdLogin;

    private String pwdStatus;

    private String credType;

    private String credNo;

    private String userName;

    private String mobileNo;

    private String contactAddress;

    private String contactPhone;

    private String email;

    private String openSms;

    private String birthday;

    private Date updateTime;

    private Date signInTime;

    private String platformOtherFlag;

    private String authMethod;

    private String remakr;

    private String reserved1;

    private String reserved2;

    private String reserved3;

    private String reserved4;
    
    private String cardId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getExternalCardId() {
        return externalCardId;
    }

    public void setExternalCardId(String externalCardId) {
        this.externalCardId = externalCardId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getPwdLogin() {
        return pwdLogin;
    }

    public void setPwdLogin(String pwdLogin) {
        this.pwdLogin = pwdLogin;
    }

    public String getPwdStatus() {
        return pwdStatus;
    }

    public void setPwdStatus(String pwdStatus) {
        this.pwdStatus = pwdStatus;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenSms() {
        return openSms;
    }

    public void setOpenSms(String openSms) {
        this.openSms = openSms;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public String getPlatformOtherFlag() {
        return platformOtherFlag;
    }

    public void setPlatformOtherFlag(String platformOtherFlag) {
        this.platformOtherFlag = platformOtherFlag;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getRemakr() {
        return remakr;
    }

    public void setRemakr(String remakr) {
        this.remakr = remakr;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getReserved4() {
        return reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }
    
    public String getUserTypeName() {
    	return WxUserType.ALL.get(this.getUserType()) == null ? "" : WxUserType.valueOf(this.getUserType()).getName();
    }
    
    public String getUserCredTypeName() {
    	return CertType.ALL.get(this.getCredType()) == null ? "" : CertType.valueOf(this.getCredType()).getName();
    }

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}