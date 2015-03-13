package gnete.card.entity;

import gnete.card.entity.state.UserState;
import gnete.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class UserInfo {
    private String userId;

    private String userName;

    private String branchNo;

    private String merchantNo;

    private String deptId;

    private String userPwd;

    private String pwdDate;

    private String phone;

    private String mobile;

    private String email;

    private Date updateTime;

    private String updateUser;

    private String state;

    private boolean isInitPwd;
    
    private int pwdMismatchCnt;
    
    private RoleInfo role;
    
    private UserCertificate userCertificate;
    
    /** 是否是证书用户 */
    public boolean isCertUser(){
    	return userCertificate != null;
    }
    
	/** 
	 * 获得离密码修改日期的天数
	 */
    public long getDaysOfPwdChanged(){
		Calendar now = new GregorianCalendar();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		this.pwdDate = StringUtils.isBlank(this.pwdDate) ? "20131121" : this.pwdDate; // 如果为空，则默认设置为版本该功能版本上线日期
		return (now.getTimeInMillis() - DateUtil.parseDate("yyyyMMdd", this.pwdDate).getTime())
				/ (24 * 60 * 60 * 1000);
    }
    
	public String getStatusName(){
    	return UserState.ALL.get(this.state) == null? "" : UserState.valueOf(this.state).getName();
    }
    
	// ------------------------------------------
	
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getPwdDate() {
        return pwdDate;
    }

    public void setPwdDate(String pwdDate) {
        this.pwdDate = pwdDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public RoleInfo getRole() {
		return role;
	}

	public void setRole(RoleInfo role) {
		this.role = role;
	}

	public UserCertificate getUserCertificate() {
		return userCertificate;
	}

	public void setUserCertificate(UserCertificate userCertificate) {
		this.userCertificate = userCertificate;
	}

	public boolean isInitPwd() {
		return isInitPwd;
	}

	public void setInitPwd(boolean isInitPwd) {
		this.isInitPwd = isInitPwd;
	}

	public int getPwdMismatchCnt() {
		return pwdMismatchCnt;
	}

	public void setPwdMismatchCnt(int pwdMismatchCnt) {
		this.pwdMismatchCnt = pwdMismatchCnt;
	}
}