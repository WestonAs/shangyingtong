package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;

import java.util.Date;

public class AddMagReg {
    private Long addMagId;

    private String cardId;
    
    private String custName;

    private String certType;

    private String certNo;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    private String branchCode;
    
    private String branchName;
    
    public Long getAddMagId() {
        return addMagId;
    }

    public void setAddMagId(Long addMagId) {
        this.addMagId = addMagId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    // 获得证件类型的名称
    public String getCertTypeName(){
		return CertType.ALL.get(this.certType) == null ? "" : CertType.valueOf(this.certType).getName();
	}
    
    // 获取状态名称
    public String getStatusName(){
    	return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
    }

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

}