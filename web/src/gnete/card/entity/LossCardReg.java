package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class LossCardReg {
    private Long lossBatchId;

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
    
    private String cardBranch; //发卡机构号

    public Long getLossBatchId() {
        return lossBatchId;
    }

    public void setLossBatchId(Long lossBatchId) {
        this.lossBatchId = lossBatchId;
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

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}
	
	public String getBatName() {
		if (StringUtils.isEmpty(this.cardId)) {
			return "批量卡挂失";
		} else {
			return "单张卡挂失";
		}
	}

}