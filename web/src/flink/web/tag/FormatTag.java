package flink.web.tag;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

/**
 * EL格式化Function.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-9-6
 */
public class FormatTag {

	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	public static String formatAmt(String amount) {
		if (StringUtils.isEmpty(amount)) {
			return StringUtils.EMPTY;
		}
		BigDecimal b = new BigDecimal(amount);
		String result = null;
		DecimalFormat fmt = new DecimalFormat("#,##0.00");
		result = fmt.format(b.doubleValue());
		return result;
	}
	
	/**
	 * 格式化金额
	 * @param amount
	 * @param pre
	 * @return
	 */
	public static String formatAmt(String amount, int pre) {
		if (StringUtils.isEmpty(amount)) {
			return StringUtils.EMPTY;
		}
		BigDecimal b = new BigDecimal(amount);
		String result = null;
		String s = StringUtils.leftPad("0", pre, '0');
		DecimalFormat fmt = new DecimalFormat("##0." + s);
		result = fmt.format(b.doubleValue());
		return result;
	}
	
	/**
	 * 格式化百分数
	 * @param amount
	 * @return
	 */
	public static String formatPercent(String percent) {
		if (StringUtils.isEmpty(percent)) {
			return StringUtils.EMPTY;
		}
		return formatAmt(percent, 3) + "%";
	}

	/**
	 * 将数据库中的值以百分数的形式显示。如：0.99显示为99%
	 * @param amount
	 * @return
	 */
	public static String formatPer(String percent) {
		if (StringUtils.isEmpty(percent)) {
			return StringUtils.EMPTY;
		}
		NumberFormat numFormat = NumberFormat.getPercentInstance();
		numFormat.setMaximumFractionDigits(0);
		return numFormat.format(new BigDecimal(percent).doubleValue());
	}
	
	/**
	 * 格式化百分数
	 * @param amount
	 * @return
	 */
	public static String formatPercent(String percent, int pre) {
		if (StringUtils.isEmpty(percent)) {
			return StringUtils.EMPTY;
		}
		return formatAmt(percent, pre) + "%";
	}
}
