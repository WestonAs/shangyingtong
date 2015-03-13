package gnete.card.entity;

import gnete.card.entity.flag.BirthdayFlag;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.PromotionsRuleType;
import gnete.card.entity.type.PromtType;

import java.math.BigDecimal;

public class PromtRuleDef {
	private String promtId;

	private String promtRuleId;

	private String amtType;

	private BigDecimal amtRef;

	private String promtRuleType;

	private BigDecimal ruleParam1;

	private BigDecimal ruleParam2;

	private BigDecimal ruleParam3;

	private BigDecimal ruleParam4;

	private String ruleParam5;

	private String ruleStatus;

	private String reserved1;

	private String reserved2;

	private String reserved3;

	private String reserved4;

	private String reserved5;

	private String birthdayFlag;

	private String promtType;
	
	private String promtRuleName;

	public String getPromtId() {
		return promtId;
	}

	public void setPromtId(String promtId) {
		this.promtId = promtId;
	}

	public String getPromtRuleId() {
		return promtRuleId;
	}

	public void setPromtRuleId(String promtRuleId) {
		this.promtRuleId = promtRuleId;
	}

	public String getAmtType() {
		return amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	public BigDecimal getAmtRef() {
		return amtRef;
	}

	public void setAmtRef(BigDecimal amtRef) {
		this.amtRef = amtRef;
	}

	public String getPromtRuleType() {
		return promtRuleType;
	}

	public void setPromtRuleType(String promtRuleType) {
		this.promtRuleType = promtRuleType;
	}

	public BigDecimal getRuleParam1() {
		return ruleParam1;
	}

	public void setRuleParam1(BigDecimal ruleParam1) {
		this.ruleParam1 = ruleParam1;
	}

	public BigDecimal getRuleParam2() {
		return ruleParam2;
	}

	public void setRuleParam2(BigDecimal ruleParam2) {
		this.ruleParam2 = ruleParam2;
	}

	public BigDecimal getRuleParam3() {
		return ruleParam3;
	}

	public void setRuleParam3(BigDecimal ruleParam3) {
		this.ruleParam3 = ruleParam3;
	}

	public BigDecimal getRuleParam4() {
		return ruleParam4;
	}

	public void setRuleParam4(BigDecimal ruleParam4) {
		this.ruleParam4 = ruleParam4;
	}

	public String getRuleParam5() {
		return ruleParam5;
	}

	public void setRuleParam5(String ruleParam5) {
		this.ruleParam5 = ruleParam5;
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

	public String getBirthdayFlag() {
		return birthdayFlag;
	}

	public void setBirthdayFlag(String birthdayFlag) {
		this.birthdayFlag = birthdayFlag;
	}

	public String getPromtType() {
		return promtType;
	}

	public void setPromtType(String promtType) {
		this.promtType = promtType;
	}
	
	public String getPromtRuleName() {
		return promtRuleName;
	}
	
	public void setPromtRuleName(String promtRuleName) {
		this.promtRuleName = promtRuleName;
	}

	/**
	 * 活动类型名
	 */
	public String getPromtTypeName() {
		return PromtType.ALL.get(promtType) == null ? "" : PromtType.valueOf(
				promtType).getName();
	}

	/**
	 * 金额类型名
	 */
	public String getAmtTypeName() {
		return AmtType.ALL.get(amtType) == null ? "" : AmtType.valueOf(amtType)
				.getName();
	}

	/**
	 * 促销规则类型名
	 */
	public String getPromtRuleTypeName() {
		return PromotionsRuleType.ALL.get(promtRuleType) == null ? ""
				: PromotionsRuleType.valueOf(promtRuleType).getName();
	}

	/**
	 * 规则状态名
	 */
	public String getRuleStatusName() {
		return PromotionsRuleState.ALL.get(ruleStatus) == null ? ""
				: PromotionsRuleState.valueOf(ruleStatus).getName();
	}

	/**
	 * 生日标志名
	 */
	public String getBirthdayFlagName() {
		return BirthdayFlag.ALL.get(birthdayFlag) == null ? "" : BirthdayFlag
				.valueOf(birthdayFlag).getName();
	}

}