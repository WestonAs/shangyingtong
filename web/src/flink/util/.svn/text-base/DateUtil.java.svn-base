package flink.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author haichen.ma
 * 
 */
public class DateUtil {

	/**
	 * DateUtils
	 */
	private DateUtil() {
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return DateUtil.formatDate("yyyy-MM-dd");
	}

	/**
	 * 获取当前日期字符串，格式为yyyyMMdd
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 获取当前日期字符串，格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getCurrentToDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取当前时间字符串，格式为HHmmss
	 * 
	 * @return String
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("HHmmss").format(new Date());
	}

	public static String getCurrentDateTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static String getCurrentTimeMillis() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * 获取当前时间的字符串，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentPrettyDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取对应Date的日期字符串，格式为yyyyMMdd
	 * 
	 * @param date
	 *            源Date
	 * @return
	 */
	public static String getDateYYYYMMDD(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * getDate
	 * 
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static Date formatDate(String date, String pattern) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}

		try {
			DateFormat format = new SimpleDateFormat(pattern);
			Date result = format.parse(date);

			if (date.equals(format.format(result))) {
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * getDate
	 * 
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String formatDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return DateUtil.getDate(date, "yyyyMM-dd");
	}

	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * getDateDiffDays
	 * 
	 * @param curDate
	 * @param oriDate
	 */
	public static int getDateDiffDays(Date curDate, Date oriDate) {
		final int MS_PER_DAY = 1000 * 60 * 60 * 24;
		return (int) Math.abs((curDate.getTime() - oriDate.getTime()) / MS_PER_DAY);
	}
	/**
	 * getDateDiffDays
	 * @param date1
	 * @param date2
	 * @return pattern 字符串日期格式，如yyyyMMdd
	 */
	public static int getDateDiffDays(String date1, String date2, String pattern) {
		Date d1 = formatDate(date1, pattern);
		Date d2 = formatDate(date2, pattern);
		return getDateDiffDays(d1, d2);
	}

	public static String addDays(String date, int num, String pattern) {
		return DateUtil.getDate(DateUtil.addDays(DateUtil.formatDate(date, pattern), num), pattern);
	}

	public static Date addDays(Date date, int num) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);

