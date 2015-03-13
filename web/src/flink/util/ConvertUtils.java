package flink.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 转换工具.
 * @author aps-mhc
 *
 */
public abstract class ConvertUtils {
	public static Long[] convert(String[] array) {
		if (ArrayUtils.isEmpty(array)) {
			return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		}
		
		Long[] result = new Long[array.length];
		
		for (int i = 0, n = array.length; i < n; i++) {
			result[i] = Long.valueOf(array[i]);
		}
		
		return result;
	}
	
	public static Long[] convert(String s, String separator) {
		if (StringUtils.isEmpty(s)) {
			return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		}
		
		return convert(s.split("\\" + separator));
	}
	
	public static String[] convert(Long[] array) {
		if (ArrayUtils.isEmpty(array)) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		
		String[] result = new String[array.length];
		
		for (int i = 0, n = array.length; i < n; i++) {
			result[i] = String.valueOf(array[i]);
		}
		
		return result;
	}
	
	public static String convert(Long[] array,String separator){
		String rs = "";
		
		if (ArrayUtils.isEmpty(array)) {
			return null;
		}
		
		for(int i=0;i<array.length-1;i++){
			rs += array[i]+separator;
		}
		
		rs += array[array.length-1];
		return rs;
	}
	
	public static Integer[] convertArray(Long[] array) {
		if (array == null) {
			return null;
		}
		
		Integer[] result = new Integer[array.length];
		
		for (int i = 0; i < array.length; i++) {
			result[i] = new Integer(array[i].intValue());
		}
		
		return result;
	}
}
