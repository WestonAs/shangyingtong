package gnete.card.entity;

import gnete.card.entity.state.ExternalCardImportState;

import java.util.Date;

public class MembImportReg {
    private Long id;

    private String cardBranch;

    private String fileName;

    private Integer totalCount;

    private String status;

    private String updateBy;

    private Date updateTime;

    private String remark;

    private String branchName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getStatusName() {
		return ExternalCardImportState.ALL.get(this.status) == null ? ""
				: ExternalCardImportState.valueOf(this.status).getName();
	}
}