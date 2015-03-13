package gnete.card.entity;

import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.type.DateType;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class BalanceReportSet {
	private String cardBranch;

	private String dateType;

	private String generateDate;

	private String updateBy;

	private Date updateTime;
	
	/** 新增字段，是否需要生成余额明细报表，Y：是，N：否 */
	private String needDetailFlag;
	
	// 非表中字段
	private String branchName;

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * 日期类型名
	 * @return
	 */
	public String getDateTypeName() {
		return DateType.ALL.get(this.dateType) == null ? "" : DateType.valueOf(
				this.dateType).getName();
	}
	
	/**
	 * 日期名
	 * @return
	 */
	public String getGenerateDateName() {
		String result = "";
		if (StringUtils.equals(this.dateType, DateType.MONTH.getValue())) {
			result = DayOfMonthType.ALL.get(this.generateDate) == null ? "" : DayOfMonthType.valueOf(this.generateDate).getName();
		} 
		else if (StringUtils.equals(this.dateType, DateType.WEEK.getValue())) {
			result = DayOfWeekType.ALL.get(this.generateDate) == null ? "" : DayOfWeekType.valueOf(this.generateDate).getName();
		}
		
		return result;
	}
	
	/**
	 * 是否需要生成余额明细报表
	 * @return
	 */
	public String getNeedDetailFlagName() {
		return YesOrNoFlag.ALL.get(this.needDetailFlag) == null ? "" : YesOrNoFlag.valueOf(this.needDetailFlag).getName();
	}

	public String getNeedDetailFlag() {
		return needDetailFlag;
	}

	public void setNeedDetailFlag(String needDetailFlag) {
		this.needDetailFlag = needDetailFlag;
	}
}