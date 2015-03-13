package gnete.card.entity;

import gnete.card.entity.type.CardMembFeeFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.TransType;

import java.math.BigDecimal;
import java.util.Date;

public class CardMembFee {

	private Long		feeRuleId;

	private String		branchCode;

	private String		cardId;

	protected String	transType;

	private BigDecimal	ulMoney;

	/** 手续费计费方式 */
	private String		feeType;

	private String		curCode;

	private BigDecimal	feeRate;
	private BigDecimal	minAmt;
	private BigDecimal	maxAmt;
	/** 计费周期 */
	private String		costCycle;

	private String		modifyDate;
	private String		updateBy;
	private Date		updateTime;

	@Override
	public String toString() {
		return "CardMembFee [branchCode=" + branchCode + ", cardId=" + cardId + ", costCycle=" + costCycle
				+ ", curCode=" + curCode + ", feeRate=" + feeRate + ", feeRuleId=" + feeRuleId + ", feeType="
				+ feeType + ", maxAmt=" + maxAmt + ", minAmt=" + minAmt + ", modifyDate=" + modifyDate
				+ ", transType=" + transType + ", ulMoney=" + ulMoney + ", updateBy=" + updateBy
				+ ", updateTime=" + updateTime + "]";
	}

	/** 会员手续费计费类型 */
	public String getFeeTypeName(){
		return CardMembFeeFeeType.ALL.get(feeType) == null ? "" : CardMembFeeFeeType.valueOf(feeType).getName();
	}
	
	/** 会员手续费交易类型 */
	public String getTransTypeName(){
			return TransType.ALL.get(transType) == null ? "" : TransType.valueOf(transType).getName();
	}
	
	/** 会员手续费计费周期 */
	public String getCostCycleName(){
		return CostCycleType.ALL.get(costCycle) == null ? "" : CostCycleType.valueOf(costCycle).getName();
	}
	
	
	
	// ------------------------------- getter and setter followed------------------------
	
	public BigDecimal getUlMoney() {
		return ulMoney;
	}

	public void setUlMoney(BigDecimal ulMoney) {
		this.ulMoney = ulMoney;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}

	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getCostCycle() {
		return costCycle;
	}

	public void setCostCycle(String costCycle) {
		this.costCycle = costCycle;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
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

	public Long getFeeRuleId() {
		return feeRuleId;
	}

	public void setFeeRuleId(Long feeRuleId) {
		this.feeRuleId = feeRuleId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

}