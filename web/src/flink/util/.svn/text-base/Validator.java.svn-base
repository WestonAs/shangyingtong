package flink.util;

import org.apache.commons.lang.StringUtils;

import gnete.etc.BizException;

/**
 * <p>Title: 参数校校</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 雁联</p>
 * @author dhs
 * @version 1.0
 */
public class Validator {

	private Validator() {
	}
	
	/**
	 * 校验字段不能为空或空格
	 * 
	 * */
	public static void checkNotNull(Object value, String message) throws BizException{
		
		if(value == null || value.toString().trim().length() == 0){
			
			message = message+"不能为空.";
			throw new BizException(message);
		}
	}
	/**
	 * 检查字符串长度
	 * */
	public static void checkLength(Object value, String message, int maxLen) throws BizException{
		
		checkNotNull(value,message);
		if (value.toString().getBytes().length > maxLen) {
			message = message + "长度超过" + maxLen + "个字节的最大限制;";
			throw new BizException(message);
		}
	}
	/**
	 * 检查值为O
	 * */
	public static String checkNull(Object value){
		
		if(value == null || value.toString().equals("0") ){
			
			return "";
		}else{
			
			return String.valueOf(value);
		}
	}
	
	/**
	 * 双精度小数验证
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isDouble(String target){
		if(StringUtils.isBlank(target)){
			return false;
		}
		
		try{
			new Double(target);
		}catch (Exception err) {
			return false;
		}
		return true;
	}
}
