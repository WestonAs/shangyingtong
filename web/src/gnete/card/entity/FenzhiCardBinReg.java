package gnete.card.entity;

import gnete.card.entity.state.CheckState;

import java.util.Date;

public class FenzhiCardBinReg {
    private String regId;

    private String branchCode;

    private String strBinNo;

    private Integer binCount;

    private String appBranch;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getStrBinNo() {
        return strBinNo;
    }

    public void setStrBinNo(String strBinNo) {
        this.strBinNo = strBinNo;
    }

    public Integer getBinCount() {
        return binCount;
    }

    public void setBinCount(Integer binCount) {
        this.binCount = binCount;
    }

    public String getAppBranch() {
        return appBranch;
    }

    public void setAppBranch(String appBranch) {
        this.appBranch = appBranch;
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
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return CheckState.ALL.get(this.status) == null ? "" : CheckState
				.valueOf(this.status).getName();
	}
}