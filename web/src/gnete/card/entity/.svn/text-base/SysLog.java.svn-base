package gnete.card.entity;

import gnete.card.entity.state.SysLogViewState;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;

import java.util.Date;

public class SysLog {
    private Long id;

    private String branchNo;

    private String merchantNo;

    private String limitId;

    private String errorCode;

    private String logType;

    private String logClass;

    private String content;

    private Date logDate;

    private String approach;

    private String viewUser;

    private Date viewDate;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLimitId() {
        return limitId;
    }

    public void setLimitId(String limitId) {
        this.limitId = limitId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public String getViewUser() {
        return viewUser;
    }

    public void setViewUser(String viewUser) {
        this.viewUser = viewUser;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getStateName() {
		return SysLogViewState.ALL.get(this.state) == null ? ""
				: SysLogViewState.valueOf(this.state).getName();
	}
    
    public String getLogTypeName() {
    	return SysLogType.ALL.get(this.logType) == null ? ""
    			: SysLogType.valueOf(this.logType).getName();
    }
    
    public String getLogClassName() {
    	return SysLogClass.ALL.get(this.logClass) == null ? ""
    			: SysLogClass.valueOf(this.logClass).getName();
    }
    
}