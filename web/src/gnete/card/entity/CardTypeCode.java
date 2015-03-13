package gnete.card.entity;

import gnete.card.entity.state.CardTypeState;
import java.util.Date;

public class CardTypeCode {
	
    private String cardTypeCode;

    private String cardTypeName;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getCardTypeCode() {
        return cardTypeCode;
    }

    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
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
    
    public String getStateName() {
		return CardTypeState.ALL.get(this.status) == null ? "" : CardTypeState
				.valueOf(this.status).getName();
	}
    
    
}