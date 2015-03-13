package gnete.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	public static boolean isValidDate(String dateStr) {
		String[] patterns = new String[] { "yyyyMMdd", "yyyy-MM-dd" };//不断添加...
		return isValidDate(dateStr, patterns);
	}
	
	public static boolean isValidDate(String dateStr, String[] patterns) {
		try {
			return DateUtils.parseDate(dateStr, patterns) != null;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 把 当前日期 转换成 String;
	 * 
	 * @return 日期字符串：格式如"2001-07-04  12:08:56 "
	 */
	public static String formatDate() {
		return formatDate(new Date());
	}

	/**
	 * 把 当前日期 转换成指定格式的 String;
	 * 
	 * @return 日期字符串
	 */
	public static String formatDate(String fmt) {
		return formatDate(fmt, new Date());
	}

	/**
	 * 把 毫秒数 转换成 String
	 * 
	 * @param time
	 *            the milliseconds since January 1, 1970, 00:00:00 GMT
	 * @return 日期字符串：格式如："2001-07-04  12:08:56 "
	 */
	public static String formatDate(long time) {
		return formatDate("yyyy-MM-dd HH:mm:ss", new Date(time));
	}

	/**
	 * 把 Date 转换成String;
	 * 
	 * @return 日期字符串：格式 如"2001-07-04  12:08:56 ";
	 */
	public static String formatDate(Date date) {
		return formatDate("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * 把 Date 转换成String;
	 * 
	 * @param fmt
	 *            日期格式
	 * @param date
	 * @return
	 */
	public static String formatDate(String fmt, Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 把 String 转换成 Date
	 * 
	 * @param dateStr
	 *            格式:yyyy-MM-dd HH:mm:ss，yyyy-MM-dd等格式
	 * @return Date 正常日期，或者null
	 */
	public static Date parseDate(String dateStr) {
		try {
			return DateUtils.parseDate(dateStr, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按fmt格式，将 String 转换成 Date 对象
	 * 
	 * @param fmt
	 *            自定义日期字符串格式
	 * @param dateStr
	 *            日期的字符串表示
	 * @return Date
	 */
	public static Date parseDate(String fmt, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String during = (days > 0 ? days + "天" : "") + (hours > 0 ? hours + "小时" : "")
				+ (minutes > 0 ? minutes + "分" : "") + (seconds > 0 ? seconds + "秒" : "");
		return "".equals(during) ? "0秒" : during;
	}
}
