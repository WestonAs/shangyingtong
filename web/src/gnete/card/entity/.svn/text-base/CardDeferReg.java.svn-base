package gnete.card.entity;

import flink.util.CommonHelper;
import gnete.card.entity.flag.IsFileFlag;
import gnete.card.entity.state.RegisterState;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CardDeferReg {
    private Long cardDeferId;

    private String cardId;

    private String effDate;

    private String expirDate;

    private String newExpirDate;

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
    
    private String deferType;
    
    /** 发卡机构编号 */
    private String cardBranch;

	/** 领卡机构编号 */
    private String appOrgId;

    public Long getCardDeferId() {
        return cardDeferId;
    }

    public void setCardDeferId(Long cardDeferId) {
        this.cardDeferId = cardDeferId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }

    public String getNewExpirDate() {
        return newExpirDate;
    }

    public void setNewExpirDate(String newExpirDate) {
        this.newExpirDate = newExpirDate;
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
		 return StringUtils.isEmpty(this.status)?"" :RegisterState.valueOf(this.status).getName();
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
    
    public String getDeferType() {
		return deferType;
	}

	public void setDeferType(String deferType) {
		this.deferType = deferType;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}
	
	 public String getDeferTypeName() {
			return CommonHelper.isEmpty(deferType) ? "否" : IsFileFlag.valueOf(deferType).getName();
		}
}