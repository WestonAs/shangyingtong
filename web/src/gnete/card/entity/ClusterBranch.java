package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

public class ClusterBranch extends ClusterBranchKey {

	private String branchname;//新增属性
	private String updateby;
	private Date updatetime;
	private String status;
	private String remark;

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	
	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    public String getStatusname() {
    	return CommonState.ALL.get(this.status) == null ? "" : CommonState.valueOf(this.status).getName();
    }
}