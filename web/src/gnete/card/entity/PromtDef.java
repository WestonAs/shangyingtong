package gnete.card.entity;

import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PromtType;
import gnete.card.entity.type.TransType;

import java.util.Date;

public class PromtDef {
	private String promtId;

	private String issType;

	private String issId;

	private String pinstType;

	private String pinstId;

	private String cardBinScope;

	private String effDate;

	private String expirDate;

	private String addScope;

	private String reserved1;

	private String reserved2;

	private String reserved3;

	private String reserved4;

	private String reserved5;

	private String transType;

	private String updateBy;

	private Date updateTime;

	private String promtType;

	private String promtName;

	private String status;
	
	/** 指定时间段标识 1-包含时间段,0或空-不包含时间段 */
	private String timeFlag;
	/** 生效时间,格式: HHmm 24小时制  */
	private String effTime;
	/** 失效时间,格式: HHmm 24小时制 */
	private String expirTime;
	
	/** 新增属性 */
	private String groupName;


	public String getPromtId() {
		return promtId;
	}

	public void setPromtId(String promtId) {
		this.promtId = promtId;
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

	public String getPinstType() {
		return pinstType;
	}

	public void setPinstType(String pinstType) {
		this.pinstType = pinstType;
	}

	public String getPinstId() {
		return pinstId;
	}

	public void setPinstId(String pinstId) {
		this.pinstId = pinstId;
	}

	public String getCardBinScope() {
		return cardBinScope;
	}

	public void setCardBinScope(String cardBinScope) {
		this.cardBinScope = cardBinScope;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(String expirDate) {
		this.expirDate = expirDate;
	}

	public String getAddScope() {
		return addScope;
	}

	public void setAddScope(String addScope) {
		this.addScope = addScope;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public String getReserved5() {
		return reserved5;
	}

	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public String getPromtType() {
		return promtType;
	}

	public void setPromtType(String promtType) {
		this.promtType = promtType;
	}

	public String getPromtName() {
		return promtName;
	}

	public void setPromtName(String promtName) {
		this.promtName = promtName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getEffTime() {
		return effTime;
	}

	public void setEffTime(String effTime) {
		this.effTime = effTime;
	}

	public String getExpirTime() {
		return expirTime;
	}

	public void setExpirTime(String expirTime) {
		this.expirTime = expirTime;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * 状态名
	 */
	public String getStatusName() {
		return PromotionsRuleState.ALL.get(status) == null ? ""
				: PromotionsRuleState.valueOf(status).getName();
	}

	/**
	 * 活动类型名
	 */
	public String getPromtTypeName() {
		return PromtType.ALL.get(promtType) == null ? "" : PromtType.valueOf(
				promtType).getName();
	}

	/**
	 * 发行机构类型名
	 */
	public String getIssTypeName() {
		return IssType.ALL.get(issType) == null ? "" : IssType.valueOf(issType)
				.getName();
	}

	/**
	 * 参与机构类型名
	 */
	public String getPinstTypeName() {
		return IssType.ALL.get(pinstType) == null ? "" : IssType.valueOf(
				pinstType).getName();
	}
	
	/**
	 * 赠送机构类型名
	 */
	public String getSendTypeName() {
		return IssType.ALL.get(reserved4) == null ? "" : IssType.valueOf(reserved4).getName();
	}
	
	/**
	 * 赠送机构号
	 */
	public String getSendId() {
		return reserved5;
	}

	/**
	 * 交易类型名
	 */
	public String getTransTypeName() {
		return TransType.ALL.get(transType) == null ? "" : TransType.valueOf(
				transType).getName();
	}
}