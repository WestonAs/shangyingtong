package flink.timer;

import java.util.Calendar;

/**
 * <p>间隔单位枚举</p>
 * <ul>
 * <li>间隔单位(秒、分、小时、天、周、月、年)</li>
 * <li>缺省为1个该单位间隔(比如1秒、1分、1小时、1天、1月、1年)</li>
 * </ul>
 * @Project: Card
 * @File: IntervalEnum.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-21
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public enum IntervalEnum {
	SECOND(Calendar.SECOND,1), 
	MINUTE(Calendar.MINUTE,1), 
	HOUR(Calendar.HOUR_OF_DAY,1), 
	DAY(Calendar.DAY_OF_MONTH,1), 
	WEEK(Calendar.WEEK_OF_YEAR,1), 
	MONTH(Calendar.MONTH,1), 
	YEAR(Calendar.YEAR,1);
	
	private int calendarField;

	private int amount;

	private IntervalEnum(int calendarField, int amount) {
		this.calendarField = calendarField;
		this.amount = amount;
	}
	
	public int getCalendarField() {
		return calendarField;
	}

	public int getAmount() {
		return this.amount;
	}
	
	public String toString() {
		return ("interval field : " + this.calendarField + ",amount :" + this.amount);
	}
	
	public int[] getIntervalParams() {
		int[] result = new int[] {};
		switch(this) {
		case DAY :
			result = new int[] {Calendar.DAY_OF_MONTH,1};
		    break;
		case SECOND :
			result = new int[]{Calendar.SECOND,1};
			break;
		case MONTH:
			result = new int[] {Calendar.MONTH,1};
			break;			
		case HOUR :
			result = new int[] {Calendar.HOUR_OF_DAY,1};
			break;			
		case WEEK:
			result = new int[] {Calendar.WEEK_OF_YEAR,1};
			break;
		case YEAR :
			result = new int[] {Calendar.YEAR,1};			
			break;
		case MINUTE :
			result = new int[] {Calendar.MINUTE,1};
			break;			
		default :
			break;
		}
		
		return result;
	}
}
