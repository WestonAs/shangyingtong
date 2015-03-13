package flink.etc;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import flink.util.DateUtil;

/**
 * 起止日期对.
 * 
 * @author aps-mhc
 * 
 */
public class DatePair {
	private Date startDate;
	private Date endDate;
	
	public DatePair() {
		super();
	}
	
	public DatePair(Date beginDate, Date endDate) {
		super();
		this.startDate = beginDate;
		this.endDate = endDate;
	}
	
	public DatePair(String beginDate, String endDate){
		if (StringUtils.isNotEmpty(beginDate)) {
			this.startDate = DateUtil.formatDate(beginDate, "yyyyMMdd");
		}
		if (StringUtils.isNotEmpty(endDate)) {
			this.endDate = DateUtil.formatDate(endDate, "yyyyMMdd");
		}
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 设置日期.
	 * 
	 * @param params
	 * @param datePair
	 */
	public void setTruncatedTimeDate(Map map) {
		this.setTruncatedTimeDate(map, "startDate", "endDate");
	}
	
	/**
	 * 设置日期，同时给Map中指定的key
	 * @param map
	 * @param startDateKey
	 * @param endDateKey
	 */
	@SuppressWarnings("unchecked")
	public void setTruncatedTimeDate(Map map, String startDateKey, String endDateKey) {
		if (map == null) {
			map = new HashMap();
		}
		if (getStartDate() != null) {
			Date beginDate = DateUtils.truncate(getStartDate(), Calendar.DAY_OF_MONTH);
			map.put(startDateKey, beginDate);
		}
		if (getEndDate() != null) {
			Date endDate = DateUtils.addDays(DateUtils.truncate(getEndDate(), Calendar.DAY_OF_MONTH), 1);
			map.put(endDateKey, endDate);
		}
	}
	
}
