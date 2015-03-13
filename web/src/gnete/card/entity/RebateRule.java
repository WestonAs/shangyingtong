package gnete.card.entity;

import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.type.RebateRuleCalType;
import gnete.card.entity.type.RebateRuleType;

import java.math.BigDecimal;
import java.util.Date;

public class RebateRule {
	private Long rebateId;

	private String rebateName;

	private String calType;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String cardBranch;
	
	private String isCommon;
	
	private String inputBranch;

	/** 返利方式：0 一次性返利， 1周期返利 */
	private String rebateType;
	
	//------- 以下 period开头的属性，只有在返利方式为“周期返利”的返利规则中才用到 --------
	
	/** 周期类型：0 按月 */
	private String periodType;
	
	private Long periodNum;
	
	private Date periodStartTime;
	
	private Date periodEndTime;
	
	private String periodImmedRebate;
	
	/** 满该金额（上期消费金额）才返利 */
	private BigDecimal periodTransAmt;
	
	public String getPeriodTypeDesc(){
		if("0".equals(periodType)){
			return "按月分期";
		}else{
			return "";
		}
	}

	public boolean isCommonRebateType(){
		return RebateRuleType.YES.getValue().equals(isCommon);
	}
	public boolean isNotCommonRebateType(){
		return RebateRuleType.NO.getValue().equals(isCommon);
	}
	public boolean isPosRebateType(){
		return RebateRuleType.TOTAL.getValue().equals(isCommon);
	}
	
	public boolean isNormalStatus(){
		return RebateRuleState.NORMAL.getValue().equals(status);
	}
	public boolean isUsedStatus(){
		return RebateRuleState.USED.getValue().equals(status);
	}
	public boolean isDisableStatus(){
		return RebateRuleState.DISABLE.getValue().equals(status);
	}
	
	/**
	 * 状态名
	 */
	public String getStatusName() {
		return RebateRuleState.ALL.get(status) == null ? "" : RebateRuleState.valueOf(status).getName();
	}
	
	/**
	 * 计算方式名
	 */
	public String getCalTypeName() {
		return RebateRuleCalType.ALL.get(calType) == null ? "" : RebateRuleCalType.valueOf(this.calType).getName();
	}
	
	public String getIsCommonName() {
		return RebateRuleType.ALL.get(isCommon) == null ? "" : RebateRuleType.valueOf(this.isCommon).getName();
	}
	
	// ------------------------------- getter and setter followed------------------------
	
	public Long getRebateId() {
		return rebateId;
	}

	public void setRebateId(Long rebateId) {
		this.rebateId = rebateId;
	}

	public String getRebateName() {
		return rebateName;
	}

	public void setRebateName(String rebateName) {
		this.rebateName = rebateName;
	}

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
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


	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}
	
	public String getInputBranch() {
		return inputBranch;
	}

	public void setInputBranch(String inputBranch) {
		this.inputBranch = inputBranch;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public Long getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(Long periodNum) {
		this.periodNum = periodNum;
	}

	public Date getPeriodStartTime() {
		return periodStartTime;
	}

	public void setPeriodStartTime(Date periodStartTime) {
		this.periodStartTime = periodStartTime;
	}

	public Date getPeriodEndTime() {
		return periodEndTime;
	}

	public void setPeriodEndTime(Date periodEndTime) {
		this.periodEndTime = periodEndTime;
	}

	public String getPeriodImmedRebate() {
		return periodImmedRebate;
	}

	public void setPeriodImmedRebate(String periodImmedRebate) {
		this.periodImmedRebate = periodImmedRebate;
	}

	public BigDecimal getPeriodTransAmt() {
		return periodTransAmt;
	}

	public void setPeriodTransAmt(BigDecimal periodTransAmt) {
		this.periodTransAmt = periodTransAmt;
	}
	
}