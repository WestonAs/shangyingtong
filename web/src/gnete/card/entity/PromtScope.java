package gnete.card.entity;

import gnete.card.entity.type.ScopeType;

import java.math.BigDecimal;

public class PromtScope {
	private Long id;

	private String promtId;

	private String scopeType;

	private String merNo;

	private String cardBinScope;

	private String cardSubclass;

	private BigDecimal ptLlimit;

	private BigDecimal ptUlimit;

	private String membLevel;

	private String effDate;

	private String expirDate;

	// 数据库新增字段
	private String pinstType;

	private String pinstId;

	private String transType;

	private String promtType;

	private String status;

	// 加入非表字段
	private String merName;

	private String binName;

	private String subClassName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromtId() {
		return promtId;
	}

	public void setPromtId(String promtId) {
		this.promtId = promtId;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getCardBinScope() {
		return cardBinScope;
	}

	public void setCardBinScope(String cardBinScope) {
		this.cardBinScope = cardBinScope;
	}

	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public BigDecimal getPtLlimit() {
		return ptLlimit;
	}

	public void setPtLlimit(BigDecimal ptLlimit) {
		this.ptLlimit = ptLlimit;
	}

	public BigDecimal getPtUlimit() {
		return ptUlimit;
	}

	public void setPtUlimit(BigDecimal ptUlimit) {
		this.ptUlimit = ptUlimit;
	}

	public String getMembLevel() {
		return membLevel;
	}

	public void setMembLevel(String membLevel) {
		this.membLevel = membLevel;
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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPromtType() {
		return promtType;
	}

	public void setPromtType(String promtType) {
		this.promtType = promtType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public String getSubClassName() {
		return subClassName;
	}

	public void setSubClassName(String subClassName) {
		this.subClassName = subClassName;
	}

	/**
	 * 范围类型名
	 * 
	 * @return
	 */
	public String getScopeTypeName() {
		return ScopeType.ALL.get(this.scopeType) == null ? "" : ScopeType
				.valueOf(this.scopeType).getName();
	}
}