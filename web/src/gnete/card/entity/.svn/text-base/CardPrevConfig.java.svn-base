package gnete.card.entity;

import gnete.card.entity.state.CurrCodeState;

import java.util.Date;

public class CardPrevConfig {
    private String branchCode;

    private String cardPrev;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCardPrev() {
        return cardPrev;
    }

    public void setCardPrev(String cardPrev) {
        this.cardPrev = cardPrev;
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
    
    /**
     * 取得状态名
     * @return
     */
    public String getStatusName() {
    	return CurrCodeState.ALL.get(this.status) == null ? "" : CurrCodeState.valueOf(this.status).getName();
    }
}