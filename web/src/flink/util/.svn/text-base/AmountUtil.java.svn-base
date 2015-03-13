package flink.util;

import java.math.BigDecimal;

/**
 * 
 * @author aps-mjn
 * 
 */
public class AmountUtil {

	private static final int SCALE = 2;
	private static final int LONGSCALE = 6;

	public static BigDecimal format(BigDecimal value) {
		if (value == null)
			return null;
		return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 加
	 * 
	 * @param amount1
	 * @param amount2
	 * @return
	 */
	public static BigDecimal add(BigDecimal amount1, BigDecimal amount2) {
		BigDecimal result = new BigDecimal("0.00");
		if (amount1 != null) {
			result = result.add(amount1);
		}
		if (amount2 != null) {
			result = result.add(amount2);
		}
		return result;
	}

	/**
	 * 减
	 * 
	 * @param amount
	 * @param subtrahend
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal amount, BigDecimal subtrahend) {
		BigDecimal result = new BigDecimal("0.00");
		if (amount != null) {
			result = result.add(amount);
		}
		if (subtrahend != null) {
			result = result.subtract(subtrahend);
		}
		return result;
	}

	/**
	 * 乘
	 * 
	 * @param amount
	 * @param multiplicand
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal amount, BigDecimal multiplicand) {
		BigDecimal result = new BigDecimal("0.00");
		if (amount != null && multiplicand != null) {
			result = amount.multiply(multiplicand);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(multiply(new BigDecimal(220), new BigDecimal(10)));
	}

	/**
	 * 除
	 * 
	 * @param amount
	 * @param divisor
	 * @return
	 */
	public static BigDecimal divide(BigDecimal amount, BigDecimal divisor) {
		BigDecimal result = null;
		if (divisor == null || divisor.intValue() == 0) {
			return null;
		}
		if (amount != null) {
			result = amount.divide(divisor, LONGSCALE, BigDecimal.ROUND_HALF_UP);
		}
		return result;
	}

	/**
	 * 取最小值
	 * 
	 * @param amount
	 * @param divisor
	 * @return
	 */
	public static BigDecimal min(BigDecimal first, BigDecimal second) {
		if (first.doubleValue() > second.doubleValue()) {
			return second;
		}
		return first;
	}

	/**
	 * 大于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean gt(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2) > 0;
	}
	
	/**
	 * 等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean et(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2) == 0;
	}
	
	/**
	 * 不等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean ne(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2) != 0;
	}
	
	/**
	 * 小于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean lt(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2) < 0;
	}
	
	/**
	 * 大于等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean ge(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2) >= 0;
	}
	
	/**
	 * 小于等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean le(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2) <= 0;
	}
}