		return c.getTime();
	}

	public static Date addMonths(Date date, int num) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	public static Date addYears(Date date, int num) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, num);
		return c.getTime();
	}

	public static Date getStartDate(String startDate) {
		return formatDate(startDate, "yyyyMMdd");
	}

	public static Date getEndDate(String endDate) {
		Date date = formatDate(endDate, "yyyyMMdd");

		return date == null ? null : addDays(date, 1);
	}

	public static Date getStartDate(Date startDate) {
		return clearTime(startDate);
	}

	private static Date clearTime(Date date) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	public static Date getEndDate(Date endDate) {
		Date date = clearTime(endDate);

		return date == null ? null : addDays(date, 1);
	}

	public static String getDateFormatStr(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	public static Date getParseDate(String str, String[] pattern)
			throws ParseException {
		return DateUtils.parseDate(str, pattern);
	}

	public static String getMonthAddStr(String str, int add) throws Exception {
		Date date = getParseDate(str, new String[] { "yyyyMM" });

		return getDateFormatStr(DateUtils.addMonths(date, add), "yyyyMM");
	}

	public static String getMonthAddStr(String str, int add, boolean isSpecial)
			throws Exception {
		Date date = getParseDate(str, new String[] { "yyyyMM" });

		int month = getDateMonth(date);

		// 如果是一年的最后一个月且按照特殊处理的话
		if (month == 11) {
			if (isSpecial) {
				return new StringBuilder(String.valueOf(getDateYear(date)))
						.append(String.valueOf(month + add + 1)).toString();
			}
		}

		return getDateFormatStr(DateUtils.addMonths(date, add), "yyyyMM");
	}

	public static String[] getReportDateMonthPare(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return new String[] { "", "" };
		}

		Date date = getParseDate(str, new String[] { "yyyyMM" });

		int month = getDateMonth(date);

		// 判断是否是最后一个月

		if (month == 11) {
			// 201013
			String anotherStr = new StringBuilder(String.valueOf(getDateYear(date))).append(
					String.valueOf(month + 1 + 1)).toString();

			return new String[] { str, anotherStr };
		}

		return new String[] { str, "" };
	}

	public static int getDateMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.MONTH);
	}

	public static int getDateYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.YEAR);
	}

	/**
	 * 日期合法check
	 * 
	 * @param date
	 *            需要check的日期
	 * @return 日期是否合法
	 */
	public static boolean chkDateFormat(String date) {
		try {
			// 如果输入日期不是8位的,判定为false.
			if (StringUtils.isBlank(date) || !date.matches("[0-9]{8}")) {
				return false;
			}
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(4, 6)) - 1;
			int day = Integer.parseInt(date.substring(6));
			Calendar calendar = GregorianCalendar.getInstance();
			
			// 当 Calendar 处于 non-lenient 模式时，如果其日历字段中存在任何不一致性，它都会抛出一个异常。
			calendar.setLenient(false);
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DATE, day);
			
			// 如果日期错误,执行该语句,必定抛出异常.
			calendar.get(Calendar.YEAR);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断yyyyMMdd的日期是否为月底
	 * @param date
	 * @return
	 */
	public static boolean isEndOfMonth(String date){
		
		if(chkDateFormat(date)){
			date = addDays(date, 1, "yyyyMMdd");
			if("01".equals(date.substring(6, 8))){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 获得date所在月份的最后一天
	 * @param date
	 * @return yyyyMMdd
	 */
	public static String getLastDay(Date date){    
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");      
  
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(date);
       calendar.set(Calendar.DATE, 1); //设为当前月的1号  
       calendar.add(Calendar.MONTH, 1); //加一个月，变为下月的1号  
       calendar.add(Calendar.DATE, -1); //减去一天，变为当月最后一天  
         
       return sdf.format(calendar.getTime());    
    }  

	/**
	 * 获得一个月之前的日子
	 * @param date
	 * @return
	 */
	public static String getPreMonthDay(Date date){    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");      
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);   
		calendar.add(Calendar.DATE, 1);   
		
		return sdf.format(calendar.getTime());    
	}  

	/**
	 * 获得一个月之后的日子
	 * @param date
	 * @return
	 */
	public static String getNextMonthDay(Date date){    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");      
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);   
		
		return sdf.format(calendar.getTime());    
	}
	
	/**
	 * 判断给定的字符串是否是合法指定格式的日期
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String str, String pattern) {
		boolean isValidDate = false;
		if (StringUtils.isBlank(str)) {
			return false;
		}
		
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
			
			isValidDate = true;
		} catch (ParseException ex) {
			isValidDate = false;
		}

		return isValidDate;
	}
	
	public static String getDateName(String orgDate, String pattern, String namePattern) {
		if (StringUtils.isBlank(orgDate)
				|| StringUtils.isEmpty(pattern)) {
			return StringUtils.EMPTY;
		}
		
		if (orgDate.length() != pattern.length()) {
			return StringUtils.EMPTY;
		}
		
		String dateName = "";
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			Date date = format.parse(orgDate);
			
			DateFormat nameFormat = new SimpleDateFormat(namePattern);
			dateName = nameFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dateName;
	}

	public static void main(String[] args) {
//		System.out.println(formatDate("0090501", "yyyyMMdd"));
//		System.out.println(chkDateFormat("20091232"));
		
		/*Date curDate = DateUtil.formatDate("20111010", "yyyyMMdd");
		Date oriDate = DateUtil.formatDate("20111110", "yyyyMMdd");*/
//		Date date = DateUtil.formatDate("20111129", "yyyyMMdd");
//		System.out.println(DateUtil.getPreMonthDay(date));
//		System.out.println(DateUtil.getNextMonthDay(date));
		//System.out.println(DateUtil.isValidDate("19990001", "yyyyMMdd"));
		System.out.println(DateUtil.getDateName("0522152717", "MMddHHmmss", "MM-dd HH:mm:ss"));
	}
}
