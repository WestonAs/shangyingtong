package gnete.card.entity;

import gnete.card.entity.flag.DiscntExclusiveFlag;
import gnete.card.entity.state.RuleState;
import gnete.card.entity.type.DiscntOperMethod;
import gnete.card.entity.type.IssType;

import java.math.BigDecimal;

public class DiscntProtclDef {
	private String		discntProtclNo;

	private String		discntClass;

	private String		jinstType;

	private String		jinstId;

	private BigDecimal	discntRate;

	private BigDecimal	settDiscntRate;

	private String		discntOperMthd;

	private String		discntExclusiveFlag;

	private String		expirDate;

	private String		effDate;

	private String		protclPaperSn;

	private String		ruleStatus;

	private String		reserved1;

	private String		reserved2;

	private String		reserved3;

	private String		reserved4;

	private String		reserved5;

	// 新增字段
	private String		discntProtclName;

	// ---------------------------- 非表中字段 -----------------
	private String		className;
	private String		discntLabelCode;//折扣特征码 

	/**
	 * 状态名
	 */
	public String getRuleStatusName() {
		return RuleState.ALL.get(this.ruleStatus) == null ? "" : RuleState.valueOf(this.ruleStatus).getName();
	}

	/**
	 * 折扣操作方法名
	 */
	public String getDiscntOperMthdName() {
		return DiscntOperMethod.ALL.get(this.discntOperMthd) == null ? "" : DiscntOperMethod.valueOf(
				this.discntOperMthd).getName();
	}

	/**
	 * 联名机构类型名
	 */
	public String getJinstTypeName() {
		return IssType.ALL.get(this.jinstType) == null ? "" : IssType.valueOf(this.jinstType).getName();
	}

	/**
	 * 排他标志名
	 */
	public String getDiscntExclusiveFlagName() {
		return DiscntExclusiveFlag.ALL.get(this.discntExclusiveFlag) == null ? "" : DiscntExclusiveFlag
				.valueOf(this.discntExclusiveFlag).getName();
	}

	// ------------------------------------- getter and setter followed---------------------

	public String getDiscntProtclNo() {
		return discntProtclNo;
	}

	public void setDiscntProtclNo(String discntProtclNo) {
		this.discntProtclNo = discntProtclNo;
	}

	public String getDiscntClass() {
		return discntClass;
	}

	public void setDiscntClass(String discntClass) {
		this.discntClass = discntClass;
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

	public BigDecimal getDiscntRate() {
		return discntRate;
	}

	public void setDiscntRate(BigDecimal discntRate) {
		this.discntRate = discntRate;
	}

	public String getDiscntOperMthd() {
		return discntOperMthd;
	}

	public void setDiscntOperMthd(String discntOperMthd) {
		this.discntOperMthd = discntOperMthd;
	}

	public String getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(String expirDate) {
		this.expirDate = expirDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getProtclPaperSn() {
		return protclPaperSn;
	}

	public void setProtclPaperSn(String protclPaperSn) {
		this.protclPaperSn = protclPaperSn;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
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

	public BigDecimal getSettDiscntRate() {
		return settDiscntRate;
	}

	public void setSettDiscntRate(BigDecimal settDiscntRate) {
		this.settDiscntRate = settDiscntRate;
	}

	public String getDiscntExclusiveFlag() {
		return discntExclusiveFlag;
	}

	public void setDiscntExclusiveFlag(String discntExclusiveFlag) {
		this.discntExclusiveFlag = discntExclusiveFlag;
	}

	public String getDiscntProtclName() {
		return discntProtclName;
	}

	public void setDiscntProtclName(String discntProtclName) {
		this.discntProtclName = discntProtclName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDiscntLabelCode() {
		return discntLabelCode;
	}

	public void setDiscntLabelCode(String discntLabelCode) {
		this.discntLabelCode = discntLabelCode;
	}
}