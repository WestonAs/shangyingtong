package flink.util;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/** 手机号 工具类 */
public class MobileNumUtil {

	/**
	 * 传入的mobileNum是不是手机号
	 * 
	 * @param mobileNum
	 * @return
	 */
	public static boolean isMobileNum(String mobileNum) {
		return StringUtils.isNotBlank(mobileNum)
				&& Pattern.matches("^1\\d{10}$", mobileNum);
	}

	public static void main(String[] args) throws IOException {
		System.out.println(MobileNumUtil.isMobileNum("13016155153"));
	}
}
