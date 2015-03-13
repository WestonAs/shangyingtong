package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;

import java.math.BigDecimal;
import java.util.Date;

public class PointChgReg {
	private Long pointChgId;

	private String acctId;

	private String cardId;

	private String ptClass;

	private String jinstType;

	private String jinstId;

	private BigDecimal ptAvlb;

	private BigDecimal ptChg;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String ptClassName;
	
	private String branchCode;
	
	/** 发卡机构 */
	private String cardBranch;
	
	public Long getPointChgId() {
		return pointChgId;
	}

	public void setPointChgId(Long pointChgId) {
		this.pointChgId = pointChgId;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public String getJinstType() {
		return jinstType;
	}

	public void setJinstType(String jinstType) {
		this.jinstType = jinstType;
	}

	public String getJinstId() {
		return jinstId;
	}

	public void setJinstId(String jinstId) {
		this.jinstId = jinstId;
	}

	public BigDecimal getPtAvlb() {
		return ptAvlb;
	}

	public void setPtAvlb(BigDecimal ptAvlb) {
		this.ptAvlb = ptAvlb;
	}

	public BigDecimal getPtChg() {
		return ptChg;
	}

	public void setPtChg(BigDecimal ptChg) {
		this.ptChg = ptChg;
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

	public String getJinstTypeName() {
		return IssType.ALL.get(jinstType) == null ? "" : IssType.valueOf(
				this.jinstType).getName();
	}

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}

	public String getStatusName() {
		return RegisterState.valueOf(this.status).getName();
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}
}