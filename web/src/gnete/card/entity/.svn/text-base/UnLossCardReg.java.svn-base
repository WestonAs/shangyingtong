package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class UnLossCardReg {
    private Long unlossBatchId;

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
    
    private String startCard;
    
    private String endCard;
    
    private Long cardNum;
    
    private String procNote;

    public Long getUnlossBatchId() {
        return unlossBatchId;
    }

    public void setUnlossBatchId(Long unlossBatchId) {
        this.unlossBatchId = unlossBatchId;
    }

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
    
    public String getStatusName(){
    	return StringUtils.isEmpty(this.status) ? "" : RegisterState.valueOf(this.status).getName();
    }
    
    public String getCertTypeName(){
    	return StringUtils.isEmpty(this.certType)? "":CertType.valueOf(this.certType).getName();
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

	public String getStartCard() {
		return startCard;
	}

	public void setStartCard(String startCard) {
		this.startCard = startCard;
	}

	public String getEndCard() {
		return endCard;
	}

	public void setEndCard(String endCard) {
		this.endCard = endCard;
	}

	public Long getCardNum() {
		return cardNum;
	}

	public void setCardNum(Long cardNum) {
		this.cardNum = cardNum;
	}

	public String getProcNote() {
		return procNote;
	}

	public void setProcNote(String procNote) {
		this.procNote = procNote;
	}

}