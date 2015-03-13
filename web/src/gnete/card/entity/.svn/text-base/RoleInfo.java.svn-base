package gnete.card.entity;

import gnete.card.entity.type.RoleType;

import java.util.Date;

public class RoleInfo implements java.io.Serializable{
   	private static final long serialVersionUID = 1L;

	private String roleId;

    private String roleName;

    private String userType;

    private String branchNo;

    private String merchantNo;

    private String deptId;

    private String roleType;
    
    private String updateUser;
    
    private Date updateTime;
    
    private String proxyCard;

    public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

	public String getProxyCard() {
		return proxyCard;
	}

	public void setProxyCard(String proxyCard) {
		this.proxyCard = proxyCard;
	}
	
	public String getRoleTypeName(){
		return RoleType.ALL.get(this.roleType) == null? "" : RoleType.valueOf(this.roleType).getName();
	}
}