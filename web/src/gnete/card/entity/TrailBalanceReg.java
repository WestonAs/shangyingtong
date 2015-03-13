package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.TrailType;

import java.util.Date;

public class TrailBalanceReg {
    private Long id;

    private String settDate;

    private String trailType;

    private String issType;

    private String issId;

    private String classId;

    private String status;

    private String updateBy;

    private Date updateTime;
    
    private String className;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getTrailType() {
        return trailType;
    }

    public void setTrailType(String trailType) {
        this.trailType = trailType;
    }

    public String getIssType() {
        return issType;
    }

    public void setIssType(String issType) {
        this.issType = issType;
    }

    public String getIssId() {
        return issId;
    }

    public void setIssId(String issId) {
        this.issId = issId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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
    
    public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}
    
    public String getTrailTypeName(){
    	return TrailType.ALL.get(this.trailType) == null ? "" : TrailType.valueOf(this.trailType).getName();
    }
    
    public String getIssTypeName(){
    	return IssType.ALL.get(this.issType) == null ? "" : IssType.valueOf(this.issType).getName();
    }

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}