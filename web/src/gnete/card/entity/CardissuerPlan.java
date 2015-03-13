package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

public class CardissuerPlan {
    private String brancheCode;

    private String planId;

    private String defaultCardExample;

    private String cardSubclass;

    private String status;

    private String updateBy;

    private Date updateTime;
    
    private String effDate;
    
    // 卡类型模板号
    private String cardSubclassTemp;
    
    // 新增表中没有字段
    private String planName; // 套餐名称
    
    private String cardExampleName; // 卡样名称

    public String getBrancheCode() {
        return brancheCode;
    }

    public void setBrancheCode(String brancheCode) {
        this.brancheCode = brancheCode;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getDefaultCardExample() {
        return defaultCardExample;
    }

    public void setDefaultCardExample(String defaultCardExample) {
        this.defaultCardExample = defaultCardExample;
    }

    public String getCardSubclass() {
        return cardSubclass;
    }

    public void setCardSubclass(String cardSubclass) {
        this.cardSubclass = cardSubclass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getCardExampleName() {
		return cardExampleName;
	}

	public void setCardExampleName(String cardExampleName) {
		this.cardExampleName = cardExampleName;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getCardSubclassTemp() {
		return cardSubclassTemp;
	}

	public void setCardSubclassTemp(String cardSubclassTemp) {
		this.cardSubclassTemp = cardSubclassTemp;
	}
	
	/**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return CommonState.ALL.get(this.status) == null ? "" : CommonState.valueOf(this.status).getName();
	}
}