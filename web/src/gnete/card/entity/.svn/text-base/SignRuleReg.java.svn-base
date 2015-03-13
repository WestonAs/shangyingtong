package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.DerateType;

import java.math.BigDecimal;
import java.util.Date;

public class SignRuleReg {
	private Long signRuleId;

	private String signRuleName;

	private Long signCustId;

	private Long depositRebateId;

	private BigDecimal overdraft;

	private BigDecimal annuity;

	private String derateType;

	private Long derateCount;

	private BigDecimal derateAmt;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String cardBranch;

	// 添加非表字段-签单客户名称
	private String signCustName;

	// 添加非表字段-充值返利规则名称
	private String depositRebateName;

	public Long getSignRuleId() {
		return signRuleId;
	}

	public void setSignRuleId(Long signRuleId) {
		this.signRuleId = signRuleId;
	}

	public String getSignRuleName() {
		return signRuleName;
	}

	public void setSignRuleName(String signRuleName) {
		this.signRuleName = signRuleName;
	}

	public Long getSignCustId() {
		return signCustId;
	}

	public void setSignCustId(Long signCustId) {
		this.signCustId = signCustId;
	}

	public Long getDepositRebateId() {
		return depositRebateId;
	}

	public void setDepositRebateId(Long depositRebateId) {
		this.depositRebateId = depositRebateId;
	}

	public BigDecimal getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}

	public BigDecimal getAnnuity() {
		return annuity;
	}

	public void setAnnuity(BigDecimal annuity) {
		this.annuity = annuity;
	}

	public String getDerateType() {
		return derateType;
	}

	public void setDerateType(String derateType) {
		this.derateType = derateType;
	}

	public Long getDerateCount() {
		return derateCount;
	}

	public void setDerateCount(Long derateCount) {
		this.derateCount = derateCount;
	}

	public BigDecimal getDerateAmt() {
		return derateAmt;
	}

	public void setDerateAmt(BigDecimal derateAmt) {
		this.derateAmt = derateAmt;
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

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getSignCustName() {
		return signCustName;
	}

	public void setSignCustName(String signCustName) {
		this.signCustName = signCustName;
	}

	public String getDepositRebateName() {
		return depositRebateName;
	}

	public void setDepositRebateName(String depositRebateName) {
		this.depositRebateName = depositRebateName;
	}

	/**
	 * 获取“状态”名称
	 */
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState
				.valueOf(status).getName();
	}

	/**
	 * 年费减免方式名
	 */
	public String getDerateTypeName() {
		return DerateType.ALL.get(derateType) == null ? "" : DerateType
				.valueOf(derateType).getName();
	}
}