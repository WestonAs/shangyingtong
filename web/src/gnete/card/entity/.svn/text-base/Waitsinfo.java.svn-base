package gnete.card.entity;

import gnete.card.entity.state.WaitsinfoState;
import gnete.card.entity.state.WebState;

import java.util.Date;

public class Waitsinfo {
    private Long id;

    private String msgType;
    
    private Long refId;

    private String message;

    private String status;

    private String userCode;

    private Date sendTime;

    private Date dealTime;

    private String note;
    
    private String webState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getWebState() {
		return webState;
	}

	public void setWebState(String webState) {
		this.webState = webState;
	}
	
	public String getStatusName() {
		return WaitsinfoState.ALL.get(this.status) == null ? ""
				: WaitsinfoState.valueOf(this.status).getName();
	}
	
	public String getWebStatusName() {
		return WebState.ALL.get(this.webState) == null ? ""
				: WebState.valueOf(this.webState).getName();
	}
}