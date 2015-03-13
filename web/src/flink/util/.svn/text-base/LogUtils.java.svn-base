package flink.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 日志工具.
 * @author aps-mhc
 *
 */
public abstract class LogUtils {
	/**
	 * 替换日志内容中的代字符,格式为{0},{1}....
	 * @param log
	 * @param param
	 * @return
	 */
	public static String r(String log, Object...params) {
		if (StringUtils.isBlank(log) || ArrayUtils.isEmpty(params)) {
			return log;
		}
		
		for (int i = 0, n = params.length; i < n; i++) {
			String regex = "\\{" + i + "}";
			log = log.replaceFirst(regex, ObjectUtils.toString(params[i]));
		}
		
		return log;
	}
}
